package com.testing123.dataObjects;

import java.util.HashSet;
import java.util.Set;

public class RevisionData {

	private String fisheyePath;
	private int churn;
	private Set<String> author;

	public RevisionData() {
		fisheyePath = null;
		churn = -1;
		this.author = new HashSet<String>();
	}

	public RevisionData(String line) {
		this();
		String[] split = line.split(",");
		this.fisheyePath = split[0].replaceAll("\"", "");
		this.churn = Integer.parseInt(split[3]) + Integer.parseInt(split[2]);
		this.author.add(split[1].replaceAll("\"", ""));
	}
	
	public RevisionData(RevisionData one, RevisionData two){
		if(one.fisheyePath!=null && one.fisheyePath.equals(two.getFisheyePath())){
			one.churn += two.getChurn();
			one.author.addAll(two.getAuthor());
		}
	}

	public String getFisheyePath() {
		return fisheyePath;
	}

	public int getChurn() {
		return churn;
	}

	public Set<String> getAuthor() {
		return author;
	}

	@Override
	public String toString() {
		return "RevisionData [Fisheyepath=" + fisheyePath + ", churn=" + churn + ", author=" + author + "]";
	}
	
	public boolean combine(RevisionData other){
		if(this.fisheyePath!=null && this.fisheyePath.equals(other.getFisheyePath())){
			this.churn += other.getChurn();
			this.author.addAll(other.getAuthor());
			return true;
		}else{
			return false;
		}
	}
}
