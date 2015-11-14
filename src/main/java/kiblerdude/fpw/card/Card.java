package kiblerdude.fpw.card;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

import jersey.repackaged.com.google.common.collect.ImmutableList;

import java.util.Collections;
import java.util.List;

public class Card {

	@JsonProperty
	private String id;
	@JsonProperty
	private String promotion;
	@JsonProperty
	private String name;
	@JsonProperty
	private String date;
	@JsonProperty
	private List<Match> matches;
	
	@JsonCreator
	public Card(
			@JsonProperty String id,
			@JsonProperty String promotion,
			@JsonProperty String name,
			@JsonProperty String date,
			@JsonProperty List<Match> matches) {		
		this.id = id;
		this.promotion = promotion;
		this.name = name;
		this.date = date;
		
		if (matches != null) {
			this.matches = ImmutableList.copyOf(matches);
		}
	}

	public String getId() {
		return id;
	}

	public String getPromotion() {
		return promotion;
	}

	public String getName() {
		return name;
	}

	public String getDate() {
		return date;
	}

	public List<Match> getMatches() {
		if (matches != null) {
			return Collections.unmodifiableList(matches);
		} else {
			return Collections.unmodifiableList(Lists.newArrayList());
		}
	}
	
	@Override
	public String toString() {
		return Joiner.on(" ").join(id,  promotion,  name, date);
	}
}
