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
		stringDoubleMap.put("d", 7.0);
		stringDoubleMap.put("e", 3.0);
		stringDoubleMap.put("f", 4.0);
		
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
		assertEquals("[[[f,4.0],[d,7.0],[e,3.0],[b,2.0],[c,3.0],[a,1.0]]]",MapToJsonArray.mapToString(stringDoubleMap).replaceAll("\\s+",""));
	}

}
