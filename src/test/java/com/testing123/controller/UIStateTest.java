package com.testing123.controller;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.testing123.controller.UIState;

public class UIStateTest {
	private static UIState state;

	@Before
	public void setUp() throws Exception {
		state = new UIState();
	}

	@Test
	public void TestInitialUIState() {
		assertEquals("Tue Jul 15 06:07:55 PDT 2014", state.getStart().toString());
		assertEquals("Mon Jul 21 06:07:35 PDT 2014", state.getEnd().toString());
		//assertEquals(true, state.getIsDelta());
	}
	
	@Test
	public void TestSetUIState() {
		//state.setIsDelta(false);
		//assertEquals(false, state.getIsDelta());
		state.verifyState();
	}

}
