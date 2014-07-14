package com.testing123.vaadin;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class FisheyeQuery {

	private String repository;
	private String dateRange;
	private String clauses;
	// private String grouping;
	private String returns;

	public FisheyeQuery(String repository, ConvertDate startDate, ConvertDate endDate) {
		this.repository = repository;
		dateRange = getDateRange(startDate, endDate);
		clauses = "";
		returns = "";
		returns = "";
	}

	public void clearClauses() {
		clauses = "";
	}

	public void clearReturns() {
		returns = "";
	}

	public void addPath(String path) {
		addClause(" path like " + path);
	}

	public void inProject(String project) {
		addPath("\\" + project + "/trunk/src/main/**");
	}

	public void onlyJava() {
		addPath("*.java");
	}
	
	public void addReturn(String returnElement){
		returns += ", " + returnElement;
	}
	
	private void addClause(String clause){
		clauses += " and " + clause;
	}

	private String getClauses() {
		if(clauses.isEmpty())
			return clauses;
		return clauses.substring(4);
	}

	private String getReturns() {
		if(returns.isEmpty()){
			///ERROR
			addReturn("path");
		}
		return " return " + returns.substring(1);
	}

	private String getQuery() {
		return "select revisions " + dateRange + getClauses() + getReturns();
	}
	
	private String getDateRange(ConvertDate startDate, ConvertDate endDate) {
		return " where date in [" + startDate.getSonarFormat().substring(0, 10) + "," + endDate.getSonarFormat().substring(0, 10)
				+ "] ";
	}

	@Override
	public String toString() {
		String home = "http://fisheye.cobalt.com/rest-service-fe/search-v1/queryAsRows/";
		String link = home + repository + ".json?query=" + getQuery();
		return link.replaceAll("\\s+", "%20");
	}
}