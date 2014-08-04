package com.testing123.dataObjects;

public class RevisionData {

	private String fisheyePath;
	private int churn;
	private String author;

	public RevisionData() {
		fisheyePath = null;
		churn = -1;
		this.author = null;
	}

	public RevisionData(String line) {
		String[] split = line.split(",");
		this.fisheyePath = split[0].replaceAll("\"", "");
		this.churn = Integer.parseInt(split[3]) + Integer.parseInt(split[2]);
		this.author = split[1].replaceAll("\"", "");
	}

	public String getFisheyePath() {
		return fisheyePath;
	}

	public int getChurn() {
		return churn;
	}

	public String getAuthor() {
		return author;
	}

	@Override
	public String toString() {
		return "RevisionData [Fisheyepath=" + fisheyePath + ", churn=" + churn + ", author=" + author + "]";
	}
	
	public boolean combine(RevisionData other){
		if(this.fisheyePath!=null && this.fisheyePath.equals(other.getFisheyePath())){
			this.churn += other.getChurn();
			this.author += "," + other.getAuthor();
			return true;
		}else{
			return false;
		}
	}
}
