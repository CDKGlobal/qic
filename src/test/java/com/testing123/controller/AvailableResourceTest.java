package com.testing123.controller;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.testing123.dataObjects.ConvertDate;

public class AvailableResourceTest {
	private SQLConnector conn;

	@Before
	public void setUp() throws SQLException {
		conn = new SQLConnector("TestDatabase");
		conn.updateQuery("DELETE FROM dates");
		conn.updateQuery("INSERT INTO dates (display, datescol) VALUES ('2014-08-07', null)");
		conn.updateQuery("INSERT INTO dates (display, datescol) VALUES ('2014-08-08', null)");
	}
	
	@Test
	public void TestGetAvailableDatesReturnsAllDates() {
		List<ConvertDate> list = AvailableResources.getAvailableDates(conn);
		assertEquals("2014-08-07", list.get(0).getDBFormat());
		assertEquals("2014-08-08", list.get(1).getDBFormat());
		assertEquals(2, list.size());
	}
	
	@Test
	public void TestGetAvailableDatesReturnsSomething() {
		assertTrue(0 < AvailableResources.getAvailableDates(conn).size());
	}

}
