package com.testing123.dataObjects;

import java.util.List;

public class QueryData {
	private String key;
	private String name;
	private double ncloc;
	private double churn;
	private double complexity;
	private double deltaComplexity;
	private double issues;
	private double deltaIssues;
	private double coverage;
	private double deltaCoverage;
	private List<String> authors;
	
	public String getKey() {
		return key;
	}
	
	public String getName() {
		return name;
	}
	
	public List<String> getAuthors() {
		return this.authors;
	}
	
	public double getMetric(String metric) {
		if (metric.equals("ncloc")) {
			return ncloc;
		} else if (metric.equals("churn")) {
			return churn;
		} else if (metric.equals("complexity")) {
			return complexity;
		} else if (metric.equals("delta_complexity")) {
			return deltaComplexity;
		} else if (metric.equals("issues")) {
			return issues;
		} else if (metric.equals("deltaIssues")) {
			return deltaIssues;
		} else if (metric.equals("coverage")) {
			return coverage;
		} else if (metric.equals("deltaCoverage")) {
			return deltaCoverage;
		} else {
			return 0.0;
		}
	}
	
	public void setMetric(String metric, double val) {
		if (metric.equals("ncloc")) {
			this.ncloc = val;
		} else if (metric.equals("churn")) {
			this.churn = val;
		} else if (metric.equals("complexity")) {
			this.complexity = val;
		} else if (metric.equals("delta_complexity")) {
			this.deltaComplexity = val;
		} else if (metric.equals("issues")) {
			this.issues = val;
		} else if (metric.equals("deltaIssues")) {
			this.deltaIssues = val;
		} else if (metric.equals("coverage")) {
			this.coverage = val;
		} else if (metric.equals("deltaCoverage")) {
			this.deltaCoverage = val;
		} else {
			return;
		}
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNcloc(double ncloc) {
		this.ncloc = ncloc;
	}

	public void setChurn(double churn) {
		this.churn = churn;
	}

	public void setComplexity(double complexity) {
		this.complexity = complexity;
	}

	public void setDeltaComplexity(double deltaComplexity) {
		this.deltaComplexity = deltaComplexity;
	}

	public void setIssues(double issues) {
		this.issues = issues;
	}

	public void setDeltaIssues(double deltaIssues) {
		this.deltaIssues = deltaIssues;
	}

	public void setCoverage(double coverage) {
		this.coverage = coverage;
	}

	public void setDeltaCoverage(double deltaCoverage) {
		this.deltaCoverage = deltaCoverage;
	}
	
	public void setAuthors(List<String> authors) {
		this.authors = authors;
	}
	
	
}