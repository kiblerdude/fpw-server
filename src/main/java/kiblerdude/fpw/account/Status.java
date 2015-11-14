package kiblerdude.fpw.account;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Status {
	JOBBER("Jobber"),
	LOWCARDER("Low-carder"),
	MIDCARDER("Mid-carder"),
	PRO("Pro"),
	SUPERSTAR("Superstar"),
	HOF("Hall of Famer");
	
	private final String display;
	
	private Status(String display) {
		this.display = display;
	}
	
	@JsonValue
	public String getStatus() {
		return display;
	}
	
	public static Status fromLevel(Long level) {
		if (level <= 2) {
			return JOBBER;
		} else if (level > 2 && level <= 4) {
			return LOWCARDER;
		} else if (level > 4 && level <= 6) {
			return MIDCARDER;
		} else if (level > 6 && level <= 8) {
			return PRO;
		} else if (level > 8 && level <= 12) {
			return SUPERSTAR;
		} else {
			return HOF;
		}
	}
}
