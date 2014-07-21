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
        assertEquals(-1, (int)data.getYValue());
        assertEquals(-1, (int)data.getXValue());
    }

    @Test
    public void TestSetAndGetKey() {
        DataPoint data = dataPointFactory();
        data.setKey("key");
        assertEquals("key", data.getKey());
        assertEquals(-1, (int)data.getYValue());
        assertEquals(-1, (int)data.getXValue());
    }

    @Test
    public void TestSetAndGetComplexity() {
        DataPoint data = dataPointFactory();
        data.setYValue(100);
        assertEquals(null, data.getKey());
        assertEquals(100, (int)data.getYValue());
        assertEquals(-1, (int)data.getXValue());
    }

    @Test
    public void TestSetAndGetLineOfCode() {
        DataPoint data = dataPointFactory();
        data.setXValue(500);
        assertEquals(null, data.getKey());
        assertEquals(-1, (int)data.getYValue());
        assertEquals(500, (int)data.getXValue());
    }

    @Test
    public void TestSetAndToString() {
        DataPoint data = dataPointFactory();
        data.setKey("boy");
        data.setXValue(500);
        data.setYValue(100);
        // System.out.println(data.toString());
        assertEquals("[500.0, 100.0, \"boy\"]", data.toString());
    }
}
