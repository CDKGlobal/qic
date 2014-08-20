package com.testing123.dataObjects;

import static org.junit.Assert.*;

import java.util.HashSet;

import org.junit.Test;

public class DataPointSetTest {

	@Test
	public void testEmpty(){
		DataPointSet test = new DataPointSet();
		assertEquals(new HashSet<DataPoint>(), test.getNegVals());
		assertEquals(new HashSet<DataPoint>(), test.getPosVals());
		assertEquals("[ { data: [], points: { show: true, fill: true, fillColor: \"#009933\" } }, { data: [], points: { show: true, fill: true, fillColor: \"#FF0000\" }} ]", test.toString());
	}
	
	@Test
	public void testAddingEmptyPosDataPoint(){
		DataPointSet test = new DataPointSet();
		test.addPos(new DataPoint());
		assertEquals(0,test.getNegVals().size());
		assertEquals(1,test.getPosVals().size());
		assertEquals("[ { data: [], points: { show: true, fill: true, fillColor: \"#009933\" } }, { data: [[-1.0, -1.0, \"null\", \"\",\"Changed Complexity: \"]], points: { show: true, fill: true, fillColor: \"#FF0000\" }} ]",test.toString());
	}

}
