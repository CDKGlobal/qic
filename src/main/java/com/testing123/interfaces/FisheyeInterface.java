package com.testing123.interfaces;

import java.util.Set;

import com.testing123.dataObjects.ConvertPath;
import com.testing123.dataObjects.FisheyeData;
import com.testing123.dataObjects.RepoAndDirData;
import com.testing123.dataObjects.RevisionData;

public interface FisheyeInterface {
		  	  
	  public Set<RevisionData> getRevisionsFromProject(String repository, String directory, String date);
	  
	  public FisheyeData getrevision(RepoAndDirData project, ConvertPath path, String date);
	  
}
