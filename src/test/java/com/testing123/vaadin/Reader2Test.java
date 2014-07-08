package com.testing123.vaadin;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

public class Reader2Test {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Test
	public void TestReadNonValidMetricReturnsEmptySet() {
		assertEquals(0, Reader2.getData(null, "").size());
		assertEquals(0, Reader2.getData("", "").size());
		assertEquals(0, Reader2.getData("", null).size());
		assertEquals(0, Reader2.getData(null, null).size());
		assertEquals(0, Reader2.getData("ncloc", " ").size());
		assertEquals(0, Reader2.getData("fee", "foo").size());
	}
	
	@Test
	public void TestReadTwoMetricReturnsNonEmptySet() {
		assertTrue(0 == Reader2.getData("ncloc", "complexity").size());
		assertTrue(0 == Reader2.getData("complexity", "comment_lines").size());
	}
	
	
}
