package com.testing123.vaadin;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ConvertDateTest {
	private ConvertDate date;
	
	@Before
	public void setUp() throws Exception {
		date = new ConvertDate("2014-07-07T06-09-17-0700");
	}
	
	@Test
	public void TestGetSonarFormatReturnsOriginalFormat() {
		assertEquals("2014-07-07T06-09-17-0700", date.getSonarFormat());
	}
	
	@Test
	public void TestToStringReturnsReadableFormat() {
		assertEquals("Mon Jul 07 06:09:17 PDT 2014", date.toString());
	}
}
