package com.testing123.downloader;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import com.testing123.dataObjects.ChangedData;
import com.testing123.dataObjects.RevisionData;

public class DownloadFisheyeDataTest {
	
	private static List<ChangedData> test;
	
	@BeforeClass
	public static void onlyOnce(){
		test = new DownloadFisheyeData(new MockFI(), new MockDBI()).getAllFisheyeUpdates();
	}

	@Test
	public void test(){
		System.out.println("listSize = " + test.size());
		for (ChangedData cd:test){
			System.out.println(cd.toString());
		}
	}
	
}
