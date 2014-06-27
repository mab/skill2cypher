package de.saxsys.skill2cypher.parser;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.activation.UnsupportedDataTypeException;
import javax.xml.bind.JAXBElement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.docx4j.TextUtils;
import org.docx4j.wml.Drawing;
import org.docx4j.wml.P;
import org.docx4j.wml.R;
import org.docx4j.wml.Tbl;
import org.docx4j.wml.Tc;
import org.docx4j.wml.Tr;

public abstract class Parser {
	
	protected static final Logger LOG = LogManager.getLogger(Parser.class);

	
	public List<String> splitStringByCommaAndSpace(String value) {
		return Arrays.asList(value.trim().split("\\s*,\\s*"));
	}
	
	protected List<String> getContentFromTable(Tbl table) throws Exception {
		List<String> result = new ArrayList<>();
		
		List<Object> content = table.getContent();
		for (Object tableContent : content) {
			if (tableContent instanceof Tr) {
				Tr tr = (Tr)tableContent;
				for (Object rowContent : tr.getContent()) {
					@SuppressWarnings("unchecked")
					JAXBElement<Tc> j = (JAXBElement<Tc>)rowContent;
					for (Object columnContent : j.getValue().getContent()) {
						if (columnContent instanceof P) {
							P p = (P)columnContent;
							if (p.getContent().isEmpty() || isImage(p.getContent())) {
								continue;
							}
							
							// LOG.debug("Content size: " + p.getContent().size() + " toString: " + columnContent);

							StringWriter w = new StringWriter();
							TextUtils.extractText(p, w);
							String columnText = w.toString();
							if (!columnText.isEmpty()) {
								// adding content to result list
								result.add(columnText);
							}
						} else {
							throw new UnsupportedDataTypeException(columnContent.getClass() + " not supported yet!");
						}
					}
				}
			}
		}
		
		return result;
	}
	
	protected Map<String, List<String>> getContentFrom2ColumnTable(Tbl table) throws Exception {
		Map<String, List<String>> result = new HashMap<>();
		
		List<Object> content = table.getContent();
		for (Object tableContent : content) {
			if (tableContent instanceof Tr) {
				Tr tr = (Tr)tableContent;
				if (tr.getContent().size() != 2) {
					LOG.error("Table contains more than 2 columns! Data might be lost");
					continue;
				}
				
				// Just process Data where we can find an Key in the second column!
				@SuppressWarnings("unchecked")
				JAXBElement<Tc> key = (JAXBElement<Tc>)tr.getContent().get(1);
				if (isEmpty(key.getValue().getContent()) || isLegend(key.getValue().getContent())) {
					continue;
				}
				
				// get key
				String keyValue = key.getValue().getContent().get(0).toString().trim();
				
				// get value
				@SuppressWarnings("unchecked")
				JAXBElement<Tc> value = (JAXBElement<Tc>)tr.getContent().get(0);
				String stringValue = value.getValue().getContent().get(0).toString();
				List<String> listStringValues = splitStringByCommaAndSpace(stringValue);
				
				LOG.debug(keyValue + ": " + listStringValues);
				result.put(keyValue, listStringValues);
			}
		}
		return result;
	}
	
	/** Checks whether the key is empty. Word puts empty P. */
	private boolean isEmpty(List<Object> content) {
		if (content.isEmpty()) {
			return true;
		} else {
			Object object = content.get(0);
			if (object == null || object.toString().trim().isEmpty()) {
				return true;
			} else {
				return false;
			}
		}
		
	}

	private boolean isLegend(List<Object> content) {
		if (!content.isEmpty() && content.get(0).toString().equals("Legende")) {
			return true;
		} else {
			return false;
		}
	}

	private boolean isImage(List<Object> content) {
		boolean result = false;
		for (Object o : content) {
			try {
				R r = (R)o;
				@SuppressWarnings("unchecked")
				JAXBElement<Drawing> d = ((JAXBElement<Drawing>)r.getContent().get(0));
				if (d.getDeclaredType() == Drawing.class) {
					result = true;
					break;
				}
			} catch (Exception e) {
				// do nothing
			}
		}
		return result;
	}
	
}
