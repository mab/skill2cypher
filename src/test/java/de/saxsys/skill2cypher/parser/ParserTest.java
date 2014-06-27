package de.saxsys.skill2cypher.parser;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;

import de.saxsys.skill2cypher.parser.Parser;

public class ParserTest {

	@Test
	public void testSplitStringByCommaAndSpaceShouldReturnAListOfStrings() {
		Parser mock = Mockito.mock(Parser.class, Mockito.CALLS_REAL_METHODS);
		List<String> result = mock.splitStringByCommaAndSpace("Value 1, Value 2, Value 3 (F)");
		List<String> expected = new ArrayList<>();
		expected.add("Value 1");
		expected.add("Value 2");
		expected.add("Value 3 (F)");
		assertEquals(expected, result);
	}
	
}
