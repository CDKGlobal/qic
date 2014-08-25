package com.testing123.dataObjects;

import java.util.HashSet;
import java.util.Set;

import com.testing123.vaadin.RegexUtil;

public class RevisionData {

	private String fisheyePath;
	private int churn;
	private Set<String> author;
	private boolean isDeleted;

	public RevisionData() {
		fisheyePath = null;
		churn = -1;
		this.author = new HashSet<String>();
		this.isDeleted = false;
	}

	public RevisionData(String line) {
		this();
		if (RegexUtil.isRevisionData(line)) {
			String[] split = line.split(",");
			this.fisheyePath = split[0].replaceAll("\"", "");
			this.churn = Integer.parseInt(split[3]) + Integer.parseInt(split[2]);
			this.author.add(split[1].replaceAll("\"", ""));
			this.isDeleted = Boolean.parseBoolean(split[4].replaceAll("\"", ""));
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

	public boolean getIsDeleted() {
		return isDeleted;
	}

	@Override
	public String toString() {
		return "RevisionData [Fisheyepath=" + fisheyePath + ", churn=" + churn + ", author=" + author + "]";
	}

	public boolean combine(RevisionData other) {
		if (this.fisheyePath != null && this.fisheyePath.equals(other.getFisheyePath())) {
			this.churn += other.getChurn();
			this.author.addAll(other.getAuthor());
			this.isDeleted = this.isDeleted || other.getIsDeleted();
			return true;
		} else {
			return false;
		}
	}
}
