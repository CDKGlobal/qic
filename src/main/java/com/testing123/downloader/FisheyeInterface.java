package com.testing123.downloader;

import java.util.Set;

import com.testing123.dataObjects.RevisionData;

public interface FisheyeInterface {
	
	  public Set<RevisionData> getRevisionsFromProject(String repository, String directory);

}
