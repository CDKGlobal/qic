package com.testing123.vaadin;

import static org.junit.Assert.*;

import org.junit.Test;

public class RegexUtilTest {
	
	@Test
	public void testSampleInput(){
		String text = "\"Websites/Tetra/trunk/modules/dealer-service/src/main/java/com/cobaltgroup/websites/dealerservice/util/LayoutXmlParser.java\",\"ghoshs\",142,9,\"true\"";
		assertTrue(RegexUtil.isRevisionData(text));
		}
	
	@Test
	public void testPassInNull() {
		assertFalse(RegexUtil.isRevisionData(null));
	}
	
	@Test
	public void testNoAuthor(){
		String text = "\"Websites/Tetra/trunk/modules/dealer-service/src/main/java/com/cobaltgroup/websites/dealerservice/util/LayoutXmlParser.java\",142,9";
		assertFalse(RegexUtil.isRevisionData(text));

	}
	
	@Test
	public void testNullDate(){
		assertFalse(RegexUtil.isCorrectDateFormat(null));
	}
	
	@Test
	public void testIncorrectDates(){
		assertFalse(RegexUtil.isCorrectDateFormat("01-01-2014"));
		assertFalse(RegexUtil.isCorrectDateFormat("2014/01/10"));
		assertFalse(RegexUtil.isCorrectDateFormat("1111-13-01"));
		assertFalse(RegexUtil.isCorrectDateFormat("1111-00-00"));

	}
	
	@Test
	public void testCorrectDateFormat(){
		assertTrue(RegexUtil.isCorrectDateFormat("2014-12-30"));
	}
	
	@Test
	public void testCasesUsedInOtherTests(){
		assertTrue(RegexUtil.isRevisionData("\"ui/vaadin-ui/fake.java\",\"blum\",12,32,\"true\""));
		assertTrue(RegexUtil.isRevisionData("\"ui/vaadin-ui/fake.java\",\"weiyoud\",10,11,\"true\""));
		assertTrue(RegexUtil.isRevisionData("\"ui/vaadin-ui/fake.java\",\"author\",13,12,\"true\""));
		assertTrue(RegexUtil.isRevisionData("\"path.java\",\"author\",-1,-1,\"true\""));
		assertTrue(RegexUtil.isRevisionData("\"path.java\",\"author\",-1,-1,\"false\""));
	}

}
