package com.testing123.vaadin;

import static org.junit.Assert.*;

import org.junit.Test;

import com.testing123.dataObjects.DataPoint;
import com.testing123.dataObjects.DataPointSet;
import com.testing123.dataObjects.FooterData;

public class FooterSummaryTest {

	@Test
	public void testDefaults() {
		FooterData data = FooterSummary.getFooterData(new DataPointSet());
		assertEquals(0,data.getNegative());
		assertEquals(0,data.getPositive());
		assertEquals(0,data.getTotal());
	}
	
	@Test
	public void testOnePositivePoint(){
		DataPointSet dps = new DataPointSet();
		dps.addPos(new DataPoint("",31,-1));
		FooterData posData = FooterSummary.getFooterData(dps);
		assertEquals(0,posData.getNegative());
		assertEquals(31,posData.getPositive());
		assertEquals(31,posData.getTotal());
	}
	
	@Test
	public void testOneNegativePoint(){
		DataPointSet dps = new DataPointSet();
		dps.addNeg(new DataPoint("",-31,-1));
		FooterData negData = FooterSummary.getFooterData(dps);	
		assertEquals(31,negData.getNegative());
		assertEquals(0,negData.getPositive());
		assertEquals(-31,negData.getTotal());
	}
	
	@Test
	public void testMultiplePoints(){
		DataPointSet dps = new DataPointSet();
		dps.addPos(new DataPoint("",0,-1));
		dps.addPos(new DataPoint("",2,-1));
		dps.addPos(new DataPoint("",7,-1));
		dps.addPos(new DataPoint("",100,-1));
		dps.addPos(new DataPoint("",13,-1));
		dps.addNeg(new DataPoint("",-50,-1));
		dps.addNeg(new DataPoint("",-1,-1));
		dps.addPos(new DataPoint("",9,-1));

		FooterData negData = FooterSummary.getFooterData(dps);	
		assertEquals(51,negData.getNegative());
		assertEquals(131,negData.getPositive());
		assertEquals(80,negData.getTotal());
	}
	
	@Test
	public void testEmptyByFile(){
		FooterData data = FooterSummary.getFooterDataByFile(new DataPointSet());
		assertEquals(0,data.getNegative());
		assertEquals(0,data.getPositive());
		assertEquals(0,data.getTotal());
	}
	
	@Test
	public void testOnePositivePointbyFile(){
		DataPointSet dps = new DataPointSet();
		dps.addPos(new DataPoint("",31,-1));
		FooterData posData = FooterSummary.getFooterDataByFile(dps);
		assertEquals(0,posData.getNegative());
		assertEquals(1,posData.getPositive());
		assertEquals(1,posData.getTotal());
	}
	
	@Test
	public void testOneNegativePointbyFile(){
		DataPointSet dps = new DataPointSet();
		dps.addNeg(new DataPoint("",-31,-1));
		FooterData negData = FooterSummary.getFooterDataByFile(dps);	
		assertEquals(1,negData.getNegative());
		assertEquals(0,negData.getPositive());
		assertEquals(1,negData.getTotal());
	}
	
	@Test
	public void testMultiplePointsByFile(){
		DataPointSet dps = new DataPointSet();
		dps.addPos(new DataPoint("",0,-1));
		dps.addPos(new DataPoint("",2,-1));
		dps.addPos(new DataPoint("",7,-1));
		dps.addPos(new DataPoint("",100,-1));
		dps.addPos(new DataPoint("",13,-1));
		dps.addNeg(new DataPoint("",-50,-1));
		dps.addNeg(new DataPoint("",-1,-1));
		dps.addPos(new DataPoint("",9,-1));

		FooterData negData = FooterSummary.getFooterDataByFile(dps);	
		assertEquals(2,negData.getNegative());
		assertEquals(6,negData.getPositive());
		assertEquals(8,negData.getTotal());
	}

}
