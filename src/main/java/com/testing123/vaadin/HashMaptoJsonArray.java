package com.testing123.vaadin;

import java.util.Arrays;
import java.util.Map;

public class HashMaptoJsonArray {
	
	private static int[][] dataPoints;
	
	private static void makeJavaArray(Map<Integer, Integer> hashedmap){
		
		int size = hashedmap.size();
		dataPoints = new int[size][2];
		
		int count = 0;
		for(Map.Entry<Integer,Integer> entry : hashedmap.entrySet()){
		    dataPoints[count][0] = entry.getKey();
		    dataPoints[count][1] = entry.getValue();
		    count++;
		}
	}
	
	public static String hashMapToString(Map<Integer, Integer> hashedmap){
		makeJavaArray(hashedmap);
		return "[" + Arrays.deepToString(dataPoints) + "]";
	}
	
	
}
