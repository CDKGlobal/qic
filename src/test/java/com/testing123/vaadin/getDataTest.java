package com.testing123.vaadin;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.testing123.controller.UIState;
import com.testing123.controller.UIState.Axis;

public class getDataTest {
	static Queryable qi;
	GetData gd;
	UIState state;
	
	@BeforeClass
    public static void setUpBeforeClass() throws Exception {
        qi = new MockQueryable();
    }
	
	@Before
	public void setUp(){
		gd = new GetData(qi);
		state = new UIState();
	}

	@Test
	public void passInDLOCGetDLOCvsComplexity() {
		state.setX(Axis.DELTA_LINESOFCODE);
		System.out.println();
		assertEquals(3,gd.getData(state).size());
	}
	
	@Test
	public void passInLOCgetLOCvsComplexity() {
		state.setX(Axis.LINESOFCODE);
		System.out.println();
		assertEquals(5,gd.getData(state).size());
	}
	
	@Test
	public void passInEmptyDComplexReturnEmpty() {
		state.setX(Axis.DELTA_COMPLEXITY);
		System.out.println();
		assertEquals("[]",gd.getData(state).toString());
	}

}
