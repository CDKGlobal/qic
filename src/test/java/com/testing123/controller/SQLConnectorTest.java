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
		ResultSet rs = SQLConnector.basicQuery(query);
		try {
			assertTrue(rs.next());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void TestBasicQueryReturnsNonNullResult() {
		testQuery("SHOW TABLES");
	}
	
	@Test
	public void TestBasicDataQueryReturnsNonNullResult() {
		testQuery("SELECT * FROM 2014_07_08T06_07_31_0700");
	}
	
	@Test
	public void TestProcessReturnsNonEmptyList() {
		ResultSet rs = SQLConnector.basicQuery("SELECT * FROM 2014_07_08T06_07_31_0700");
		SQLConnector.process(rs);
	}
}
