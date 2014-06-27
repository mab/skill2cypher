package de.saxsys.skill2cypher.model;

public enum SkillCategory {

	Branchenkenntnis("Branchenfach - kenntnisse", "Brachenfach- kenntnisse"),
	Betriebssystem("Betriebs-systeme", "Betriebssysteme", "Betriebs-"),
	Programmiersprache("Programmier-sprachen"),
	Framework("Frameworks", "Klassen-bibliotheken", "Schnittstellen-"),
	API("Programmier-schnittstellen", "APIs"),
	Datenbank("Datenbanken"),
	Server("Server"),
	Vorgehensmodell("Vorgehensmodelle"),
	Entwurfsmethode("Entwurfs-methoden"),
	Seitenbeschreibungssprache("Seitenbeschrei-bungssprachen"),
	Standard("Standards"),
	Werkzeuge("Tools/ Werkzeuge", "Anwendungen", "Tools / Werkzeuge"),
	Sprache("Sprachen"),
	Versionsverwaltung("Versions-verwaltung"),
	Ignore("Aufgaben-bereich  Entwicklung", "Praxis");
	
	private String[] originalCategorynames;
	
	private SkillCategory(String... originalCategorynames) {
		this.originalCategorynames = originalCategorynames;
	}
	
	public static SkillCategory getCategoryFromName(String originalCategoryName) {
		for (SkillCategory skillCategory : SkillCategory.values()) {
			for (String categoryName : skillCategory.originalCategorynames) {
				if (categoryName.equals(originalCategoryName)) {
					return skillCategory;
				}
			}
		}
		throw new IllegalArgumentException("No SkillCategory found for: " + originalCategoryName);
	}
}
