package com.testing123.vaadin;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.testing123.controller.SQLConnector;

public class QueryDatabase {

	public Map<String, Double> getcomplexity(ConvertDate date) {

		Map<String, Double> map = new HashMap<String, Double>();
		String formattedDate = extractDate(date);
		List<WebData> dataList = getDataList(formattedDate);

		for (WebData file : dataList) {
			if (!dataList.isEmpty() && "CLA".equals(file.getQualifier())) {
				map.put(file.getName(), file.getMsr().get(1).getVal());//retrieves complexity
			}
		}
		return map;
	}

	private static List<WebData> getDataList(String date) {
		ResultSet results = null;
		try {
			results = SQLConnector.basicQuery("SELECT * FROM " + date + " WHERE QUALIFIER = 'CLA';");
		} catch (Exception e) {
			return new ArrayList<WebData>();
		}
		return SQLConnector.process(results, "ncloc", "complexity");
	}

	private static String extractDate(ConvertDate dateObject) {
		String sonarFormat = dateObject.getSonarFormat();
		String[] dateAndTime = sonarFormat.split("T");
		String[] date = dateAndTime[0].split("-");
		return "d" + date[1] + "_" + date[2];
	}

}
