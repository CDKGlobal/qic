package com.testing123.vaadin;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Reads from the API
 * @author chenc
 *
 */
public class Reader {
	private static final String link = "http://sonar.cobalt.com/api/resources?resource=com.cobalt.dap:platform&depth=-1&scopes=DIR&format=json";
	
	public static Map<Integer, Integer> getData(){
		return getData("ncloc");
	}
	
	public static Map<Integer, Integer> getData(String metric) {
		
		Map<Integer, Integer> coords = new TreeMap<Integer, Integer>();
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			URL folderLink = new URL(link).toURI().toURL();
			
			List<JsonClass> folderList = mapper.readValue(folderLink, new TypeReference<List<JsonClass>>() {});
			
			//System.out.println("Number of folders: " + folderList.size());			
			
			//int csum = 0;
			HashMap<String, Double> counts = new HashMap<String, Double>();
			for (JsonClass folder : folderList) {
				String currentFolder = "http://sonar.cobalt.com/api/resources?resource=" + folder.getKey() + "&depth=1&metrics=" + metric + "&format=json";
				
//				String currentFolder = "http://sonar.cobalt.com/api/resources?resource=com.cobalt.dap:platform:com.cobalt.dap.wicket.view.dmag.report&depth=1&metrics=ncloc&format=json";
				URL filesLink = new URL(currentFolder);
				List<JsonClass> fileList = mapper.readValue(filesLink, new TypeReference<List<JsonClass>>() {});
				//System.out.println(folder.getKey());
				//System.out.println("folder: " + i);

				for (JsonClass file : fileList) {
					if (fileList.size() != 0) {
						if (counts.containsKey(file.getName())) {
							counts.put(file.getName() + " (2)", file.getMsr().getVal());
							continue;
						}
						counts.put(file.getName(), file.getMsr().getVal());
					}
					//csum++;
				}
//				System.out.println(csum);
//				System.out.println(csum + "\t" + folder.getName());
			}
			//System.out.println(csum);
			
//			int sum = 0;
//			System.out.println("Number of files :" + counts.size());
//			for (String d : counts.keySet()) {
//				System.out.println(d + ": " + counts.get(d) + " lines");
//				sum += counts.get(d);
//			}
			
			//System.out.println("TOTAL LINES: " + sum);
			
			
			/** stores the data into the map as coordinates, keySet() as x-coords, values() as y-coords **/
			// x: lines of code
			// y: number of files
			for (double linesOfCode : counts.values()) {
				int lines = (int) linesOfCode;
				lines/=10;
				lines*=10;
				lines+= 5;
				if (coords.containsKey(lines)) {
					coords.put(lines, coords.get(lines) + 1);				
				} else {
					coords.put(lines, 1);
				}
			}
			
//			for (int lines : coords.keySet()) {
//				System.out.println(lines + ", " + coords.get(lines));
//			}
		
		//Catch exceptions
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return coords;
	}

	
	/**
	 * Parses a JSON file
	 * 
	 * @param fileName the name of the file
	 * @param result the parsed JSON string
	 * @return
	 * @throws Exception 
	 */
	public static String JSONParser(String fileName) throws Exception {
		String result = "";
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(fileName));

			String current;
			while ((current = reader.readLine()) != null) {
				result += current;
			}
		} catch (IOException e) {
			e.printStackTrace(System.err);
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
		return result;
	}
}