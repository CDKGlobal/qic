package com.testing123.ui;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
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
		assertEquals("Mon Jul 07 06:09:17 PDT 2014", state.getStart().toString());
		assertEquals("Thu Jul 10 06:07:56 PDT 2014", state.getEnd().toString());
		assertEquals(true, state.getIsDelta());
	}
	
	@Test
	public void TestSetUIState() {
		state.setIsDelta(false);
		assertEquals(false, state.getIsDelta());
	}

}
