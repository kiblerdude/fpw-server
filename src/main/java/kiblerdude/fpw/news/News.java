package kiblerdude.fpw.news;

import java.util.Arrays;

import jersey.repackaged.com.google.common.base.Joiner;

public class News {
	
	private final String source;
	private final String title;
	private final String link;
	
	public News(String source, String title, String link) {
		this.source = source;
		this.title = title;
		this.link = link;
	}

	public String getSource() {
		return source;
	}

	public String getTitle() {
		return title;
	}

	public String getLink() {
		return link;
	}
	
	@Override
	public String toString() {
		return Joiner.on(" ").join(Arrays.asList(source, title, link));
	}
}
