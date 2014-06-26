
//does this help check out?
package com.testing123.vaadin;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.MappingJsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Reader {
	//private static final URL link = new URL("http://sonar.cobalt.com/api/resources?resource=com.cobalt.dap:platform&depth=2&metrics=ncloc&format=json").toURI().toURL();

	public static void main(String[] args) {
		
		ObjectMapper mapper = new ObjectMapper();

		try {
			URL link = new URL("http://sonar.cobalt.com/api/resources?resource=com.cobalt.dap:platform&depth=2&metrics=ncloc&format=json").toURI().toURL();
			
			List<JsonClass> jsonClassList =
				    mapper.readValue(link, new TypeReference<List<JsonClass>>() {});
			
			JsonClass one = jsonClassList.get(0);
			System.out.println(one.getId());
			System.out.println(one.getKey());
			System.out.println(one.getName());
			System.out.println(one.getScope());
			System.out.println(one.getQualifier());
			System.out.println(one.getLname());
			System.out.println(one.getMsr().getVal());
			System.out.println(jsonClassList.size());
		
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
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
