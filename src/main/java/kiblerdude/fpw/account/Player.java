package kiblerdude.fpw.account;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class Player {
	
	@JsonProperty("name")
	private String name;
	@JsonProperty("experience")
    private Long experience;
	@JsonProperty("level")
    private Long level;
	@JsonProperty("status")
	private Status status;
    
	@JsonProperty("wins")
    private Long wins;
	@JsonProperty("losses")
    private Long losses;
	@JsonProperty("draws")
    private Long draws;
    
    // credits = money to purchase items
	@JsonProperty("credits")
    private Long credits;
    
    // karma represents good or bad.  positive karma = good, negative karma = bad
	@JsonProperty("karma")
    private Long karma;
	@JsonProperty("gimmick")
	private Gimmick gimmick;
    
    /**
     * Constructor for new accounts.
     * @param name
     */
    public Player(String name) {
    	this.name = name;
    	this.level = 1L;
    	this.experience = 0L;
    	this.wins = 0L;
    	this.losses = 0L;
    	this.draws = 0L;
    	this.credits = 100L;
    	this.karma = 0L;
    	this.status = Status.fromLevel(level);
    	this.gimmick = Gimmick.fromKarma(karma);
    }
    
    /**
     * Constructor for existing accounts.
     * 
     * @param name
     * @param level
     * @param experience
     * @param wins
     * @param losses
     * @param draws
     * @param credits
     * @param karma
     */
    @JsonCreator
    public Player(
    		@JsonProperty("name") String name,
    		@JsonProperty("level") Long level,
    		@JsonProperty("experience") Long experience, 
    		@JsonProperty("wins") Long wins,
    		@JsonProperty("losses") Long losses,
    		@JsonProperty("draws") Long draws,
    		@JsonProperty("credits") Long credits,
    		@JsonProperty("karma") Long karma) {
    	this.name = name;
    	this.level = level;
    	this.experience = experience;
    	this.wins = wins;
    	this.losses = losses;
    	this.draws = draws;
    	this.credits = credits;
    	this.karma = karma;
    	this.status = Status.fromLevel(level);
    	this.gimmick = Gimmick.fromKarma(karma);    	
    }
    
	public String getName() {
		return name;
	}
	
    public Long getExperience() {
        return experience;
    }

    public Long getLevel() {
        return level;
    }

    public Long getWins() {
        return wins;
    }

    public Long getLosses() {
        return losses;
    }

    public Long getDraws() {
        return draws;
    }

    public Long getCredits() {
        return credits;
    }

    public Long getKarma() {
        return karma;
    }
    
    public Gimmick getGimmick() {
    	return gimmick;
    }
    
    public Status getStatus() {
    	return status;
    }
}
