package com.testing123.vaadin;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Reader 2 will parse the web API links so data is fetched with multiple threads.
 * 
 * @author chenc
 */
public class Reader2 {
	
	private static final String link = "http://sonar.cobalt.com/api/resources?resource=com.cobalt.dap:platform&depth=-1&scopes=DIR&format=json";
	public static ObjectMapper mapper;
	public static  String metric;
	
	/**
	 * Grabs the data from a URL and returns the data points needed to be plotted in the form of
	 * a map, with the keys being the x-coordinate and the values being the y-coordinate 
	 * 
	 * @return a Map of x-coordinates mapped to their respective y-coordinates
	 */
	public static Set<DataPoint> getData(String inputMetric) {
		String[] metrics = inputMetric.split(",");
		if (metrics.length != 2) {
			return new HashSet<DataPoint>();
		}
		metric = inputMetric;
		
		MapHolder mapHolder = new MapHolder();
		mapper = new ObjectMapper();
		
		try {
			URL folderLink = new URL(link).toURI().toURL();
			
			/** produces a list of folders **/
			List<WebData> folderList = mapper.readValue(folderLink, new TypeReference<List<WebData>>() {});
					
			//System.out.println("URL SUCCESS");
			ForkJoinPool forkJoinPool = new ForkJoinPool();
			
			/** Uses the Java Fork-Join framework to grab all the files in the project. **/
			CallFolders callFolders = new CallFolders(0, folderList.size(), new MapHolder(), folderList);
			mapHolder = forkJoinPool.invoke(callFolders);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return mapHolder.fileData;
	}
}