package de.saxsys.skill2cypher.model;

public enum SkillLevel {
	
	Grundlagen("G"),
	Fortgeschritten("F"),
	Professionell("P"),
	Experte("E");
	
	private String abrv;

	private SkillLevel(String abrv) {
		this.abrv = abrv;
	}
	
	public static SkillLevel getSkillLevel(String abrv) {
		for (SkillLevel level : SkillLevel.values()) {
			if (level.getAbrv().equals(abrv)) {
				return level;
			}
		}
		throw new IllegalArgumentException(abrv + " is not a valid abbreviation for a SkillLevel!");
	}
	
	public String getAbrv() {
		return abrv;
	}

	public void setAbrv(String abrv) {
		this.abrv = abrv;
	}


	
}
