package kiblerdude.fpw.news;

public class NewsSource {
	
	private final String source;
	private final String link;
	private final Integer count;
	
	public NewsSource(String source, String link, Integer count) {
		this.source = source;
		this.link = link;
		this.count = count;
	}

	public String getSource() {
		return source;
	}

	public Integer getCount() {
		return count;
	}

	public String getLink() {
		return link;
	}
}
