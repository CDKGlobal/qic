package com.testing123.vaadin;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

public class DataPointTest {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    private DataPoint dataPointFactory() {
        return new DataPoint();
    }

    @Test
    public void TestEmptyDataPointReturnsDefaultValues() {
        DataPoint data = dataPointFactory();
        assertEquals(null, data.getKey());
        assertEquals(-1, (int)data.getComplexity());
        assertEquals(-1, (int)data.getLineOfCode());
    }

    @Test
    public void TestSetAndGetKey() {
        DataPoint data = dataPointFactory();
        data.setKey("key");
        assertEquals("key", data.getKey());
        assertEquals(-1, (int)data.getComplexity());
        assertEquals(-1, (int)data.getLineOfCode());
    }

    @Test
    public void TestSetAndGetComplexity() {
        DataPoint data = dataPointFactory();
        data.setComplexity(100);
        assertEquals(null, data.getKey());
        assertEquals(100, (int)data.getComplexity());
        assertEquals(-1, (int)data.getLineOfCode());
    }

    @Test
    public void TestSetAndGetLineOfCode() {
        DataPoint data = dataPointFactory();
        data.setLineOfCode(500);
        assertEquals(null, data.getKey());
        assertEquals(-1, (int)data.getComplexity());
        assertEquals(500, (int)data.getLineOfCode());
    }

    @Test
    public void TestSetAndToString() {
        DataPoint data = dataPointFactory();
        data.setKey("boy");
        data.setLineOfCode(500);
        data.setComplexity(100);
        // System.out.println(data.toString());
        assertEquals("[500.0, 100.0, \"boy\"]", data.toString());
    }
}
