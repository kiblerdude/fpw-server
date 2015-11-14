package kiblerdude.fpw.card;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import jersey.repackaged.com.google.common.collect.ImmutableList;

import java.util.Collections;
import java.util.List;

public class Match {
	
	@JsonProperty
	private List<String> participants;
	
	@JsonProperty
	private String description;
	
	@JsonProperty
	private String winner;
	
	@JsonCreator
	public Match(
			@JsonProperty List<String> participants, 
			@JsonProperty String description, 
			@JsonProperty String winner) {
		this.participants = ImmutableList.copyOf(participants);
		this.description = description;
		this.winner = winner;
	}
	
	public List<String> getParticipants() {
		return Collections.unmodifiableList(participants);
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getWinner() {
		return winner;
	}
}
