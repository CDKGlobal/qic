package com.testing123.dataObjects;

import static org.junit.Assert.*;

import org.junit.Test;

public class ConvertPathTest {

	@Test
	public void testEmptyStringEqualsEmptyString() {
		assertEquals(new ConvertPath(""), new ConvertPath(""));
	}
	
	@Test
	public void testStringEqualsSameString(){
		assertEquals(new ConvertPath("asdf/./sdad"), new ConvertPath("asdf/./sdad"));
	}
	
	@Test
	public void testOldSonarFormatEqualsNewSonarFormat(){
		assertEquals(new ConvertPath("com.vaadin:QIC2:com.testing123.vaadin.Msr "), new ConvertPath("com.vaadin:QIC2:src/main/java/com/testing123/vaadin/Msr.java"));
	}
	
	@Test
	public void testFileNameMustMatch(){
		assertFalse(new ConvertPath("com.vaadin:QIC2:com.testing123.vaadin.MsrTest").equals( new ConvertPath("com.vaadin:QIC2:src/main/java/com/testing123/vaadin/Msr.java")));
	}
	
	@Test
	public void testNoProjectInfo(){
		assertEquals(new ConvertPath("com.vaadin:QIC2:com.testing123.vaadin.Msr "), new ConvertPath("QIC/trunk/src/main/java/com/testing123/vaadin/Msr.java"));
	}
	
	@Test
	public void testGetPath(){
		assertEquals("com/testing123/vaadin/Msr",new ConvertPath("com.vaadin:QIC2:com.testing123.vaadin.Msr ").getFisheyePath());
		assertEquals("com.vaadin:QIC2:com.testing123.vaadin.Msr",new ConvertPath("com.vaadin:QIC2:com.testing123.vaadin.Msr ").getSonarPath());
	}
	
	

}
