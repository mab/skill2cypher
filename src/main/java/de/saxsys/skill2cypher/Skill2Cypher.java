package de.saxsys.skill2cypher;

import static de.saxsys.skill2cypher.model.SkillCategory.Ignore;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import de.saxsys.skill2cypher.generator.CypherGenerator;
import de.saxsys.skill2cypher.model.Skill;
import de.saxsys.skill2cypher.model.SkillCategory;
import de.saxsys.skill2cypher.model.SkillProfile;
import de.saxsys.skill2cypher.parser.ProfileParser;

public class Skill2Cypher {

	public static void main(String[] args) throws Exception {
		System.out.println("Skill2Cypher");
		System.out.println("Parsing profiles");
		List<SkillProfile> profiles = new ArrayList<>();
		profiles.add(new ProfileParser("BaumgartMatthias.docx").parse());
		profiles.add(new ProfileParser("RichardKaufmann.docx").parse());
		profiles.add(new ProfileParser("SchmeckSebastian.docx").parse());
 
		System.out.println("Finished parsing profiles");
		
		System.out.println("Creating cypher file");
		File file = new File("target/cypher.txt");
		if (file.exists()) {
			file.delete();
		}
		
		if (!file.exists()) {
			file.createNewFile();
		}
		
		CypherGenerator generator = new CypherGenerator();
		
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
		
		// Delete all nodes and relationships
		out.println("// Delete all nodes and relationships");
		out.println("MATCH (n) OPTIONAL MATCH (n)-[r]-() DELETE n,r;");
		out.println();
		
		// Categories
		out.println("// Create Skill Categories");
		out.println("CREATE CONSTRAINT ON (s:Skill) ASSERT s.name IS UNIQUE;");
		for (SkillCategory category : SkillCategory.values()) {
			if(!category.equals(Ignore)) {
				out.println(generator.generateCypher(category));				
			}
		}
		out.println();
		
		// Skillprofile
		List<Skill> createdSkills = new ArrayList<>();
		for (SkillProfile profile : profiles) {
			out.println("// Profile of " + profile.getPerson().getFirstname() + " " + profile.getPerson().getLastname());
			out.println(generator.generateCypher(profile.getPerson()));
			for (Skill skill : profile.getSkills()) {
				if (!createdSkills.contains(skill)) {
					out.println(generator.generateCypher(skill));
					createdSkills.add(skill);
				}
				out.println(generator.generateCypher(profile.getPerson(), skill));
			}
			out.println();
		}
		
		out.close();
		System.out.println("Finished!");
	}

}
