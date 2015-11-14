package kiblerdude.fpw.news;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;
import org.junit.Ignore;
import org.junit.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

public class NewsServiceTest {

	@Ignore
	@Test
	public void test() {

		List<NewsSource> sources = Lists.newArrayList(
			new NewsSource("podcastone.com", "http://www.podcastone.com/podcast?categoryID2=619", 1),
			new NewsSource("podcastone.com", "http://www.podcastone.com/podcast?categoryID2=542", 1),
			new NewsSource("wrestlinginc.com", "http://www.wrestlinginc.com/wi/rss/pro-wrestling-news.xml", 4),
			new NewsSource("cagesideseats.com", "http://www.cagesideseats.com/rss/current", 4),
			new NewsSource("1wrestling.com", "http://www.1wrestling.com/feed/", 4),
			new NewsSource("impactwrestling.com", "http://feeds.feedburner.com/impactwrestling/news?format=xml", 4),
			new NewsSource("rohwrestling.com", "http://www.rohwrestling.com/newsfeed", 4),
			new NewsSource("wrestlingnewssource.com", "http://cdn.wrestlingnewssource.com/feeds/feed_news.xml", 4));
		//("http://www.wwe.com/feeds/rss/features", 4);
		//("http://www.reddit.com/r/squaredcircle/.rss", 6);
		//("http://www.kayfabenews.com/feed/", 4);

		SyndFeedInput input = new SyndFeedInput();

		// get each feed, aggregate into one list, sort by published date
		
		List<SyndEntry> allFeedEntries = sources.stream().map(source -> {
			List<SyndEntry> l = Lists.newArrayList();
			try {
				Content content = Request.Get(source.getLink()).execute().returnContent();
				SyndFeed feed = input.build(new XmlReader(content.asStream()));
				
				List<SyndEntry> entries = feed.getEntries();
				entries.stream().forEach(entry -> { entry.setUri(source.getSource()); });
				
				l.addAll(feed.getEntries());
			} catch (FeedException | IOException e) {
				System.out.println(e.getMessage());
			}
			return l.stream().limit(source.getCount());
		}).flatMap(l -> l).collect(Collectors.toList());
		
		List<SyndEntry> sortedEntries = allFeedEntries.stream().sorted((e1, e2) -> {
			return ~(e1.getPublishedDate().compareTo(e2.getPublishedDate()));
		}).collect(Collectors.toList());
		
		ImmutableList.Builder<News> builder = ImmutableList.builder();
		sortedEntries.stream().forEach(e -> {
			News news = new News(e.getUri(), e.getTitle(), e.getLink());
			builder.add(news);
		});
	}

}
