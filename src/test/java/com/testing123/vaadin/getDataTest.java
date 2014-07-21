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
		//assertEquals("[[8.0, 14.0, \"t\"], [32.0, 13.0, \"c\"], [28.0, 11.0, \"b\"]]",gd.getData(state).toString());
		//assertEquals("[[32.0, 13.0, \"c\"], [28.0, 11.0, \"b\"], [8.0, 14.0, \"t\"]]", gd.getData(state).toString());
	}
	
	@Test
	public void passInLOCgetLOCvsComplexity() {
		state.setX(Axis.LINESOFCODE);
		System.out.println();
		//assertEquals("[[6.0, 14.0, \"t\"], [11.0, 54.0, \"p\"], [15.0, 11.0, \"d\"], [13.0, 11.0, \"b\"], [8.0, 11.0, \"i\"]]",gd.getData(state).toString());
		//assertEquals("[[8.0, 11.0, \"i\"], [15.0, 11.0, \"d\"], [13.0, 11.0, \"b\"], [11.0, 54.0, \"p\"], [6.0, 14.0, \"t\"]]",gd.getData(state).toString());
	}
	
	@Test
	public void passInEmptyDComplexReturnEmpty() {
		state.setX(Axis.DELTA_COMPLEXITY);
		System.out.println();
		assertEquals("[]",gd.getData(state).toString());
	}

}
