package com.testing123.vaadin;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.testing123.controller.UIState;
import com.testing123.controller.UIState.Axis;

public class GetDataTest {
	static Queryable qi;
	GetData gd;
	UIState state;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		qi = new MockQueryable();
	}

	@Before
	public void setUp() {
		gd = new GetData(qi);
		state = new UIState();
	}

	@Test
	public void passInDLOCGetDLOCvsComplexity() {
		state.setX(Axis.DELTA_LINESOFCODE);
		Set<DataPoint> set = gd.getData(state);
		assertEquals(3, set.size());
		assertEquals("[[28.0, 11.0, \"b\", \"Authors: \"], [32.0, 13.0, \"c\", \"Authors: \"], [8.0, 14.0, \"t\", \"Authors: \"]]",
				HashSetToTreeSet(set).toString());
	}

	@Test
	public void passInLOCgetLOCvsComplexity() {
		state.setX(Axis.LINESOFCODE);
		Set<DataPoint> set = gd.getData(state);
		assertEquals(5, set.size());
		assertEquals(
"[[13.0, 11.0, \"b\", \"Authors: \"], [15.0, 11.0, \"d\", \"Authors: \"], [8.0, 11.0, \"i\", \"Authors: \"], [11.0, 54.0, \"p\", \"Authors: \"], [6.0, 14.0, \"t\", \"Authors: \"]]",
HashSetToTreeSet(set).toString());

	}

	@Test
	public void passInEmptyDComplexReturnEmpty() {
		state.setX(Axis.DELTA_COMPLEXITY);
		assertEquals("[]", gd.getData(state).toString());
	}

	public TreeSet<DataPoint> HashSetToTreeSet(Set<DataPoint> set) {
		return new TreeSet<DataPoint>(set);
	}

}
