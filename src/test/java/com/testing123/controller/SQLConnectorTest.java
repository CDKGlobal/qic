package com.testing123.controller;

import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.BeforeClass;
import org.junit.Test;

public class SQLConnectorTest {
	private static SQLConnector conn;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		conn = new SQLConnector();
	}

	private void testQuery(String query) {
		ResultSet rs = conn.basicQuery(query);
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
	public void TestEmptyResultQueryReturnsNull() {
		ResultSet rs = conn.basicQuery("SELECT * FROM authors LIMIT 0");
		assertEquals(null, rs);
	}
	
	@Test
	public void TestBadConnectionReturnsNull() {
		SQLConnector conn = new SQLConnector("dogs");
		assertEquals(null, conn.getConn());
	}
	
	@Test
	public void TestBadQueryReturnsNull() {
		ResultSet rs = conn.basicQuery("SELECT dogs FROM authors LIMIT 0");
		assertEquals(null, rs);
	}
	
	@Test
	public void TestBadFormatQueryReturnsNull() {
		ResultSet rs = conn.basicQuery("SELECT dogs FM authors LIMIT 0");
		assertEquals(null, rs);
	}
}
