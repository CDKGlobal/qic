package com.testing123.vaadin;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface Queryable {
	
	public Map<String, Double> getChurn(String repository, ConvertDate startDate, ConvertDate endDate, String project);
	
	public Map<String, Double> getComplexity(ConvertDate date);
	
	public Map<String, Double> getNCLOC(ConvertDate date);
	
	public Map<String, Double> getDeltaComplexity(ConvertDate startDate, ConvertDate endDate);
	
	public Map<String, List<String>> getAuthors(String repository, ConvertDate startDate, ConvertDate endDate, Set<String> setOAuthorNames, String project);

}
