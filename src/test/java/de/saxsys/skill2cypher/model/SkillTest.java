package de.saxsys.skill2cypher.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

import de.saxsys.skill2cypher.model.Skill;
import de.saxsys.skill2cypher.model.SkillLevel;

public class SkillTest {

	@Test
	public void testIsEqualsShouldReturnTrueFor2EqualSkills() {
		Skill a = new Skill("Betriebssysteme", "Mac OS X", SkillLevel.Professionell);
		Skill b = new Skill("Betriebssysteme", "Mac OS X", SkillLevel.Professionell);
		assertTrue(a.equals(b));
	}
	
	@Test
	public void testIsEqualsShouldReturnFalseForSkillsWithDifferentLevel() {
		Skill a = new Skill("Betriebssysteme", "Mac OS X", SkillLevel.Professionell);
		Skill b = new Skill("Betriebssysteme", "Mac OS X", SkillLevel.Experte);
		assertFalse("Expected inequality but was equal.", a.equals(b));
	}
	
	
	@Test
	public void testDate() {
		Calendar cal = Calendar.getInstance();
		cal.set(2013, 12, 31);
		System.out.println(cal.getTimeInMillis());
	}
	
	
}
