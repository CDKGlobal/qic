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
	public static ConvertDate default_start = Preferences.DEFAULT_START_DATE;
	public static ConvertDate default_end = Preferences.DEFAULT_END_DATE;
	
	private ConvertDate start;
	private ConvertDate end;
	private XAxis x;
	private YAxis y;
	private Set<ConvertProject> projects;
	private Set<String> authors;

	public UIState() {
		this(default_start, default_end, XAxis.DELTA_LINESOFCODE);
	}
	
	public UIState(ConvertDate start, ConvertDate end, XAxis x) {
		this.start = start;
		this.end = end;
		this.x = x;
		this.y = YAxis.COMPLEXITY;		// by default and always is the case
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

	public XAxis getX() {
		return x;
	}

	public void setX(XAxis x) {
		this.x = x;
	}
	
	public YAxis getY() {
		return y;
	}

	public void setY(YAxis y) {
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
		System.out.println("Start Date: " + start.getDBFormat());
		System.out.println("End Date: " + end.getDBFormat());
		System.out.println("XAxis: " + x);
		System.out.println("Projects: " + projects);
		System.out.println("Authors: " + authors);
	}
	
	public interface Axis {
		
		public String getColName();
		
	}
	
	public enum XAxis implements Axis {
		DELTA_LINESOFCODE("Delta Lines of Codes", "churn"), 
		DELTA_COMPLEXITY("Delta Complexity", "delta_complexity"), 
		LINESOFCODE("Non Commented Lines of Code", "ncloc");
		
		private String detail;
		private String dbCol;
		
		private XAxis(String detail, String dbCol) {
			this.detail = detail;
			this.dbCol = dbCol;
		}
		
		@Override
		public String toString() {
			return detail;
		}
		
		@Override
		public String getColName() {
			return dbCol;
		}
		
		public static List<XAxis> possibleValues() {
			return new ArrayList<XAxis>(EnumSet.allOf(XAxis.class));
		}
	}
	
	public enum YAxis implements Axis {
		COMPLEXITY("Complexity", "complexity");
		
		private String detail;
		private String dbCol;
		
		private YAxis(String detail, String dbCol) {
			this.detail = detail;
			this.dbCol = dbCol;
		}
		
		@Override
		public String toString() {
			return detail;
		}
		
		@Override
		public String getColName() {
			return dbCol;
		}
		
		public static List<YAxis> possibleValues() {
			return new ArrayList<YAxis>(EnumSet.allOf(YAxis.class));
		}
	}
}