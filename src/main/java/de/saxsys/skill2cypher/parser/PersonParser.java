package de.saxsys.skill2cypher.parser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.docx4j.wml.Tbl;

import de.saxsys.skill2cypher.model.Person;

public class PersonParser extends Parser {

	static final Logger LOG = LogManager.getLogger(PersonParser.class);
	
	public Person getPerson(Tbl table) throws Exception {
		Person person = new Person();
		
		List<String> tableContent = getContentFromTable(table);
		LOG.info("Persontable content: " + tableContent);

		if (tableContent.isEmpty()) { 
			LOG.error("Parsed nothing from person table");
			return null;
		}
		String[] name = tableContent.get(0).split("\\s+");
		switch (name.length) {
		case 0:
			LOG.error("No name parsed.");
			break;
		case 1:
			person.setLastname(name[0]);
			break;
		case 2:
			person.setFirstname(name[0]);
			person.setLastname(name[1]);
			break;
		default:
			person.setFirstname(name[0]);
			// TODO implement setMiddlename
			person.setLastname(name[name.length-1]);
			break;
		}
		
		person.setYearOfBirth(getYearOfBirth(tableContent));
		person.setLanguages(getLanguages(tableContent));
		return person;
	}
	
	private int getYearOfBirth(List<String> tableContent) {
		for (Iterator<String> iterator = tableContent.iterator(); iterator.hasNext();) {
			String string = (String) iterator.next();
			if (string.equals("Geburtsjahr:")) {
				try {
					return Integer.valueOf(((String)iterator.next())).intValue();
				} catch (Exception e) {
					break;
				}
			}
		}
		return 0;
	}
	
	private List<String> getLanguages(List<String> tableContent) {
		List<String> result = new ArrayList<>();
		for (Iterator<String> iterator = tableContent.iterator(); iterator.hasNext();) {
			String string = (String) iterator.next();
			if (string.equals("Sprachen:")) {
				String languages = (String) iterator.next();
				result.addAll(splitStringByCommaAndSpace(languages));
				break;
			}
		}
		LOG.debug("Sprachen: " + result);
		return result;
	}
	
}
