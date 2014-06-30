package com.testing123.vaadin;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestJsonParser {
	static JsonClass one;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		try {
			URL link = new URL("http://sonar.cobalt.com/api/resources?resource=com.cobalt.dap:platform&depth=2&metrics=ncloc&format=json").toURI().toURL();
			
			List<JsonClass> jsonClassList =
				    mapper.readValue(link, new TypeReference<List<JsonClass>>() {});
			
			one = jsonClassList.get(0);
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

	@Test
	public void testGetId() {
		assertEquals(one.getId(), 17737);
	}
	
	@Test
	public void testGetKey() {
		assertEquals(one.getKey(), "com.cobalt.dap:platform:com.cobalt.dap.wicket.view.packageattribute.GeoTargetingEditPage");
	}
	
	@Test
	public void testGetScope() {
		assertEquals(one.getScope(), "FIL");
	}
	
	@Test
	public void testGetQualifier() {
		assertEquals(one.getQualifier(), "CLA");
	}

	@Test
	public void testGetLName() {
		assertEquals(one.getLname(), "com.cobalt.dap.wicket.view.packageattribute.GeoTargetingEditPage");
	}
	
	@Test
	public void testGetMSR() {
		assertEquals((int)one.getMsr().get(0).getVal(), 591);
	}
}
