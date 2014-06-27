package de.saxsys.skill2cypher.generator;

import static org.junit.Assert.assertEquals;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import de.saxsys.skill2cypher.model.Person;
import de.saxsys.skill2cypher.model.Skill;
import de.saxsys.skill2cypher.model.SkillCategory;
import de.saxsys.skill2cypher.model.SkillLevel;

public class CypherGeneratorTest {
	
	private CypherGenerator cypherGenerator;
	private Map<String, Object> properties;

	@Before
	public void setUp() {
		cypherGenerator = new CypherGenerator();
		properties = new LinkedHashMap<>();
	}
	
	@Test
	public void testGenerateNodeWithNoLabelAndOneProperty() {
		properties.put("name", "A Name");
		String cypher = cypherGenerator.generateCreateNodeWithLabel(properties);
		String expectedCypher = "MERGE ( {name: \"A Name\"});";
		assertEquals(expectedCypher, cypher);
	}
	
	@Test
	public void testGenerateNodeWithNoLabelAndMultipleProperties() {
		properties.put("name", "A Name");
		properties.put("p2", "p2 Value");
		properties.put("p3", "p3 Value äöüß");
		String cypher = cypherGenerator.generateCreateNodeWithLabel(properties);
		String expectedCypher = "MERGE ( {name: \"A Name\", p2: \"p2 Value\", p3: \"p3 Value äöüß\"});";
		assertEquals(expectedCypher, cypher);
	}

	@Test
	public void testGenerateNodeWithLabelAndMultipleProperties() {
		properties.put("name", "A Name");
		properties.put("p2", "p2 Value");
		properties.put("p3", "p3 Value äöüß");
		String cypher = cypherGenerator.generateCreateNodeWithLabel(properties, "Person");
		String expectedCypher = "MERGE (p:Person {name: \"A Name\", p2: \"p2 Value\", p3: \"p3 Value äöüß\"});";
		assertEquals(expectedCypher, cypher);
	}
	
	@Test
	public void testGenerateNodeWithMultipleLabelsAndMultipleProperties() {
		properties.put("name", "A Name");
		properties.put("p2", "p2 Value");
		properties.put("p3", "p3 Value äöüß");
		String cypher = cypherGenerator.generateCreateNodeWithLabel(properties, "Skill", "SkillCategory");
		String expectedCypher = "MERGE (c:Skill:SkillCategory {name: \"A Name\", p2: \"p2 Value\", p3: \"p3 Value äöüß\"});";
		assertEquals(expectedCypher, cypher);
	}
	
	@Test
	public void testGenerateNodeFromSkillCategory() {
		String cypher = cypherGenerator.generateCypher(SkillCategory.API);
		String expectedCypher = "MERGE (c:SkillCategory {name: \""+ SkillCategory.API +"\"});";
		assertEquals(expectedCypher, cypher);
	}

	@Test
	public void testGenerateNodeFromPerson() {
		Person person = new Person();
		person.setFirstname("Matthias");
		person.setLastname("Baumgart");
		person.setYearOfBirth(1983);
		String cypher = cypherGenerator.generateCypher(person);
		String expectedCypher = "MERGE (p:Person {firstname: \"Matthias\", lastname: \"Baumgart\", year_of_birth: 1983});";
		assertEquals(expectedCypher, cypher);
	}
	
	@Test
	public void testGenerateCypherFromSkill() {
		Skill skill = new Skill(SkillCategory.Betriebssystem, "Mac OS X", SkillLevel.Professionell);
		String cypher = cypherGenerator.generateCypher(skill);
		String expectedCypher = "MERGE (s:Skill {name: \"Mac OS X\"});\nMATCH (c:SkillCategory {name: \"Betriebssystem\"}), (s:Skill {name: \"Mac OS X\"}) MERGE (s)-[:IS_A]->(c);";
		assertEquals(expectedCypher, cypher);
	}
	
	@Test
	public void testGenerateCypherFromSkillAndPerson() {
		Person person = new Person();
		person.setFirstname("Matthias");
		person.setLastname("Baumgart");
		person.setYearOfBirth(1983);
		Skill skill = new Skill(SkillCategory.Betriebssystem, "Mac OS X", SkillLevel.Professionell);
		String cypher = cypherGenerator.generateCypher(person, skill);
		String expectedCypher = "\nMATCH (s:Skill {name: \"Mac OS X\"}), (p:Person {firstname: \"Matthias\", lastname: \"Baumgart\", year_of_birth: 1983}) MERGE (p)-[:HAS_SKILL {level:\"Professionell\"}]->(s);";
		assertEquals(expectedCypher, cypher);
	}
}
