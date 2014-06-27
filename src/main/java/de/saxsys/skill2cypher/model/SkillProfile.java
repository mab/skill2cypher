package de.saxsys.skill2cypher.model;

import java.util.ArrayList;
import java.util.List;

public class SkillProfile {
	
	private Person person;
	private List<Skill> skills = new ArrayList<>();
	private List<Project> projects = new ArrayList<>();
	
	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public List<Skill> getSkills() {
		return skills;
	}

	public void setSkills(List<Skill> skills) {
		this.skills = skills;
	}

	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

}
