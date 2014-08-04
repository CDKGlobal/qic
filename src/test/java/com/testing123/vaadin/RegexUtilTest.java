package com.testing123.vaadin;

import static org.junit.Assert.*;

import org.junit.Test;

import com.testing123.dataObjects.RevisionData;

public class RegexUtilTest {
	
	@Test
	public void testSampleInput(){
		String text = "\"Websites/Tetra/trunk/modules/dealer-service/src/main/java/com/cobaltgroup/websites/dealerservice/util/LayoutXmlParser.java\",\"ghoshs\",142,9";
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

}
