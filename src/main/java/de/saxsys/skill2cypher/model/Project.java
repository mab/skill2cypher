package de.saxsys.skill2cypher.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Project {

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Project [name=" + name + "]";
	}
	
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) { return false; }
		if (obj == this) { return true;	}
		if (obj.getClass() != getClass()) {	return false; }
		
		Project project = (Project) obj;
		return new EqualsBuilder()
				.append(name, project.name)
				.isEquals();
	}
	
}
