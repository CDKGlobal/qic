package com.testing123.controller;

import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.BeforeClass;
import org.junit.Test;

public class SQLConnectorTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	private void testQuery(String query) {
		ResultSet rs = new SQLConnector().basicQuery(query);
		try {
			assertTrue(rs.next());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void TestSQLQueryReturnsNonNullResult() {
		testQuery("SHOW TABLES");
	}
	
	@Test
	public void TestSelectQueryReturnsNonNullResult() {
		testQuery("SELECT * FROM authors");
	}
	
	@Test
	public void TestInvalidQueryReturnsNull() {
//		testQuery();
	}
}
