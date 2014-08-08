package com.testing123.dataObjects;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class QueryDataTest {

    private QueryData QueryDataFactory() {
        return new QueryData();
    }

    @Test
    public void TestSetAndGetKey() {
        QueryData qd = QueryDataFactory();
        qd.setKey("key");
        assertEquals("key", qd.getKey());
    }

    @Test
    public void TestSetAndGetName() {
        QueryData qd = QueryDataFactory();
        qd.setName("name");
        assertEquals("name", qd.getName());
    }

    @Test
    public void TestSetAndGetMetricNcloc() {
        QueryData qd = QueryDataFactory();
        qd.setMetric("ncloc", 0.0);
        assertEquals(0, (int) qd.getMetric("noloc"));
    }

    @Test
    public void TestSetAndGetMetricChurn() {
        QueryData qd = QueryDataFactory();
        qd.setMetric("churn", 1.0);
        assertEquals(1, (int) qd.getMetric("churn"));
    }

    @Test
    public void TestSetAndGetMetricComplexity() {
        QueryData qd = QueryDataFactory();
        qd.setMetric("complexity", 2.0);
        assertEquals(2, (int) qd.getMetric("complexity"));
    }

    @Test
    public void TestSetAndGetMetricDelta_complexity() {
        QueryData qd = QueryDataFactory();
        qd.setMetric("delta_complexity", 3.0);
        assertEquals(3, (int) qd.getMetric("delta_complexity"));
    }

    @Test
    public void TestSetAndGetAuthors() {
        QueryData qd = QueryDataFactory();
        List<String> list = new ArrayList<String>();
        list.add("author");
        list.add("weiyoud");
        qd.setAuthors(list);
        assertEquals("[author, weiyoud]", qd.getAuthors().toString());
    }

}
