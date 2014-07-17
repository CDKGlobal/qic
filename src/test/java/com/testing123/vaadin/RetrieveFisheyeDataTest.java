package com.testing123.vaadin;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class RetrieveFisheyeDataTest {
	
	ConvertDate startDate;
	ConvertDate endDate;

	@Before
	public void setUp() {
		startDate = new ConvertDate("2014-07-07T06-09-17-0700");
		endDate = new ConvertDate("2014-07-10T06-07-56-0700");
	}

	@Test
	public void TestEmptyQuery() {
		new RetrieveFisheyeData().getChurnData(startDate, endDate);
		fail("Not yet implemented");
	}

}
