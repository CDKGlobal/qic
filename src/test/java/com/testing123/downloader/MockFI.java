package com.testing123.downloader;

import java.util.HashSet;
import java.util.Set;

import com.testing123.dataObjects.RevisionData;
import com.testing123.vaadin.RegexUtil;

public class MockFI implements FisheyeInterface {

	@Override
	public Set<RevisionData> getRevisionsFromProject(String repository, String directory) {
		Set<RevisionData> set = new HashSet<RevisionData>();
		if(repository.equals("QIC.Perforce") && directory.equals("Mock")){
			add(set,"\"/Different/useless/starter/this/is/the/other/type.java\",\"Me\",0,13");
			add(set,"\"/Different/useless/starter/this/is/the/other/type.java\",\"Me\",0,13");
			add(set,"\"/Different/useless/starter/this/is/the/other/type.java\",\"Myself\",11,3");
			add(set,"\"/Different/useless/starter/this/is/the/other/type.java\",\"Irene\",7,29");
			add(set,"STRING DOESNT MATcH OH NO!");
			add(set,"\"/Useless/starter/this/is/one/type/of/path.java\",\"Chris\",0,13");
			add(set,"\"path/doesnt/match\",\"Weiyou\",0,13");
			System.out.println(set.size());
			return set;
			
		}else if(repository.equals("QIC.Perforce") && directory.equals("MockEmpty")){
			return set;
	}
		return null;
	}
	
	private void add(Set<RevisionData> set, String line){
		if(RegexUtil.isRevisionData(line)){
			set.add(new RevisionData(line));
		}
	}

}
