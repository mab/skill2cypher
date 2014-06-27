package de.saxsys.skill2cypher.parser;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBElement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.docx4j.wml.Tbl;
import org.docx4j.wml.Tc;
import org.docx4j.wml.Tr;

import de.saxsys.skill2cypher.model.Project;

public class ProjectParser extends Parser {

	static final Logger LOG = LogManager.getLogger(ProjectParser.class);
	
	public List<Project> getProjects(List<Tbl> tables) throws Exception {
		List<Project> result = new ArrayList<>();
		LOG.debug("Tables size: " + tables.size());
		for (Tbl table : tables ) {
			result.addAll(getContentFromProjectTable(table));
		}
		return result;
	}
	
	private List<Project> getContentFromProjectTable(Tbl table) {
		List<Project> result = new ArrayList<>();
		
		List<Object> content = table.getContent();
		for (Object o : content) {
			if (o instanceof Tr) {
				List<Object> trContent = ((Tr)o).getContent();
				
				inner:
				for (Object trObj : trContent) {
					if (trObj instanceof JAXBElement) {
 						@SuppressWarnings("unchecked")
						JAXBElement<Tc> jaxbel = (JAXBElement<Tc>)trObj;
 						List<Object> innerContent = jaxbel.getValue().getContent();
 						
 						// Skip empty tables and meta information like "Projekterfahrung" or data privacy statement
 						if (innerContent.size() < 4 ) break inner;
 						LOG.debug("Size innercontent: " + innerContent.size() + ": " + jaxbel.getValue().getContent());
 						
 						Project project = new Project();
 						
 						// Projectname
 						String customer = innerContent.get(0).toString();
 						String projectname = innerContent.get(1).toString();
 						
 						LOG.debug("Project: " + projectname);
 						project.setName(projectname);
 						
 						LOG.debug("Customer: " + customer);
 						
 						result.add(project);
					}
				}
				
			}
		}
		return result;
	}

}
