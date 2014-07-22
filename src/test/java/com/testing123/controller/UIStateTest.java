package com.testing123.controller;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.testing123.controller.UIState;
import com.testing123.controller.UIState.Axis;
import com.testing123.vaadin.ConvertDate;

public class UIStateTest {

	@Test
	public void TestInitialUIState() {
		UIState state = new UIState();
		assertEquals("Tue Jul 15 06:07:55 PDT 2014", state.getStart().toString());
		assertEquals("Mon Jul 21 06:07:35 PDT 2014", state.getEnd().toString());
		assertEquals(Axis.DELTA_LINESOFCODE, state.getX());
	}
	
	@Test
	public void TestInitialUIStateWithArgs() {
		UIState state = new UIState(new ConvertDate("2014-07-15T06-07-55-0700"), 
				new ConvertDate("2014-07-21T06-07-35-0700"), Axis.COMPLEXITY);
		assertEquals("Tue Jul 15 06:07:55 PDT 2014", state.getStart().toString());
		assertEquals("Mon Jul 21 06:07:35 PDT 2014", state.getEnd().toString());
		assertEquals(Axis.COMPLEXITY, state.getX());
	}
	
	@Test
	public void TestSetUIState() {
		UIState state = new UIState();
		state.setFocus("string");
		assertEquals("string", state.getFocus());
	}

}
