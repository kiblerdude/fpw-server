package kiblerdude.fpw.league;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class League {
	
	@JsonProperty("id")
	private String id;
	@JsonProperty("owner")
	private String owner;
	@JsonProperty("name")
	private String name;
}
