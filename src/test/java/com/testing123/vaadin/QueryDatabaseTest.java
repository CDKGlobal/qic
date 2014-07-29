package com.testing123.vaadin;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

public class QueryDatabaseTest {
	private static QueryDatabase q;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		q = new QueryDatabase();
	}

	@Test (expected = IllegalArgumentException.class)
	public void TestInvalidDateReturnsException() {
		Map<String, Double> list = q.getNCLOC(new ConvertDate("2014-07-7T06-09-17-0700"), null, null);
	}
	
	@Test
	public void TestGetEmptyAuthorsListReturnsEmptyMap() {
		Map<String, Double> list = q.getNCLOC(new ConvertDate("2014-07-07T06-09-17-0700"), new HashSet<String>(), new HashSet<ConvertProject>());
		assertEquals(0, list.size());
	}
	
	@Test
	public void TestGetEmptyProjectsListReturnsEmptyMap() {
		Map<String, Double> list = q.getDeltaComplexity(new ConvertDate("2014-07-07T06-09-17-0700"), new ConvertDate("2014-07-10T06-07-56-0700"), new HashSet<String>(), new HashSet<ConvertProject>());
		assertEquals(0, list.size());
	}
}
