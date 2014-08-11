package com.testing123.interfaces;

import java.util.Set;

import com.testing123.dataObjects.ConvertPath;
import com.testing123.dataObjects.FisheyeData;
import com.testing123.dataObjects.RevisionData;

public interface FisheyeInterface {
		  	  
	  public FisheyeData getRevisionList(String repository,String directory, ConvertPath path, String startDate, String endDate);

	  public Set<RevisionData> getRevisionsFromProject(String repository, String directory, String date);

}
