package de.saxsys.skill2cypher.parser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBElement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.wml.Body;
import org.docx4j.wml.CTBookmark;
import org.docx4j.wml.CTMarkupRange;
import org.docx4j.wml.Tbl;

import de.saxsys.skill2cypher.model.SkillProfile;


public class ProfileParser {
	
	static final Logger LOG = LogManager.getLogger(ProfileParser.class);

	private List<Object> document;
	private List<Tbl> tables;
	
	public ProfileParser(String filename) throws Docx4JException {
		document = loadDocument(filename);
	}
	
	private List<Object> loadDocument(String filename) throws Docx4JException {
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		InputStream is = classloader.getResourceAsStream(filename);
		WordprocessingMLPackage mlPackage = WordprocessingMLPackage.load(is);
		Body document = mlPackage.getMainDocumentPart().getJaxbElement().getBody();
		return document.getContent();
	}
	
	public SkillProfile parse() throws Exception {
		SkillProfile result = new SkillProfile();
		// getContent
		tables = getTablesFromDocument(document);
		// parseTables
		// Person Data is expected to be in the first table.
		result.setPerson(new PersonParser().getPerson(tables.get(0)));
		// Skills Data is expected to be in the third table.
		result.setSkills(new SkillParser().getSkills(tables.get(2)));
		tables.remove(0);
		tables.remove(0);
		tables.remove(0);
		result.setProjects(new ProjectParser().getProjects(tables));
		return result;
	}

	private List<Tbl> getTablesFromDocument(List<Object> document) {
		List<Tbl> result = new ArrayList<>();
		for (Object o : document) {
			 if (o instanceof JAXBElement) {
				@SuppressWarnings("rawtypes")
				JAXBElement j = (JAXBElement)o;
				Object value = j.getValue();
				if (value instanceof Tbl) {
					// adding table to result list
					result.add((Tbl)value);
				} else if (value instanceof CTBookmark){
					// not needed
					// CTBookmark bookmark = (CTBookmark)value;
					// System.out.println("Bookmark: " + bookmark.getName());
				} else if (value instanceof CTMarkupRange) {
					// not needed
					// CTMarkupRange markupRange = (CTMarkupRange)value;
				} else {
					LOG.error("Found content which is from a not known type. Value: " + value);
				}
			}
		}
		return result;
	}
	
}
