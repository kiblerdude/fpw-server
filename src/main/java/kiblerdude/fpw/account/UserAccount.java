package kiblerdude.fpw.account;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class UserAccount {
	
	@JsonProperty("account")
	private Account account;
	
	@JsonProperty("player")
	private Player player;
	
	@JsonCreator
	public UserAccount(
			@JsonProperty("account") Account account,
			@JsonProperty("player") Player player) {
		this.account = account;
		this.player = player;
	}
	
	public Account getAccount() {
		return account;
	}
	
	public Player getPlayer() {
		return player;
	}	
}
