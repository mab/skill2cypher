package de.saxsys.skill2cypher.parser;

import static de.saxsys.skill2cypher.model.SkillCategory.Ignore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.docx4j.wml.Tbl;

import de.saxsys.skill2cypher.model.Skill;
import de.saxsys.skill2cypher.model.SkillCategory;
import de.saxsys.skill2cypher.model.SkillLevel;

public class SkillParser extends Parser {

	static final Logger LOG = LogManager.getLogger(SkillParser.class);
	
	public List<Skill> getSkills(Tbl tbl) throws Exception {
		List<Skill> result = new ArrayList<>();
		Map<String, List<String>> skills = getContentFrom2ColumnTable(tbl);
		for (String key : skills.keySet()) {
			for (String skill : skills.get(key)) {
				Skill extractedSkill = extractSkill(key, skill);
				if (extractedSkill != null) {
					result.add(extractedSkill);
				}
			}
		}
		return result;
	}

	public Skill extractSkill(String category, String skill) {
		String skillName = extractSkillName(skill);
		SkillCategory categoryFromName = SkillCategory.getCategoryFromName(category);
		if (skillName == null || categoryFromName.equals(Ignore)) {
			return null;
		}
		
		Skill result = new Skill();
		result.setCategory(categoryFromName);
		result.setName(skillName);
		result.setLevel(extractSkillLevel(skill));
		return result;
	}

	private SkillLevel extractSkillLevel(String skill) {
		Pattern pattern = Pattern.compile("\\((G|F|P|E)\\)");
		Matcher matcher = pattern.matcher(skill);
		if (matcher.find()) {
		    return SkillLevel.getSkillLevel(matcher.group(1));
		}
		
		return null;
	}

	private String extractSkillName(String skill) {
		Pattern pattern = Pattern.compile("(.*)\\s*\\((G|F|P|E)\\)");
		Matcher matcher = pattern.matcher(skill);
		if (matcher.matches()) {
		    return matcher.group(1).trim();
		}
		LOG.error("No skill extracted! " + skill);
		return null;
	}

}
