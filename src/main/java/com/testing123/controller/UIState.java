package com.testing123.controller;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;

import com.testing123.dataObjects.ConvertDate;
import com.testing123.dataObjects.ConvertProject;
import com.testing123.ui.Preferences;

public class UIState {
	public static final ConvertDate defaultStart = Preferences.DEFAULT_START_DATE;
	public static final ConvertDate defaultEnd = Preferences.DEFAULT_END_DATE;
	
	private ConvertDate start;
	private ConvertDate end;
	private XAxis x;
	private YAxis y;
	private Set<ConvertProject> projects;
	private Set<String> authors;

	public UIState() {
		this(new ConvertDate(new DateTime().minusDays(7).toDate()), new ConvertDate(new DateTime().toDate()), XAxis.DELTA_LINESOFCODE);
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
	
	/**
	 * Returns a URL that can be reused to build the current state
	 * 
	 * @return a String representation of the current state as a URL
	 */
	public String getStateURI(String dateSetting, String dateOffset) {
		StringBuilder sb = new StringBuilder(Preferences.URL + "/");
		sb.append("?st=").append(dateSetting);
		sb.append("&end=").append(dateOffset);
		sb.append("&x=").append(x.getColName());
		sb.append("&y=").append(y.getColName());
		sb.append("&proj=").append(unwrapProjects(projects));
		sb.append("&auth=").append(unwrapAuthors(authors));
		return sb.toString();
	}
	
	public String getStateURI() {
		return getStateURI(start.getDBFormat(), end.getDBFormat());
	}
	
	private String unwrapAuthors(Set<String> authors) {
		StringBuilder bld = new StringBuilder();
		for (String author : authors) {
			bld.append(",");
			bld.append(author);
		}
		return bld.toString();
	}

	private String unwrapProjects(Set<ConvertProject> projects) {
		StringBuilder bld = new StringBuilder();
		for (ConvertProject project : projects) {
			bld.append(",");
			bld.append(project.getID());
		}
		return bld.toString();
	}
	
	public interface Axis {
		
		public String getColName();
		
	}
	
	public enum XAxis implements Axis {
		DELTA_LINESOFCODE("Churn (Lines Modified)", "churn", "Lines Modified vs Final Complexity"), 
		DELTA_COMPLEXITY("Change in Complexity", "delta_complexity", "Change in Complexity vs Final Complexity"), 
		//DELTA_ISSUES("Change in Issues", "delta_issues"),
		//DELTA_COVERAGE("Delta Coverage", "delta_coverage"),
		LINESOFCODE("Non Comment Lines of Code", "ncloc", "Lines of Code vs Complexity for All Files");
		
		private String detail;
		private String dbCol;
		private String description;
		
		private XAxis(String detail, String dbCol, String descr) {
			this.detail = detail;
			this.dbCol = dbCol;
			this.description = descr;
		}
		
		public String getView() {
			return description;
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
		COMPLEXITY("Final Cyclomatic Complexity", "complexity", "Final Cyclomatic Complexity"),
		ISSUES("Issues", "issues", ""),
		COVERAGE("Branch Coverage", "coverage", "");
		
		private String detail;
		private String dbCol;
		private String description;
		
		private YAxis(String detail, String dbCol, String descr) {
			this.detail = detail;
			this.dbCol = dbCol;
			this.description = descr;
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