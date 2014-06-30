package com.testing123.vaadin;

import java.util.Arrays;
import java.util.Map;

public class MapToJsonArray<K,V>{
	
	private static String[][] dataPoints;
	
	private static <K,V> void makeJavaArray(Map<K,V> map){
		
		int size = map.size();
		dataPoints = new String[size][2];
		
		int count = 0;
		for(Map.Entry<K, V> entry : map.entrySet()){
		    dataPoints[count][0] =  entry.getKey().toString();
		    dataPoints[count][1] = entry.getValue().toString();
		    count++;
		}
	}
	
	public static <K,V> String mapToString(Map<K, V> map){
		makeJavaArray(map);
		return "[" + Arrays.deepToString(dataPoints) + "]";
	}
	
	private static class Data{
		private double firstVal;
		private double secondVal;
		private String source;
		
		public Data(double one, double two, String src){
			firstVal=one;
			secondVal=two;
			source=src;
		}
		
		@Override
		public String toString(){
			return "["+firstVal+","+secondVal+",\""+source+"\"]"; 
		}
	}
	
}
