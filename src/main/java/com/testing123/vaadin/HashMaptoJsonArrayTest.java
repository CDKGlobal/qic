package com.testing123.vaadin;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.TreeMap;

import org.junit.BeforeClass;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class HashMaptoJsonArrayTest {
	
	static HashMap empty;
	static HashMap oneInput;
	static HashMap multipleInputs;
	static TreeMap bigMap;
	static HashMap bigHashMap;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		empty = new HashMap();
		
		oneInput = new HashMap();
		oneInput.put(3, 5);
		
		multipleInputs = new HashMap();
		multipleInputs.put(1, 1);
		multipleInputs.put(3, 2);
		multipleInputs.put(7, 3);
		multipleInputs.put(4, 7);
		multipleInputs.put(2, 3);
		multipleInputs.put(5, 4);
		
		bigMap = new TreeMap();
		Random generator = new Random();
		for(int i = 1; i <= 100000; i++){
			bigMap.put(i, generator.nextInt(i));
		}
		
		bigHashMap = new HashMap();
		for(int i = 1; i <= 100000; i++){
			bigHashMap.put(i, generator.nextInt(i));
		}
		
	}
	
	private static String getString(HashMap map){
		return HashMaptoJsonArray.mapToString(map).replaceAll("\\s+","");
	}
	
	private static String getString(TreeMap map){
		return HashMaptoJsonArray.mapToString(map).replaceAll("\\s+","");
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
		//System.out.println(getString(bigMap));
		assertEquals(100000,bigMap.size());
	}
	
	@Test(timeout = 1000)
	public void bigHashMapTimeTest(){
		//System.out.println(getString(bigMap));
		assertEquals(100000,bigHashMap.size());
	}

}
