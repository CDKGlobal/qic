package com.testing123.vaadin;

import java.util.List;

public class QueryData {
	private String key;
	private String name;
//	private String scope;
//	private String qualifier;
//	private String date;
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
	
//	public double getMetric(MetricGetter getter) {
//		return getter.getValue(this);
//	}

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
	
//	public static class NCLOCMetricGetter implements MetricGetter {
//		
//		@Override
//		public double getValue(QueryData data) {
//			return data.ncloc;
//		}
//	}
//	
//	public static class ChurnMetricGetter implements MetricGetter {
//		
//		@Override
//		public double getValue(QueryData data) {
//			return data.churn;
//		}
//	}
//	
//	public static class ComplexityMetricGetter implements MetricGetter {
//		
//		@Override
//		public double getValue(QueryData data) {
//			return data.complexity;
//		}
//	}
//	
//	public static class DeltaComplexityMetricGetter implements MetricGetter {
//		
//		@Override
//		public double getValue(QueryData data) {
//			return data.coverage;
//		}
//	}
	
//	public static class CoverageMetricGetter implements MetricGetter {
//		
//		@Override
//		public double getValue(QueryData data) {
//			return data.coverage;
//		}
//	}
	
//	public interface MetricGetter {
//		double getValue(QueryData data);
//	}
}