package kiblerdude.fpw.account;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * API model for updating account emails and passwords.
 * 
 * @author kiblerj
 *
 */
public class UpdatedAccountCredentials {
	
	@JsonProperty("email")
	private String email;
	@JsonProperty("password")
	private String password;
	@JsonProperty("newEmail")
	private String newEmail;
	@JsonProperty("newPassword")
	private String newPassword;
	
	@JsonCreator
	public UpdatedAccountCredentials(
			@JsonProperty("email") String email,
			@JsonProperty("password") String password,
			@JsonProperty("newEmail") String newEmail,
			@JsonProperty("newPassword") String newPassword) {
		this.email = email;
		this.password = password;
		this.newEmail = newEmail;
		this.newPassword = newPassword;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}
	
	public String getNewEmail() {
		return newEmail;
	}
	
	public String getNewPassword() {
		return newPassword;
	}
}
