package com.testing123.vaadin;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.testing123.dataObjects.ConvertDate;

public class ConvertDateTest {
	private ConvertDate date;
	
	@Before
	public void setUp() throws Exception {
		date = new ConvertDate("2014-07-07");
	}
	
	@Test
	public void TestToStringReturnsReadableFormat() {
		assertEquals("07/07/2014", date.toString());
	}
	
	@Test
	public void TestEqualsWithSameDateRepresentation() {
		ConvertDate date1 = new ConvertDate("2014-07-07");
		ConvertDate date2 = new ConvertDate("2014-07-07");
		assertEquals(date1.toString(), date2.toString());
		assertEquals(date1.toString(), date2.toString());
		assertTrue(date1.equals(date2));
	}
}
