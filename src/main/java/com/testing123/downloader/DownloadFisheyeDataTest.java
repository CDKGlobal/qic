package com.testing123.downloader;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import com.testing123.dataObjects.RevisionData;

public class DownloadFisheyeDataTest {

	@Test
	public void testAggregatorEmpty() {
		Set<RevisionData> empty = new HashSet<RevisionData>();
		Set<RevisionData> test = DownloadFisheyeData.aggregateRevisions(empty);
		assertTrue(test.isEmpty());
	}

	@Test
	public void testSeveralUniqueRemainUnchanged(){
		Set<RevisionData> severalUnique = new HashSet<RevisionData>();
		severalUnique.add(new RevisionData("\"a\",\"name1\",1,2"));
		severalUnique.add(new RevisionData("\"b\",\"name2\",4,3"));
		severalUnique.add(new RevisionData("\"c\",\"name3\",8,5"));
		Set<RevisionData> test = DownloadFisheyeData.aggregateRevisions(severalUnique);
		assertEquals(3,test.size());
	}
	
	@Test
	public void testCombineTwo(){
		Set<RevisionData> combineTwo = new HashSet<RevisionData>();
		combineTwo.add(new RevisionData("\"a\",\"name1\",1,2"));
		combineTwo.add(new RevisionData("\"a\",\"name2\",4,3"));
		Set<RevisionData> test = DownloadFisheyeData.aggregateRevisions(combineTwo);
		assertEquals(1,test.size());
		Iterator<RevisionData> i = test.iterator();
		RevisionData combined = i.next();
		assertEquals(10,combined.getChurn());
		assertEquals("name1,name2",combined.getAuthor());
	}
}
