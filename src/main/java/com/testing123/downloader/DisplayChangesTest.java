package com.testing123.downloader;

import static org.junit.Assert.*;

import org.junit.Test;

import com.testing123.dataObjects.ConvertDate;

public class DisplayChangesTest {

	@Test
	public void test() {
		ConvertDate startDate = new ConvertDate("2014-08-05");
		ConvertDate endDate = new ConvertDate("2014-08-07");
		String dbPath = "com.vaadin:QIC2:src/main/java/com/testing123/controller/UIState.java";
		new DisplayChanges().fisheyeRevision(dbPath, startDate, endDate);
		System.out.println("done");
	}
	
	@Test
	public void test2() {
		ConvertDate startDate = new ConvertDate("2014-07-01");
		ConvertDate endDate = new ConvertDate("2014-08-07");
		String dbPath = "com.vaadin:QIC2:com.testing123.vaadin.ConvertDate";
		new DisplayChanges().fisheyeRevision(dbPath, startDate, endDate);
		System.out.println("done");
	}

}
