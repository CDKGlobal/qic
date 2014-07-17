package com.testing123.controller;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import com.testing123.vaadin.ConvertDate;

public class UIState {
	private ConvertDate start;
	private ConvertDate end;
	//private boolean isDelta;
	private Axis x;
	private String grain;
	
	public UIState() {
		this(new ConvertDate("2014-07-07T06-09-17-0700"), new ConvertDate("2014-07-10T06-07-56-0700"));
	}
	
	public UIState(ConvertDate start, ConvertDate end) {
		this.start = start;
		this.end = end;
		//this.isDelta = true;
		this.grain = "Files";
	}

	public ConvertDate getStart() {
		return start;
	}

	public void setStart(ConvertDate start) {
		this.start = start;
	}

	public ConvertDate getEnd() {
		return end;
	}
	
	public void setEnd(ConvertDate end) {
		this.end = end;
	}

	//public boolean getIsDelta() {
	//	return isDelta;
	//}
	
	//public void setIsDelta(boolean isDelta) {
	//	this.isDelta = isDelta;
	//}

	public Axis getX() {
		return x;
	}

	public void setX(Axis x) {
		this.x = x;
	}
	
	public String getGrain() {
		return grain;
	}

	public void setGrain(String grain) {
		this.grain = grain;
	}
	
	public void verifyState() {
		System.out.println("CURRENT STATE:");
		System.out.println("Start Date: " + start.toString());
		System.out.println("End Date: " + end.toString());
		System.out.println("XAxis: " + x);
		System.out.println("Granularity: " + grain);
		System.out.println();
	}
	
	public enum Axis {
		DELTA_LINESOFCODE("Delta Lines of Codes"), 
		DELTA_COMPLEXITY("Delta Complexity"), 
		COMPLEXITY("Complexity");
		
		private String detail;
		
		private Axis(String detail) {
			this.detail = detail;
		}
		
		@Override
		public String toString() {
			return detail;
		}
		
		public static List<Axis> possibleValues() {
			return new ArrayList<Axis>(EnumSet.allOf(Axis.class));
		}
	}
	
	public enum Grainularity {
		PROJECT, DIRECTORY, FILE
	}
}