package com.testing123.controller;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.testing123.ui.Preferences;
import com.testing123.vaadin.ConvertDate;
import com.testing123.vaadin.ConvertProject;

public class UIState {
	
	private ConvertDate start;
	private ConvertDate end;
	private Axis x;
	private Axis y;
	private Set<ConvertProject> projects;
	private Set<String> authors;
	
	public UIState() {
		this(Preferences.DEFAULT_START_DATE, Preferences.DEFAULT_END_DATE, Axis.DELTA_LINESOFCODE);
	}
	
	public UIState(ConvertDate start, ConvertDate end, Axis x) {
		this.start = start;
		this.end = end;
		this.x = x;
		this.y = Axis.COMPLEXITY;		// by default and always is the case
		this.projects = new HashSet<ConvertProject>();
		this.authors = new HashSet<String>();
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
		
	public Set<ConvertProject> getProjects() {
		return projects;
	}
	
	public void setProjects(Set<ConvertProject> projects) {
		this.projects = projects;
	}
	
	public Set<String> getAuthorsFilter() {
		return authors;
	}
	
	public void setAuthorsFilter(Set<String> authors) {
		this.authors = authors;
	}
	
	public void verifyState() {
		System.out.println("CURRENT STATE:");
		System.out.println("Start Date: " + start.toString());
		System.out.println("End Date: " + end.toString());
		System.out.println("XAxis: " + x);
		System.out.println("Projects: " + projects);
		System.out.println("Authors: " + authors);
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