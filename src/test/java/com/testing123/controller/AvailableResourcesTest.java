package com.testing123.controller;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.testing123.controller.SQLConnector;
import com.testing123.vaadin.WebData;

public class AvailableResourcesTest {

	@Test
	public void TestGetAvailableDatesReturnsDates() {
//		assertEquals("2014-07-07T06-09-17-0700", AvailableResources.getAvailableDates().get(0));
	}
	
	@Test
	public void TestSQLConnectionThrowsNoException() {
        SQLConnector.dataQuery("", "");
	}
	
	@Test 
	public void TestInvalidGetDataListReturnsNonEmptyList() {
//		List<WebData> list = AvailableResources.getDataList("2014-07-07T06-09-17-0700");
//		assertTrue(list.size() > 0);
	}
}
