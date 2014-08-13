package com.testing123.vaadin;

import java.util.List;

import com.testing123.dataObjects.FisheyeData;
import com.testing123.dataObjects.ItemData;

public class ExtractFisheyeInfo {

	private int revision1;
	private int revision2;
	private String completeFisheyePath;
	
	public int getRevision1() {
		return revision1;
	}

	public int getRevision2() {
		return revision2;
	}

	public String getCompleteFisheyePath() {
		return completeFisheyePath;
	}
	
	public ExtractFisheyeInfo(FisheyeData data){
		setValues(data);
	}
	
	public boolean exists(){
		return revision1!=-1 && revision2!=-1 && completeFisheyePath!=null;
	}
	
	private void setValues(FisheyeData data){
		int csidIndex = getIndex(data, "csid");
		int pathIndex = getIndex(data, "path");
		List<ItemData> revisionList = data.getRow();
		int numOfRevisions = revisionList.size();

		if (csidIndex != -1 && pathIndex != -1 && numOfRevisions > 0) {
			revision1 = Integer.parseInt((String) revisionList.get(0).getItem(csidIndex));
			revision2 = Integer.parseInt((String) revisionList.get(numOfRevisions - 1).getItem(csidIndex));
			completeFisheyePath = (String) revisionList.get(0).getItem(pathIndex);
		}else{
			revision1 = -1;
			revision2 = -1;
			completeFisheyePath = null;
		}
	}
	
	private int getIndex(FisheyeData data, String key) {
		return data.getHeadings().indexOf(key);
	}
}
