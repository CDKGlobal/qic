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
	public static String metric1;
	public static String metric2;
	
	/**
	 * Grabs the data from the Sonar Web API given two valid metrics.  Behavior is unspecified
	 * if the metrics passed in do not exist.
	 * 
	 * @param m1, the first metric to get
	 * @param m2, the second metric to get
	 * @return a Map of x-coordinates mapped to their respective y-coordinates
	 */
	public static Set<DataPoint> getData(String m1, String m2) {
		
		if (!SonarMetrics.sonarHasMetrics(m1, m2)) {
			return new HashSet<DataPoint>();
		}
		
		metric1 = m1;
		metric2 = m2;
		
		MapHolder mapHolder = new MapHolder();
		mapper = new ObjectMapper();
		
		try {
			URL folderLink = new URL(link);
			
			/** produces a list of folders **/
			List<WebData> folderList = mapper.readValue(folderLink, new TypeReference<List<WebData>>() {});
			
			/** Uses the Java Fork-Join framework to grab all the files in the project. **/
			ForkJoinPool forkJoinPool = new ForkJoinPool();
			CallFolders callFolders = new CallFolders(0, folderList.size(), new MapHolder(), folderList);
			mapHolder = forkJoinPool.invoke(callFolders);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return mapHolder.fileData;
	}
}