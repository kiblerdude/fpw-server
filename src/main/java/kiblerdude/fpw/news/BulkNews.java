package kiblerdude.fpw.news;

import java.util.List;

public class BulkNews {
	
	private final List<News> stories;
	
	public BulkNews(List<News> stories) {
		this.stories = stories;
	}

	public List<News> getStories() {
		return stories;
	}
}
