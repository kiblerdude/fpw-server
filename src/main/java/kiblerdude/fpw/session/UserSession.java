package kiblerdude.fpw.session;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import kiblerdude.fpw.account.UserAccount;

/**
 * Data model for user Sessions.
 * @author kiblerj
 *
 */
public class UserSession {
	@JsonProperty("sessionId")
	private String sessionId;
	@JsonProperty("accountId")
	private String accountId;
	@JsonProperty("user")
	private UserAccount user;
	
	@JsonCreator
	public UserSession(
			@JsonProperty("accountId") String accountId,
			@JsonProperty("sessionId") String sessionId,
			@JsonProperty("user") UserAccount user) {
		this.accountId = accountId;
		this.sessionId = sessionId;
		this.user = user;
	}

	public String getSessionId() {
		return sessionId;
	}

	public String getAccountId() {
		return accountId;
	}
	
	public UserAccount getUser() {
		return user;
	}
}
