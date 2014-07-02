package com.testing123.vaadin;

import static org.junit.Assert.*;

import java.net.URL;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestJsonParser {
	static WebData one;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		URL link = new URL("http://sonar.cobalt.com/api/resources?resource=com.cobalt.dap:platform&depth=2&metrics=ncloc&format=json")
				.toURI().toURL();

		List<WebData> jsonClassList = mapper.readValue(link, new TypeReference<List<WebData>>() {});

		one = jsonClassList.get(0);
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
