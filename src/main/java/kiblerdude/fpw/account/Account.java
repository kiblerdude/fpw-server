package kiblerdude.fpw.account;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Account {
	
	@JsonProperty("id")
	private String id;
	@JsonProperty("email")
	private String email;
	@JsonProperty("password")
	private String password;
	
	public Account(String email, String id) {
		this.email = email;
		this.id = id;
	}
	
	@JsonCreator
	public Account(
			@JsonProperty("email") String email, 
			@JsonProperty("password") String password,
			@JsonProperty("id") String id) {
		this.email = email;
		this.password = password;
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}
}
