package com.testing123.downloader;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import com.testing123.dataObjects.ChangedData;
import com.testing123.dataObjects.RevisionData;
import com.testing123.vaadin.RegexUtil;

public class DownloadFisheyeDataTest {
	
	private static List<ChangedData> test;
	
	@Before
	public void onlyOnce(){
		FisheyeInterface FIMock = Mockito.mock(FisheyeInterface.class);
		Set<RevisionData> set1 = getSet1();
		Mockito.when(FIMock.getRevisionsFromProject("QIC.Perforce", "Mock")).thenReturn(set1);
		Mockito.when(FIMock.getRevisionsFromProject("QIC.Perforce", "MockEmpty")).thenReturn(new HashSet<RevisionData>());
		test = new DownloadFisheyeData(FIMock, new MockDBI()).getAllFisheyeUpdates();
	}

	private Set<RevisionData> getSet1() {
		Set<RevisionData> set = new HashSet<RevisionData>();
		add(set,"\"/Different/useless/starter/this/is/the/other/type.java\",\"Me\",0,13");
		add(set,"\"/Different/useless/starter/this/is/the/other/type.java\",\"Me\",0,13");
		add(set,"\"/Different/useless/starter/this/is/the/other/type.java\",\"Myself\",11,3");
		add(set,"\"/Different/useless/starter/this/is/the/other/type.java\",\"Irene\",7,29");
		add(set,"STRING DOESNT MATcH OH NO!");
		add(set,"\"/Useless/starter/this/is/one/type/of/path.java\",\"Chris\",0,13");
		add(set,"\"path/doesnt/match\",\"Weiyou\",0,13");
		return set;
	}

	@Test
	public void test(){
		assertEquals(2,test.size());
		ChangedData meMyselfIrine = new ChangedData(21, "2014-8-8", 76, "[Me, Irene, Myself]");
		ChangedData chris = new ChangedData(18,"2014-8-8",13,"[Chris]");
		assertTrue(test.contains(meMyselfIrine));
		assertTrue(test.contains(chris));
	}

	
	private void add(Set<RevisionData> set, String line){
		if(RegexUtil.isRevisionData(line)){
			set.add(new RevisionData(line));
		}
	}
	
	
	
}
