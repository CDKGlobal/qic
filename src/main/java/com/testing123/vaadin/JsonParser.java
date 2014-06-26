package com.testing123.vaadin;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

 
public class JsonParser {
    public static void main(String[] args) {
 
	JsonClass folder = new JsonClass();
	ObjectMapper mapper = new ObjectMapper();
 
	try {
 
		// convert user object to json string, and save to a file
		mapper.writeValue(new File("c:\\user.json"), folder);
 
		// display to console
		System.out.println(mapper.writeValueAsString(folder));
 
	} catch (JsonGenerationException e) {
 
		e.printStackTrace();
 
	} catch (JsonMappingException e) {
 
		e.printStackTrace();
 
	} catch (IOException e) {
 
		e.printStackTrace();
 
	}
 
  }
 
}