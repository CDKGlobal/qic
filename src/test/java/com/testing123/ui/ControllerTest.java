package com.testing123.ui;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import com.testing123.controller.AvailableResources;

public class ControllerTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Test
	public void TestGetAvailableDatesReturnsDates() {
		assertEquals(3, AvailableResources.getAvailableDates());
	}

}