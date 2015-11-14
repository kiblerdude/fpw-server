package kiblerdude.fpw.news;

import static java.lang.String.format;

import io.dropwizard.lifecycle.Managed;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;
import org.apache.log4j.Logger;

import com.google.common.collect.Lists;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

public class NewsService implements Managed, Runnable {

	private static final Logger LOG = Logger.getLogger(NewsService.class);

	// source, rss url, max number of stories to store
	private static final NewsSource[] SOURCES = {
			new NewsSource("podcastone.com", "http://www.podcastone.com/podcast?categoryID2=619", 1), //jr
			new NewsSource("podcastone.com", "http://www.podcastone.com/podcast?categoryID2=542", 1), //austin
			new NewsSource("podcastone.com", "http://www.podcastone.com/podcast?categoryID2=593", 1), //jericho
			//new NewsSource("radio.com", "https://api.radio.com/v2/podcast/rss/1264?format=MP3_128K", 1), //flair
			new NewsSource("anotherwrestlingpodcast.com", "http://anotherwrestlingpodcast.podbean.com/feed/", 1),
			new NewsSource("wrestlinginc.com", "http://www.wrestlinginc.com/wi/rss/pro-wrestling-news.xml", 5),
			new NewsSource("cagesideseats.com", "http://www.cagesideseats.com/rss/current", 5),
			new NewsSource("1wrestling.com", "http://www.1wrestling.com/feed/", 5),
			new NewsSource("impactwrestling.com", "http://feeds.feedburner.com/impactwrestling/news?format=xml", 4),
			new NewsSource("rohwrestling.com", "http://www.rohwrestling.com/newsfeed", 4),
			new NewsSource("wrestlingnewssource.com", "http://cdn.wrestlingnewssource.com/feeds/feed_news.xml", 5),
			new NewsSource("pwinsider.com", "http://feeds.feedburner.com/pwinsider?format=xml", 4) };
	// ("http://www.wwe.com/feeds/rss/features", 4);
	// ("http://www.reddit.com/r/squaredcircle/.rss", 6);
	// ("http://www.kayfabenews.com/feed/", 4);
	
	private final ScheduledExecutorService executor;
	private final List<News> stories;

	public NewsService() {
		executor = Executors.newScheduledThreadPool(1);
		stories = Collections.synchronizedList(Lists.newArrayList());
	}

	@Override
	public void start() throws Exception {
		LOG.info("Starting RSS Service");
		executor.scheduleAtFixedRate(this, 0, 1, TimeUnit.HOURS);
	}

	@Override
	public void stop() throws Exception {
		LOG.info("Stopping RSS Service");
		executor.shutdown();
		if (!executor.awaitTermination(5,  TimeUnit.SECONDS)) {
			executor.shutdownNow();
		}
	}
	
	public List<News> getNews(int page, int size) {
		if (page < 0 || size < 0) {
			LOG.warn("Invalid page or page size");
			return Lists.newArrayList();
		}
		int skip = (page - 1) * size;
		return stories.stream().skip(skip).limit(size).collect(Collectors.toList());
	}

	public void run() {
		LOG.info("Fetching RSS feeds");
		List<NewsSource> sources = Arrays.asList(SOURCES);
		SyndFeedInput input = new SyndFeedInput();

		// get news stores from each RSS feed and aggregate the stories into a single list	
		List<SyndEntry> allFeedEntries = sources.stream().map(source -> {
			List<SyndEntry> l = Lists.newArrayList();
			try {
				Content content = Request.Get(source.getLink()).execute().returnContent();
				SyndFeed feed = input.build(new XmlReader(content.asStream()));
				
				// set the source url for each story
				feed.getEntries().stream().forEach(entry -> { entry.setUri(source.getSource()); });
				
				l.addAll(feed.getEntries());
			} catch (FeedException | IOException e) {
				LOG.error(format("Failed to fetch news feed %s: %s", source.getLink(), e.getMessage()));
			}
			return l.stream().limit(source.getCount());
		}).flatMap(l -> l).collect(Collectors.toList());

		// sort the news stories by published date
		List<SyndEntry> sortedEntries = allFeedEntries.stream().sorted((e1, e2) -> {
			return ~(e1.getPublishedDate().compareTo(e2.getPublishedDate()));
		}).collect(Collectors.toList());
			
		// build an list of news stories
		List<News> stories = Lists.newArrayList();
		sortedEntries.stream().forEach(e -> {
			News news = new News(e.getUri(), e.getTitle(), e.getLink());
			stories.add(news);
		});
		
		LOG.info(format("Fetched %d news stories", stories.size()));
		
		if (!stories.isEmpty()) {
			this.stories.clear();
			this.stories.addAll(stories);
		}
	}
}
