package kiblerdude.fpw.account;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AccountCredentials {
	
	@JsonProperty("email")
	private String email;
	@JsonProperty("password")
	private String password;
	@JsonProperty("name")
	private String name;
	
	@JsonCreator
	public AccountCredentials(
			@JsonProperty("email") String email,
			@JsonProperty("password") String password,
			@JsonProperty("name") String name) {
		this.email = email;
		this.password = password;
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}
	
	public String getName() {
		return name;
	}
}
