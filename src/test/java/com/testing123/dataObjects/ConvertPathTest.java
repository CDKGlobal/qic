package com.testing123.dataObjects;

import static org.junit.Assert.*;

import org.junit.Test;

public class ConvertPathTest {

	@Test
	public void testEmptyStringEqualsEmptyString() {
		assertTrue(new ConvertPath("").equals( new ConvertPath("")));
	}
	
	@Test
	public void testStringEqualsSameString(){
		assertTrue(new ConvertPath("asdf/./sdad").equals(new ConvertPath("asdf/./sdad")));
	}
	
	@Test
	public void testOldSonarFormatEqualsNewSonarFormat(){
		assertTrue(new ConvertPath("com.vaadin:QIC2:com.testing123.vaadin.Msr ").equals(new ConvertPath("com.vaadin:QIC2:src/main/java/com/testing123/vaadin/Msr.java")));
	}
	
	@Test
	public void testFileNameMustMatch(){
		assertFalse(new ConvertPath("com.vaadin:QIC2:com.testing123.vaadin.MsrTest").equals( new ConvertPath("com.vaadin:QIC2:src/main/java/com/testing123/vaadin/Msr.java")));
	}
	
	@Test
	public void testNoProjectInfo(){
		assertTrue(new ConvertPath("com.vaadin:QIC2:com.testing123.vaadin.Msr ").equals(new ConvertPath("QIC/trunk/src/main/java/com/testing123/vaadin/Msr.java")));
	}
	
	@Test
	public void testGetPath(){
		assertEquals("com/testing123/vaadin/Msr.java",new ConvertPath("com.vaadin:QIC2:com.testing123.vaadin.Msr ").getFisheyePath());
		assertEquals("com.vaadin:QIC2:com.testing123.vaadin.Msr",new ConvertPath("com.vaadin:QIC2:com.testing123.vaadin.Msr ").getSonarPath());
		
		assertEquals("trunk/src/main/java/com/testing123/vaadin/Msr.java",new ConvertPath("trunk/src/main/java/com/testing123/vaadin/Msr.java").getFisheyePath());
		assertEquals("trunk.src.main.java.com.testing123.vaadin.Msr",new ConvertPath("trunk/src/main/java/com/testing123/vaadin/Msr.java").getSonarPath());
	}
	
	@Test
	public void sample(){
		assertEquals("src/main/java/com/testing123/controller/UIState.java",new ConvertPath("com.vaadin:QIC2:src/main/java/com/testing123/controller/UIState.java").getFisheyePath());
	}
	
	@Test
	public void getOriginalPath(){
		ConvertPath qic = new ConvertPath("com.vaadin:QIC2:com.testing123.vaadin.Msr ");
		assertEquals("com.vaadin:QIC2:com.testing123.vaadin.Msr", qic.getOriginalPath());
	}
	
	@Test
	public void testNotEqual(){
		ConvertPath qic = new ConvertPath("com.vaadin:QIC2:com.testing123.vaadin.Msr ");
		ConvertPath qic2 = new ConvertPath("com.vaadin:QIC2:com.testing123.vaadin.Msr ");
		assertEquals(qic, qic);
		assertFalse(qic.equals("a"));
		String n = null;
		assertFalse(qic.equals(n));
		ConvertPath nu = null;
		assertFalse(qic.equals(nu));
		assertTrue(qic.equals(qic2));
	}
	
	@Test
	public void testStringEquals(){
		assertTrue(new ConvertPath("com.vaadin:QIC2:com.testing123.vaadin.Msr ").equals("com.vaadin:QIC2:src/main/java/com/testing123/vaadin/Msr.java"));
	}

}
