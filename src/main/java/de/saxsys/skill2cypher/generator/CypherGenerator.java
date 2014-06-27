package de.saxsys.skill2cypher.generator;

import java.util.LinkedHashMap;
import java.util.Map;

import de.saxsys.skill2cypher.model.Person;
import de.saxsys.skill2cypher.model.Skill;
import de.saxsys.skill2cypher.model.SkillCategory;

public class CypherGenerator {

	public String generateCreateNodeWithLabel(Map<String, Object> properties, String... labels) {
		StringBuilder builder = new StringBuilder();
		builder.append("MERGE ");
		builder.append(generateNodeWithLabel(properties, labels));
		builder.append(";");
		return builder.toString();
	}
	
	public String generateNodeWithLabel(Map<String, Object> properties, String... labels) {
		StringBuilder builder = new StringBuilder();
		builder.append("(");
		if (labels.length > 0) {
			builder.append(getIdentifier(labels));
		}

		for (String label : labels) {
			builder.append(":" + label);
		}
		builder.append(" {");
		for (String key : properties.keySet()) {
			builder.append(key + ": ");
			
			Object o = properties.get(key);
			if (o instanceof Integer) {
				builder.append(o);
			} else {
				builder.append("\"" + o +"\"");
			}
			builder.append(", ");
		}
		builder.delete(builder.length() - 2, builder.length());
		builder.append("})");
		return builder.toString();
	}
	
	private String getIdentifier(String[] labels) {
		String mostSpecificLabel = labels[labels.length-1];
		switch (mostSpecificLabel) {
		case "Person":
			return "p";
		case "Skill":
			return "s";
		case "SkillCategory":
			return "c";
		default:
			throw new IllegalArgumentException("Unknown label: " + mostSpecificLabel);
		}
	}

	public String generateCypher(Person person) {
		return generateCreateNodeWithLabel(getProperties(person), "Person");
	}
	
	private Map<String, Object> getProperties(Person person) {
		Map<String, Object> properties = new LinkedHashMap<>();
		properties.put("firstname", person.getFirstname());
		properties.put("lastname", person.getLastname());
		properties.put("year_of_birth", person.getYearOfBirth());
		return properties;
	}
	
	private Map<String, Object> getProperties(SkillCategory skillCategory) {
		Map<String, Object> properties = new LinkedHashMap<>();
		properties.put("name", skillCategory.name());
		return properties;
	}
	
	private Map<String, Object> getProperties(Skill skill) {
		Map<String, Object> properties = new LinkedHashMap<>();
		properties.put("name", skill.getName());
		return properties;
	}
	
	public String generateNode(SkillCategory skillCategory) {
		return generateNodeWithLabel(getProperties(skillCategory), "SkillCategory");
	}
	
	public String generateCypher(SkillCategory skillCategory) {
		return generateCreateNodeWithLabel(getProperties(skillCategory), "SkillCategory");
	}
	
	public String generateNode(Skill skill) {
		return generateNodeWithLabel(getProperties(skill), "Skill");
	}
	
	public String generateNode(Person person) {
		return generateNodeWithLabel(getProperties(person), "Person");
	}

	/** requisite node for {@link SkillCategory} created. */
	public String generateCypher(Skill skill) {
		StringBuilder builder = new StringBuilder();
		builder.append(generateCreateNodeWithLabel(getProperties(skill), "Skill"));
		builder.append("\nMATCH ");
		builder.append(generateNode(skill.getCategory()));
		builder.append(", ").append(generateNode(skill));
		builder.append(" MERGE (s)-[:IS_A]->(c)");
		builder.append(";");
		return builder.toString();
	}

	public String generateCypher(Person person, Skill skill) {
		StringBuilder builder = new StringBuilder();
		builder.append("\nMATCH ");
		builder.append(generateNode(skill));
		builder.append(", ").append(generateNode(person));
		builder.append(" MERGE (p)-[:HAS_SKILL");
		if (skill.getLevel() != null) {
			builder.append(" {level:\""+ skill.getLevel().name() +"\"}");
		}
		builder.append("]->(s);");
		return builder.toString();
	}
	
}
