package com.testing123.dataObjects;

import static org.junit.Assert.*;

import org.junit.Test;

public class FooterDataTest {
	
	@Test
	public void testInitalizationOfFooter(){
		FooterData empty = new FooterData();
		assertEquals(0,empty.getTotal());
		assertEquals(0,empty.getPositive());
		assertEquals(0,empty.getNegative());
	}
	
	@Test
	public void testAddPositive(){
		FooterData addPos = new FooterData();
		addPos.addPositive(1);
		addPos.addPositive(123);
		addPos.addPositive(0);
		assertEquals(124, addPos.getPositive());		
	}
	
	@Test
	public void testAddNegative(){
		FooterData addNeg = new FooterData();
		addNeg.addNegative(-1);
		addNeg.addNegative(-123);
		addNeg.addNegative(0);
		assertEquals(124, addNeg.getNegative());		
	}
	
	@Test
	public void testTotal(){
		FooterData total = new FooterData();
		total.addNegative(-13);
		total.addPositive(19);

		assertEquals(13,total.getNegative());
		assertEquals(19,total.getPositive());
		assertEquals(6,total.getTotal());
		
		total.setTotal();
		assertEquals(13,total.getNegative());
		assertEquals(19,total.getPositive());
		assertEquals(32,total.getTotal());
	}

}
