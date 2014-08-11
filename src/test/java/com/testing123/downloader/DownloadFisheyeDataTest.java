package com.testing123.downloader;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.testing123.dataObjects.ChangedData;
import com.testing123.dataObjects.ConvertProject;
import com.testing123.dataObjects.RevisionData;
import com.testing123.interfaces.DatabaseInterface;
import com.testing123.interfaces.FisheyeInterface;
import com.testing123.vaadin.RegexUtil;

public class DownloadFisheyeDataTest {
	
	private static List<ChangedData> test;
	private FisheyeInterface FIMock;
	private DatabaseInterface DBIMock;
	
	@Before
	public void before(){	
		FIMock = Mockito.mock(FisheyeInterface.class);
		DBIMock = Mockito.mock(DatabaseInterface.class);
	}


	@Test
	public void test(){
		
		Set<RevisionData> set1 = getSet1();
		Mockito.when(FIMock.getRevisionsFromProject("QIC.Perforce", "Mock", getCurrentDate())).thenReturn(set1);
		Mockito.when(FIMock.getRevisionsFromProject("QIC.Perforce", "MockEmpty", getCurrentDate())).thenReturn(new HashSet<RevisionData>());
		
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("this.is.one.type.of.path", 18);
		map.put("this/is/the/other/type.java", 21);
		List<ConvertProject> list = new LinkedList<ConvertProject>();
		list.add(new ConvertProject("Unused", "alsoUnused", 0, "/QIC/Mock"));
		list.add(new ConvertProject("NoNameHere", "NoKeyEvenNeeded", 42542, "/QIC/MockEmpty"));
		Mockito.when(DBIMock.getMapToID(0)).thenReturn(map);
		Mockito.when(DBIMock.getAvailableProjects()).thenReturn(list);
		
		test = new DownloadFisheyeData(FIMock, DBIMock).getAllFisheyeUpdates();
		
		assertEquals(2,test.size());
		
		ChangedData meMyselfIrine = new ChangedData(21, getCurrentDate(), 76, "[Me, Irene, Myself]");
		ChangedData chris = new ChangedData(18,getCurrentDate(),13,"[Chris]");
		
		System.out.println(test);
		assertTrue(test.contains(meMyselfIrine));
		assertTrue(test.contains(chris));
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

	
	private void add(Set<RevisionData> set, String line){
		if(RegexUtil.isRevisionData(line)){
			set.add(new RevisionData(line));
		}
	}
	
	private String getCurrentDate(){
		DateTime today = new DateTime();
		return "" + today.getYear() + "-" + today.getMonthOfYear() + "-" + today.getDayOfMonth();
	}
	
}
