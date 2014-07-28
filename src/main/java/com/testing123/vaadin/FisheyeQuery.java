package com.testing123.vaadin;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;

public class FisheyeQuery {

	private String repository;
	private String dateRange;
	private String clauses;
	private String returns;

	public FisheyeQuery(String repository, ConvertDate startDate, ConvertDate endDate) {
		this.repository = repository;
		dateRange = getDateRange(startDate, endDate);
		clauses = "";
		returns = "";
	}
	
	public URL getAuthorsURL(String project, Set<String> setOfAuthorNames){
		getAuthorsState(project);
		if(!setOfAuthorNames.isEmpty()){
			addAuthorFilter(setOfAuthorNames);
		}
		return getURL(getString());
	}
	
	public URL getChurnURL(String project){
		inProject(project);
		onlyJava();
		addReturn("path");
		addReturn("sum(linesAdded)");
		addReturn("sum(linesRemoved)");
		addReturn("count(isDeleted)");
		String link = getString();
		return getURL(link);
	}
	
	private void getAuthorsState(String project){
		inProject(project);
		onlyJava();
		addReturn("path");
		addReturn("author");
		addReturn("isDeleted");
	}
	
	private URL getURL(String link){
		URL queryUrl = null;
		try {
			queryUrl = new URL(link);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return queryUrl;
	}
	
	/**
	 * 
	 * Top level adds
	 * 
	 */
	private void addAuthorFilter(Set<String> setOfAuthorNames){
		String stringOfSet = setOfAuthorNames.toString();
		stringOfSet = stringOfSet.substring(1, stringOfSet.length()-1);
		addClause("author in ( " + stringOfSet + " ) ");
	}
	
	
	private void inProject(String project) {
		addPath("/" + project + "/trunk/src/main/**");
	}

	private void onlyJava() {
		addPath("*.java");
	}
	
	private void addPath(String path) {
		addClause(" path like " + path);
	}
	
	/**
	 * 
	 * Low level adds
	 * 
	 */
	private void addClause(String clause){
		clauses += " and " + clause;
	}
	
	private void addReturn(String returnElement){
		returns += ", " + returnElement;
	}
	
	/**
	 * 
	 * returns
	 * 
	 */
	private String getClauses() {
		return clauses;
	}

	private String getReturns() {
		return " return " + returns.substring(1);
	}

	private String getQuery() {
		return "select revisions " + dateRange + getClauses() + " group by file "+ getReturns();//cannot always group by file
	}
	
	private String getDateRange(ConvertDate startDate, ConvertDate endDate) {
		return " where date in [" + startDate.getSonarFormat().substring(0, 10) + "T07:00:00," + endDate.getSonarFormat().substring(0, 10)
				+ "T07:00:00] ";
	}
	

	private String getString() {
		String home = "http://fisheye.cobalt.com/rest-service-fe/search-v1/queryAsRows/";
		String link = home + repository + ".json?query=" + getQuery();
		return link.replaceAll("\\s+", "%20");
	}
}