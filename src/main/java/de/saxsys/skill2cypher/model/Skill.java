package de.saxsys.skill2cypher.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Skill {

	private SkillCategory category;
	private String name;
	private SkillLevel level;

	public Skill() {
	}

	public Skill(SkillCategory category, String name, SkillLevel level) {
		this.category = category;
		this.name = name;
		this.level = level;
	}
	
	public Skill(String category, String name, SkillLevel level) {
		this.category = SkillCategory.getCategoryFromName(category);
		this.name = name;
		this.level = level;
	}

	public SkillCategory getCategory() {
		return category;
	}

	public void setCategory(SkillCategory category) {
		this.category = category;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public SkillLevel getLevel() {
		return level;
	}
	
	public void setLevel(SkillLevel level) {
		this.level = level;
	}

	@Override
	public String toString() {
		return "Skill [category=" + category + ", name=" + name + ", level=" + level + "]";
	}
	
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public boolean equals(Object obj) {
//		return EqualsBuilder.reflectionEquals(this, obj);
		if (obj == null) { return false; }
		if (obj == this) { return true;	}
		if (obj.getClass() != getClass()) {	return false; }
		
		Skill skill = (Skill) obj;
		return new EqualsBuilder()
				.append(category, skill.category)
				.append(name, skill.name)
				.append(level, skill.level)
				.isEquals();
	}

}
