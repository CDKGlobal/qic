package com.testing123.interfaces;

import java.util.Set;

import com.testing123.dataObjects.ConvertDate;
import com.testing123.dataObjects.ConvertPath;
import com.testing123.dataObjects.FisheyeInfo;
import com.testing123.dataObjects.RepoAndDirData;
import com.testing123.dataObjects.RevisionData;

public interface FisheyeInterface {
		  	  
	  public Set<RevisionData> getRevisionsFromProject(String repository, String directory, String date);
	  
	  public FisheyeInfo ExtractFisheyeInfo(RepoAndDirData project, ConvertPath path, ConvertDate startDate, ConvertDate endDate);

}
