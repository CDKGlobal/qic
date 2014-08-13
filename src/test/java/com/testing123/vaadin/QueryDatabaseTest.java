package com.testing123.vaadin;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.testing123.dataObjects.ConvertDate;
import com.testing123.dataObjects.ConvertProject;
import com.testing123.dataObjects.QueryData;

public class QueryDatabaseTest {
	private static QueryDatabase q;
	private Set<ConvertProject> s;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		q = new QueryDatabase();
	}
	
	@Before
	public void before() {
		s = new HashSet<ConvertProject>();
	}

	@Test
	public void TestNonExistentProjectReturnsEmptySet() {
		ConvertDate start = new ConvertDate("2014-08-04");
		ConvertDate end = new ConvertDate("2014-08-03");
		s.add(new ConvertProject("0", "0", 0, "0"));
		Set<QueryData> data = q.getDataSet(start, end, s, false);
		assertEquals(0, data.size());
	}
	
	@Test
	public void TestExistentProjectReturnsNonEmptySet() {
		ConvertDate start = new ConvertDate("2014-08-04");
		ConvertDate end = new ConvertDate("2014-08-03");
		s.add(new ConvertProject("0", "0", 17271, "0"));
		Set<QueryData> data = q.getDataSet(start, end, s, false);
		assertTrue(0 < data.size());
	}

	@Test
	public void TestNonExistentFinalDateReturnsNonEmptySet() {
		ConvertDate start = new ConvertDate("2014-01-04");
		ConvertDate end = new ConvertDate("2014-02-05");
		s.add(new ConvertProject("0", "0", 17271, "0"));
		Set<QueryData> data = q.getDataSet(start, end, s, false);
		assertTrue(0 < data.size());
	}
	
	@Test
	public void TestExistentDateReturnsNonEmptySet() {
		ConvertDate start = new ConvertDate("2014-08-02");
		ConvertDate end = new ConvertDate("2014-08-03");
		s.add(new ConvertProject("0", "0", 17271, "0"));
		Set<QueryData> data = q.getDataSet(start, end, s, false);
		assertTrue(0 < data.size());
	}
}
