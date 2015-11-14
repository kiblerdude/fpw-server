package kiblerdude.fpw.account;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Gimmick {
	FACE("Face"),
	HEEL("Heel"),
	NEUTRAL("Neutral");
	
	private final String display;
	private Gimmick(String display) {
		this.display = display;
	}
	
	@JsonValue
	public String getGimmick() {
		return display;
	}
	
	public static Gimmick fromKarma(Long karma) {
		if (karma > 5) {
			return FACE;
		} else if (karma < -5) {
			return HEEL;
		} else {
			return NEUTRAL;
		}
	}
}
