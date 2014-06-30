package com.testing123.vaadin;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;

public class MapToJsonArray<K, V> {

	private static String[][] dataPoints;
	private static Data[] labledDataPoints;

	private static <K, V> void makeJavaArray(Map<K, V> map) {

		int size = map.size();
		dataPoints = new String[size][2];

		int count = 0;
		for (Map.Entry<K, V> entry : map.entrySet()) {
			dataPoints[count][0] = entry.getKey().toString();
			dataPoints[count][1] = entry.getValue().toString();
			count++;
		}
	}

	public static <K, V> String mapToString(Map<K, V> map) {
		makeJavaArray(map);
		return "[" + Arrays.deepToString(dataPoints) + "]";
	}

	public static <K, V> void makeJavaArray(Map<K, V> map1, Map<K, V> map2) {
		int size = map1.size();
		if (size != map2.size()) {
			throw new IllegalArgumentException("maps have different size");
		}
		labledDataPoints = new Data[size];

		int count = 0;
		for (K key : map1.keySet()) {
			if (map1.containsKey(key) && map2.containsKey(key)) {
				String first = map1.get(key).toString();
				String second = map2.get(key).toString();
				String src = key.toString();
				labledDataPoints[count] = new Data(first, second, src);
			}
			count++;
		}
	}

	public static <K, V> String mapToString(Map<K, V> map1, Map<K, V> map2) {
		makeJavaArray(map1, map2);
		return "[" + Arrays.deepToString(labledDataPoints) + "]";
	}
	
	public static <T> String mapToString(Set<T> set){
		return "[" + set.toString() + "]";
	}

	private static class Data {
		private String firstVal;
		private String secondVal;
		private String source;

		public Data(String one, String two, String src) {
			firstVal = one;
			secondVal = two;
			source = src;
		}

		@Override
		public String toString() {
			return "[" + firstVal + "," + secondVal + ",\"" + source + "\"]";
		}
	}

}
