package com.testing123.vaadin;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import org.junit.BeforeClass;
import org.junit.Test;




public class MapToJsonArrayTest {
	
	static HashMap<Integer,Integer> empty;
	static HashMap<Integer,Integer>  oneInput;
	static HashMap<Integer,Integer>  multipleInputs;
	static TreeMap<Integer,Integer>  bigMap;
	static HashMap<Integer,Integer>  bigHashMap;
	static HashMap<String,Double> stringDoubleMap;
	static HashMap<String,Double> stringDoubleMap2;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		empty = new HashMap<Integer,Integer> ();
		
		oneInput = new HashMap<Integer,Integer> ();
		oneInput.put(3, 5);
		
		multipleInputs = new HashMap<Integer,Integer> ();
		multipleInputs.put(1, 1);
		multipleInputs.put(3, 2);
		multipleInputs.put(7, 3);
		multipleInputs.put(4, 7);
		multipleInputs.put(2, 3);
		multipleInputs.put(5, 4);
		
		bigMap = new TreeMap<Integer,Integer> ();
		Random generator = new Random();
		for(int i = 1; i <= 100000; i++){
			bigMap.put(i, generator.nextInt(i));
		}
		
		bigHashMap = new HashMap<Integer,Integer> ();
		for(int i = 1; i <= 100000; i++){
			bigHashMap.put(i, generator.nextInt(i));
		}
		
		stringDoubleMap = new HashMap<String,Double> ();
		stringDoubleMap.put("a", 1.0);
		stringDoubleMap.put("b", 2.0);
		stringDoubleMap.put("c", 3.0);
		stringDoubleMap.put("d", 4.5);
		stringDoubleMap.put("e", 5.0);
		stringDoubleMap.put("f", 6.7);
		
		stringDoubleMap2 = new HashMap<String,Double> ();
		stringDoubleMap2.put("a", 11.0);
		stringDoubleMap2.put("b", 12.0);
		stringDoubleMap2.put("c", 13.0);
		stringDoubleMap2.put("d", 14.5);
		stringDoubleMap2.put("e", 15.0);
		stringDoubleMap2.put("f", 16.7);
		
	}
	
	private static String getString(Map<Integer,Integer> map){
		return MapToJsonArray.mapToString(map).replaceAll("\\s+","");
	}
	

	@Test
	public void emptyHashTest() {
		assertEquals(getString(empty), "[[]]");
	}
	
	@Test
	public void oneInputTest() {
		assertEquals("[[[3,5]]]", getString(oneInput));
	}
	
	@Test
	public void multipleInputsTest() {
		assertEquals("[[[1,1],[2,3],[3,2],[4,7],[5,4],[7,3]]]",getString(multipleInputs));
	}
	
	@Test(timeout = 1000)
	public void bigMapTimeTest(){
		getString(bigMap);
		assertEquals(100000,bigMap.size());
	}
	
	@Test(timeout = 1000)
	public void bigHashMapTimeTest(){
		getString(bigHashMap);
		assertEquals(100000,bigHashMap.size());
	}
	
	@Test
	public void stringDoubleTest(){
		assertEquals("[[[f,6.7],[d,4.5],[e,5.0],[b,2.0],[c,3.0],[a,1.0]]]",MapToJsonArray.mapToString(stringDoubleMap).replaceAll("\\s+",""));
	}
	
	@Test
	public void twoStringDoubleTest(){
		assertEquals("[[[6.7,16.7,\"f\"],[4.5,14.5,\"d\"],[5.0,15.0,\"e\"],[2.0,12.0,\"b\"],[3.0,13.0,\"c\"],[1.0,11.0,\"a\"]]]",MapToJsonArray.mapToString(stringDoubleMap,stringDoubleMap2).replaceAll("\\s+",""));
	}

}
