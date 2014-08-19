package com.testing123.dataObjects;

public class FisheyeInfo {

	private int latestRevision;
	private int startingRevision;
	private String completeFisheyePath;
	
	public FisheyeInfo(int latestRevision, String completeFisheyePath) {
		this.latestRevision = latestRevision;
		this.startingRevision = latestRevision;
		this.completeFisheyePath = completeFisheyePath;
	}
	
	public void setStartingRevision(FisheyeInfo startingInfo){
		if(this.completeFisheyePath.equals(startingInfo.getCompleteFisheyePath()))
			this.startingRevision = startingInfo.getStartingRevision();
	}
	
	public int getLatestRevision() {
		return latestRevision;
	}
	public int getStartingRevision() {
		return startingRevision;
	}
	public String getCompleteFisheyePath() {
		return completeFisheyePath;
	}
	
}
