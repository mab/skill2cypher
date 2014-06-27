package de.saxsys.skill2cypher.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import de.saxsys.skill2cypher.model.SkillCategory;

public class SkillCategoryTest {

	@Test
	public void testGetSkillCategoryForKnownName() {
		assertEquals(SkillCategory.API, SkillCategory.getCategoryFromName("APIs"));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testGetSkillCategoryForUnknownNameShoulThrowException() {
		SkillCategory.getCategoryFromName("XYZ");
	}
	
}
