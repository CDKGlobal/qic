package com.testing123.vaadin;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


public class SonarMetrics {
	
	private static final String LINK = "http://sonar.cobalt.com/api/metrics";
	
	/**
	 * 
	 * @return a map of all sonar metrics to their name/definitions
	 */
	private static Map<String,String> getSonarMetrics(){
		Map<String,String> definitions = new HashMap<String,String>();
		try{
			URL link = new URL(LINK);
			ObjectMapper mapper = new ObjectMapper();
			List<WebData> fileList = mapper.readValue(link, new TypeReference<List<WebData>>() {});
			for (WebData file : fileList) {
				definitions.put(file.getKey(), file.getName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return definitions;
	}
	
	/**
	 * 
	 * @param metric1
	 * @param metric2
	 * @return true if the metrics exist in the sonar API
	 * sets the axes of the graph
	 */
	public static boolean sonarHasMetrics(String metric1, String metric2){
		Map<String, String> definitions = getSonarMetrics();
		if(definitions.containsKey(metric1) && definitions.containsKey(metric2)) {
			//setAxes(metric1, metric2, definitions);
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @param metric1
	 * @param metric2
	 * @param definitions
	 * 
	 * Sets the axis labels in flotOptions, Y axis is alphabetically lower than X
	 * 
	 */
//	public static void setAxes(String metric1, String metric2, Map<String,String> definitions){
//		if(metric1.compareTo(metric2)>0){
//			FlotOptions.setXaxis(definitions.get(metric1));
//			FlotOptions.setYaxis(definitions.get(metric2));
//		}else{
//			FlotOptions.setXaxis(definitions.get(metric2));
//			FlotOptions.setYaxis(definitions.get(metric1));
//		}
//	}
}
