package com.testing123.dataObjects;

import java.util.HashSet;
import java.util.Set;

import com.testing123.ui.Preferences;

public class DataPointSet {
	private Set<DataPoint> negVals;
	private Set<DataPoint> posVals;
	
	public DataPointSet() {
		this.negVals = new HashSet<DataPoint>();
		this.posVals = new HashSet<DataPoint>();
	}
	
	public void addNeg(DataPoint pt) {
		negVals.add(pt);
	}
	
	public void addPos(DataPoint pt) {
		posVals.add(pt);
	}
	
	public Set<DataPoint> getNegVals() {
		return negVals;
	}

	public Set<DataPoint> getPosVals() {
		return posVals;
	}

	public String toString() {
		return "[ { data: " + negVals + ", points: { show: true, fill: true, fillColor: \"" + 
				Preferences.FILL_COLOR_NEG + "\" } }, " + 
				"{ data: " + posVals + ", points: { show: true, fill: true, fillColor: \"" + 
				Preferences.FILL_COLOR_POS + "\" }} ]";
	}
}
