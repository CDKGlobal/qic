package com.testing123.vaadin;

import static org.junit.Assert.*;

import java.net.MalformedURLException;
import java.net.URL;

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
	public void TestEmptyQuery() throws MalformedURLException {
		assertEquals(
				new URL("http://fisheye.cobalt.com/rest-service-fe/search-v1/queryAsRows/Advertising.Perforce.json?query=select%20revisions%20where%20date%20in%20[2014-07-07,2014-07-10]%20and%20path%20like%20\\Platform/trunk/src/main/**%20and%20path%20like%20*.java%20group%20by%20file%20return%20path,%20sum(linesAdded),%20sum(linesRemoved),%20count(isDeleted)"),
				query.getChurn());
	}

}
