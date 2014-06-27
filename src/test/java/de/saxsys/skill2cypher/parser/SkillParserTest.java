package de.saxsys.skill2cypher.parser;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import de.saxsys.skill2cypher.model.Skill;
import de.saxsys.skill2cypher.model.SkillLevel;
import de.saxsys.skill2cypher.parser.SkillParser;

public class SkillParserTest {

	@Test
	public void testExtractSkillForMacOsProfessionell() {
		SkillParser parser = new SkillParser();
		Skill skill = parser.extractSkill("Betriebssysteme", "Mac OS X (P)");
		Skill expected = new Skill("Betriebssysteme", "Mac OS X", SkillLevel.Professionell);
		assertEquals(expected, skill);
	}
	
}
