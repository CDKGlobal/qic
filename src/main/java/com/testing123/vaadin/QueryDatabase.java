package com.testing123.vaadin;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.testing123.controller.AvailableResources;
import com.testing123.controller.SQLConnector;

public class QueryDatabase {
	
	public Map<String, DataPoint> getNCLOC(ConvertDate date){
		Map<String, DataPoint> map = new HashMap<String, DataPoint>();
		List<WebData> dataList = getDataList(date);
		for (WebData file : dataList) {
			if (!map.containsKey(file.getName())) {
				map.put(catKey(file.getKey()), new DataPoint(catKey(file.getKey()), file.getMsr().get(0).getVal()));	//retrieves ncloc
			} else {
				throw new IllegalStateException("Unhandeled duplicate case in: ncloc");
			}
		}
		return map;
	}
	
	public Map<String, DataPoint> getComplexity(ConvertDate date) {
		Map<String, DataPoint> map = new HashMap<String, DataPoint>();
		List<WebData> dataList = getDataList(date);
		for (WebData file : dataList) {
			if (!dataList.isEmpty()) {
				map.put(catKey(file.getKey()), new DataPoint(catKey(file.getKey()), file.getMsr().get(1).getVal()));	//retrieves complexity
			} else {
				throw new IllegalStateException("Unhandeled duplicate case in: complexity");
			}
		}
		
		return map;
	}
	
	public Map<String, DataPoint> getDeltaComplexity(ConvertDate startDate, ConvertDate endDate) {
		Map<String, DataPoint> initialComplexity = getComplexity(startDate);
		Map<String, DataPoint> finalComplexity = getComplexity(endDate);
		Map<String, DataPoint> deltaComplexity = new HashMap<String, DataPoint>();
		for (String name : finalComplexity.keySet()) {
			if (initialComplexity.containsKey(name)) {
				if (finalComplexity.get(name).getXValue() - initialComplexity.get(name).getXValue() != 0) {
					double change = finalComplexity.get(name).getXValue() - initialComplexity.get(name).getXValue();
					deltaComplexity.put(name, new DataPoint(name, change));
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
			results = SQLConnector.basicQuery("SELECT * FROM " + extractDate(date) + " WHERE QUALIFIER = 'CLA';");
		} catch (Exception e) {
			return new ArrayList<WebData>();
		}
		
		// params: process(ResultSet r, metric (0), metric (1))
		return SQLConnector.process(results, "ncloc", "complexity");
	}

	private static String extractDate(ConvertDate dateObject) {
		String sonarFormat = dateObject.getSonarFormat();
//		String[] dateAndTime = sonarFormat.split("T");
//		String[] date = dateAndTime[0].split("-");
//		return "d" + date[1] + "_" + date[2];
		return sonarFormat.replace("-", "_");
	}
	
	private static String catKey(String longKey) {
		String[] pack = longKey.split(":");
		String replacement = pack[1];
		return longKey.replaceAll("com.cobalt.*:", replacement + ".");
	}
}
