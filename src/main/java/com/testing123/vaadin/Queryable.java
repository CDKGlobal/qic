package com.testing123.vaadin;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.testing123.controller.UIState.Axis;
import com.testing123.controller.UIState.YAxis;

public interface Queryable {
	
	public Map<String, Double> getChurn(String repository, ConvertDate startDate, ConvertDate endDate, String project);
	
	public Map<String, Double> getComplexity(ConvertDate date, Set<String> authors, Set<ConvertProject> projects);
	
	// optimized complete
	public Set<DataPoint> getNCLOC(ConvertDate date, Set<ConvertProject> projects);
	
	//working
	public Map<String, Double> getSingleMetric(Axis axis, ConvertDate date, Set<ConvertProject> projects);
	
	public Set<QueryData> getDateRangeMetrics(ConvertDate startDate, ConvertDate endDate, Set<ConvertProject> projects);
	
	public Map<String, Double> getDeltaComplexity(ConvertDate startDate, ConvertDate endDate, Set<String> authors, Set<ConvertProject> projects);
	
	public Map<String, List<String>> getAuthors(String repository, ConvertDate startDate, ConvertDate endDate, Set<String> setOAuthorNames, String project);
}
