
//does this help check out?
package com.testing123.vaadin;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Reader {

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
