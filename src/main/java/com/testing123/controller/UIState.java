package com.testing123.controller;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import com.testing123.ui.FilterComponent;
import com.testing123.vaadin.ConvertDate;
import com.testing123.vaadin.ConvertProject;

public class UIState {
	public static final ConvertDate DEFAULT_START_DATE = new ConvertDate("2014-07-15T06-07-55-0700");
	public static final ConvertDate DEFAULT_END_DATE = new ConvertDate("2014-07-21T06-07-35-0700");

	
	private ConvertDate start;
	private ConvertDate end;
	private Axis x;
	private Axis y;
	private String focus;
	private Granularity grain;
	private Set<ConvertProject> projects;
	
	public UIState() {
		this(DEFAULT_START_DATE, DEFAULT_END_DATE, Axis.DELTA_LINESOFCODE);
	}
	
	public UIState(ConvertDate start, ConvertDate end, Axis x) {
		this.start = start;
		this.end = end;
		this.x = x;
		this.y = Axis.COMPLEXITY;		// by default and always is the case
		this.focus = null;
		this.grain = Granularity.FILE;
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
	
	public Axis getY() {
		return y;
	}

	public void setY(Axis y) {
		this.y = y;
	}
	
	public String getFocus() {
		return focus;
	}

	public void setFocus(String focus) {
		this.focus = focus;
	}
	
	public Granularity getGranularity() {
		return grain;
	}

	public void setGranularity(Granularity grain) {
		this.grain = grain;
	}
	
	public Set<ConvertProject> getProjects() {
		return projects;
	}
	
	public void setProjects(Set<ConvertProject> projects) {
		this.projects = projects;
	}
	
	public void verifyState() {
		System.out.println("CURRENT STATE:");
		System.out.println("Start Date: " + start.toString());
		System.out.println("End Date: " + end.toString());
		System.out.println("XAxis: " + x);
		System.out.println("Focus: " + focus);
		System.out.println("Granularity: " + grain);
		System.out.println();
	}
	
	public enum Axis {
		DELTA_LINESOFCODE("Delta Lines of Codes"), 
		DELTA_COMPLEXITY("Delta Complexity"), 
		LINESOFCODE("Non Commented Lines of Code"),
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
	
	public enum Granularity {
		PROJECT, DIRECTORY, FILE
	}
}