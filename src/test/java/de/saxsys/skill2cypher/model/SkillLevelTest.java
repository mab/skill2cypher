package de.saxsys.skill2cypher.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import de.saxsys.skill2cypher.model.SkillLevel;

public class SkillLevelTest {
	
	@Test
	public void testGetSkillLevelForKnownAbrv() {
		assertEquals(SkillLevel.Professionell, SkillLevel.getSkillLevel("P"));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testGetSkillLevelForUnknownAbrvShoulThrowException() {
		SkillLevel.getSkillLevel("A");
	}

}
