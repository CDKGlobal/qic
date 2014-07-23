package com.testing123.vaadin;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeSet;

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
		assertEquals(-1, (int) data.getYValue());
		assertEquals(-1, (int) data.getXValue());
	}

	@Test
	public void TestSetAndGetKey() {
		DataPoint data = dataPointFactory();
		data.setKey("key");
		assertEquals("key", data.getKey());
		assertEquals(-1, (int) data.getYValue());
		assertEquals(-1, (int) data.getXValue());
	}

	@Test
	public void TestSetAndGetComplexity() {
		DataPoint data = dataPointFactory();
		data.setYValue(100);
		assertEquals(null, data.getKey());
		assertEquals(100, (int) data.getYValue());
		assertEquals(-1, (int) data.getXValue());
	}

	@Test
	public void TestSetAndGetLineOfCode() {
		DataPoint data = dataPointFactory();
		data.setXValue(500);
		assertEquals(null, data.getKey());
		assertEquals(-1, (int) data.getYValue());
		assertEquals(500, (int) data.getXValue());
	}

	@Test
	public void TestSetAndToString() {
		DataPoint data = dataPointFactory();
		data.setKey("boy");
		data.setXValue(500);
		data.setYValue(100);
		assertEquals("[500.0, 100.0, \"boy\", \"Authors: \"]", data.toString());
	}

	@Test
	public void TestAddingSingleAuthor() {
		DataPoint data = dataPointFactory();
		data.addAuthor("name");
		assertEquals("[-1.0, -1.0, \"null\", \"Authors: name \"]", data.toString());
	}

	@Test
	public void TestAddingAuthorList() {
		DataPoint data = dataPointFactory();
		data.setAuthors(new ArrayList<String>(Arrays.asList("Me", "Myself", "I")));
		assertEquals("[-1.0, -1.0, \"null\", \"Authors: Me I Myself \"]", data.toString());
	}

	@Test
	public void TestComparable() {
		TreeSet<DataPoint> set = new TreeSet<DataPoint>();
		set.add(new DataPoint("AString", 12.0, 18.5));
		set.add(new DataPoint("AString", 12.0, 18.5));
		set.add(new DataPoint("NotString", 12.0, 18.5));
		set.add(new DataPoint("NotString", 11.0, 18.5));
		set.add(new DataPoint("NotString", 11.0, 20));
		assertEquals(
				"[[12.0, 18.5, \"AString\", \"Authors: \"], [11.0, 18.5, \"NotString\", \"Authors: \"], [11.0, 20.0, \"NotString\", \"Authors: \"], [12.0, 18.5, \"NotString\", \"Authors: \"]]",
				set.toString());
	}
}
