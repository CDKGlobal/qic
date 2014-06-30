package com.testing123.vaadin;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
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
	
	public static Set<DataPoint> getData(){
		return getData("ncloc,complexity");
	}
	
	/**
	 * Grabs the data from a URL and returns the data points needed to be plotted in the form of
	 * a map, with the keys being the x-coordinate and the values being the y-coordinate 
	 * 
	 * @return a Map of x-coordinates mapped to their respective y-coordinates
	 */
	public static Set<DataPoint> getData(String inputMetric) {	
		metric = inputMetric;
		
		MapHolder mh = new MapHolder();
		mapper = new ObjectMapper();
		
		try {
			URL folderLink = new URL(link).toURI().toURL();
			
			/** produces a list of folders **/
			List<JsonClass> folderList = mapper.readValue(folderLink, new TypeReference<List<JsonClass>>() {});
					
			System.out.println("URL SUCCESS");
			ForkJoinPool fjp = new ForkJoinPool();
			
			/** Uses the Java Fork-Join framework to grab all the files in the project. **/
			CallFolders cF = new CallFolders(0, folderList.size(), new MapHolder(), folderList);
			mh = fjp.invoke(cF);
			
			/** stores the data into the map as coordinates, keySet() as x-coords, values() as y-coords **/
			// x: lines of code
			// y: number of files
//			for (double linesOfCode : counts) {
//				int lines = (int) linesOfCode;
////				lines/=10;		// puts the data in bucket sizes
////				lines*=10;
////				lines+= 5;		// organizes the data to be at every 5 points
//				if (coords.containsKey(lines)) {
//					coords.put(lines, coords.get(lines) + 1);				
//				} else {
//					coords.put(lines, 1);
//				}
//			}
			
			for (DataPoint d : mh.fileData) {
				System.out.println(d.toString());
			}
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return mh.fileData;
	}
}