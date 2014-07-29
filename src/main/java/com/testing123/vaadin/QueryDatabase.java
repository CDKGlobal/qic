package com.testing123.vaadin;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.testing123.controller.AvailableResources;
import com.testing123.controller.SQLConnector;

public class QueryDatabase {
	
	public Map<String, Double> getNCLOC(ConvertDate date){
		Map<String, Double> map = new HashMap<String, Double>();
		List<WebData> dataList = getDataList(date);
		if (dataList == null) {
			return map;
		}
		for (WebData file : dataList) {
			if (!map.containsKey(file.getName())) {
				map.put(formatKey(file.getKey()), file.getMsr().get(0).getVal());	//retrieves ncloc
			} else {
				throw new IllegalStateException("Unhandeled duplicate case in: ncloc");
			}
		}
		return map;
	}
	
	public Map<String, Double> getComplexity(ConvertDate date) {
		if (!date.verify()) {
			throw new IllegalArgumentException("Invalid date");
		}
		Map<String, Double> map = new HashMap<String, Double>();
		List<WebData> dataList = getDataList(date);
		if (dataList == null) {
			return map;
		}
		for (WebData file : dataList) {
			if (!dataList.isEmpty()) {
				map.put(formatKey(file.getKey()), file.getMsr().get(1).getVal());	//retrieves complexity
			} else {
				throw new IllegalStateException("Unhandeled duplicate case in: complexity");
			}
		}
		
		return map;
	}
	
	public Map<String, Double> getDeltaComplexity(ConvertDate startDate, ConvertDate endDate) {
		Map<String, Double> initialComplexity = getComplexity(startDate);
		Map<String, Double> finalComplexity = getComplexity(endDate);
		Map<String, Double> deltaComplexity = new HashMap<String, Double>();
		for (String name : finalComplexity.keySet()) {
			if (initialComplexity.containsKey(name)) {
				double change = finalComplexity.get(name) - initialComplexity.get(name);
				if (change != 0) {
					deltaComplexity.put(name, change);
				}
			} else {
				deltaComplexity.put(name, finalComplexity.get(name));
			}
		}
		return deltaComplexity;
	}
	
	private static List<WebData> getDataList(ConvertDate date) {
		ResultSet results = null;
		try {
			results = SQLConnector.basicQuery("SELECT * FROM " + date.getDBFormat() + " WHERE QUALIFIER = 'CLA';");
		} catch (Exception e) {
			return new ArrayList<WebData>();
		}
		
		// params: process(ResultSet r, metric (0), metric (1))
		return SQLConnector.process(results, "ncloc", "complexity");
	}
	
	private static String formatKey(String longKey) {
		String[] pack = longKey.split(":");
		String replacement = pack[1];
		return longKey.replaceAll("com.cobalt.*:", replacement + ".");
	}
}
