package com.testing123.vaadin;

import static org.junit.Assert.assertEquals;

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
        assertEquals(17737, one.getId());
    }

    @Test
    public void testGetKey() {
        assertEquals("com.cobalt.dap:platform:com.cobalt.dap.wicket.view.packageattribute.GeoTargetingEditPage", one.getKey());
    }

    @Test
    public void testGetScope() {
        assertEquals("FIL", one.getScope());
    }

    @Test
    public void testGetQualifier() {
        assertEquals("CLA", one.getQualifier());
    }

    @Test
    public void testGetLName() {
        assertEquals("com.cobalt.dap.wicket.view.packageattribute.GeoTargetingEditPage", one.getLname());
    }

    @Test
    public void testGetMSR() {
        assertEquals(591, (int)one.getMsr().get(0).getVal());
    }

    @Test
    public void testGetName() {
        assertEquals("GeoTargetingEditPage", one.getName());
    }

}
