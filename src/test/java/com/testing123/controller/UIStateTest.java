package com.testing123.controller;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import com.testing123.controller.UIState;
import com.testing123.controller.UIState.XAxis;
import com.testing123.controller.UIState.YAxis;
import com.testing123.dataObjects.ConvertDate;
import com.testing123.dataObjects.ConvertProject;

public class UIStateTest {
	private static ConvertDate date1;
	private static ConvertDate date2;
	private static ConvertProject proj1;
	private static ConvertProject proj2;
	
	@BeforeClass
	public static void setUp() {
		date1 = new ConvertDate("2014-07-01");
		date2 = new ConvertDate("2014-07-03");
		proj1 = new ConvertProject("proj1", "1", 1, "1.1");
		proj2 = new ConvertProject("proj2", "2", 2, "2.2");
	}

	@Test
	public void TestInitialUIState() {
		UIState state = new UIState();
		state.verifyState();
		assertEquals("07/29/2014", state.getStart().toString());
		assertEquals("07/30/2014", state.getEnd().toString());
		assertEquals(XAxis.DELTA_LINESOFCODE, state.getX());
	}
	
	@Test
	public void TestInitialUIStateWithArgs() {
		UIState state = new UIState(new ConvertDate("2014-07-15"), 
				new ConvertDate("2014-07-21"), XAxis.LINESOFCODE);
		assertEquals("07/15/2014", state.getStart().toString());
		assertEquals("07/21/2014", state.getEnd().toString());
		assertEquals(XAxis.LINESOFCODE, state.getX());
	}
	
	@Test
	public void TestDateSetters() {
		UIState state = new UIState(new ConvertDate("2014-07-15"), 
				new ConvertDate("2014-07-21"), XAxis.LINESOFCODE);
		state.setStart(date1);
		state.setEnd(date2);
		assertEquals("07/01/2014", state.getStart().toString());
		assertEquals("07/03/2014", state.getEnd().toString());
		assertEquals(XAxis.LINESOFCODE, state.getX());
	}
	
	@Test
	public void TestXAxisSetter() {
		UIState state = new UIState();
		state.setX(XAxis.DELTA_COMPLEXITY);
		assertEquals("Change in Complexity", state.getX().toString());
		assertEquals(XAxis.DELTA_COMPLEXITY, state.getX());
		assertEquals("delta_complexity", state.getX().getColName());
	}
	
	@Test
	public void TestPossibleXAxisValuesIsConsistent() {
		assertEquals(5, XAxis.possibleValues().size());
	}
	
	@Test
	public void TestYAxisSetter() {
		UIState state = new UIState();
		state.setY(YAxis.COMPLEXITY);
		assertEquals("Complexity", state.getY().toString());
		assertEquals(YAxis.COMPLEXITY, state.getY());
		assertEquals("complexity", state.getY().getColName());
	}
	
	@Test
	public void TestPossibleYAxisValuesIsConsistent() {
		assertEquals(3, YAxis.possibleValues().size());
	}
	
	@Test
	public void TestAuthorsSetter() {
		UIState state = new UIState();
		Set<String> auths = new HashSet<String>();
		auths.add("boil");
		auths.add("crab");
		state.setAuthorsFilter(auths);
		assertEquals(auths, state.getAuthorsFilter());
	}
	
	@Test
	public void TestProjectSetter() {
		UIState state = new UIState();
		Set<ConvertProject> projs = new HashSet<ConvertProject>();
		projs.add(proj1);
		projs.add(proj2);
		state.setProjects(projs);
		assertEquals(projs, state.getProjects());
	}
}
