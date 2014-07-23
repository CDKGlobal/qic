package com.testing123.vaadin;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

public class QueryDatabaseTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	
	}

	@Test
	public void testGetNCLOCReturnsNoDuplicates() {
		Map<String, Double> list = new QueryDatabase().getNCLOC(new ConvertDate("2014-07-07T06-09-17-0700"));
		assertTrue(list.size() > 700);
	}
	
	@Test
	public void test() {
		Map<String, Double> list = new QueryDatabase().getDeltaComplexity(new ConvertDate("2014-07-07T06-09-17-0700"), new ConvertDate("2014-07-10T06-07-56-0700"));
	}
	
	@Test
	public void test2() {
		Map<String, Double> list = new QueryDatabase().getComplexity(new ConvertDate("2014-07-07T06-09-17-0700"));
		assertTrue(list.size() > 700);
	}
}
