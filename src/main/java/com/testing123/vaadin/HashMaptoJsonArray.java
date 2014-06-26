package com.testing123.vaadin;

import java.util.Arrays;
import java.util.Map;

public class HashMaptoJsonArray {
	
	private static int[][] dataPoints;
	
	private static void makeJavaArray(Map<Integer, Integer> map){
		
		int size = map.size();
		dataPoints = new int[size][2];
		
		int count = 0;
		for(Map.Entry<Integer, Integer> entry : map.entrySet()){
		    dataPoints[count][0] =  entry.getKey();
		    dataPoints[count][1] = entry.getValue();
		    count++;
		}
	}
	
	public static String mapToString(Map<Integer, Integer> hashedmap){
		makeJavaArray(hashedmap);
		return "[" + Arrays.deepToString(dataPoints) + "]";
	}
	
	
}
