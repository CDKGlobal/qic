package com.testing123.vaadin;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class FisheyeQueryTest {
	FisheyeQuery query;

	@Before
	public void setUp() {
		ConvertDate StartDate = new ConvertDate("2014-07-07T06-09-17-0700");
		ConvertDate EndDate = new ConvertDate("2014-07-10T06-07-56-0700");
		query = new FisheyeQuery("Advertising.Perforce", StartDate, EndDate);
	}

	@Test
	public void TestEmptyQuery() {
		query.getChurn();
		assertEquals(
				"http://fisheye.cobalt.com/rest-service-fe/search-v1/queryAsRows/Advertising.Perforce.json?query=select%20revisions%20where%20date%20in%20[2014-07-07,2014-07-10]%20return%20path",
				query.toString());
	}

}
