package com.testing123.dataObjects;

import static org.junit.Assert.*;

import org.junit.Test;

public class RevisionDataTest {

	@Test
	public void testCreatable() {
		RevisionData rd = new RevisionData();
		assertEquals(null,rd.getAuthor());
		assertEquals(-1,rd.getChurn());
		assertEquals(null,rd.getFisheyePath());
	}
	
	@Test
	public void testSampleInput(){
		String text = "\"Websites/Tetra/trunk/modules/dealer-service/src/main/java/com/cobaltgroup/websites/dealerservice/util/LayoutXmlParser.java\",\"ghoshs\",142,9";
		RevisionData rd = new RevisionData(text);
		assertEquals("ghoshs",rd.getAuthor());
		assertEquals(151,rd.getChurn());
		assertEquals("Websites/Tetra/trunk/modules/dealer-service/src/main/java/com/cobaltgroup/websites/dealerservice/util/LayoutXmlParser.java",rd.getFisheyePath());
	}
	
	@Test
	public void testToString(){
		String text = "\"ui/vaadin-ui/fake.java\",\"blum\",123,321";
		RevisionData rd = new RevisionData(text);
		assertEquals("ui/vaadin-ui/fake.java", rd.getFisheyePath());
		assertEquals("blum", rd.getAuthor());
		assertEquals(444,rd.getChurn());
		assertEquals("RevisionData [Fisheyepath=ui/vaadin-ui/fake.java, churn=444, author=blum]",rd.toString());
	}
}
