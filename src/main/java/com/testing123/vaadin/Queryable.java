package com.testing123.vaadin;

import java.util.Map;

public interface Queryable {
	
	public Map<String, Double> getChurn(ConvertDate startDate, ConvertDate endDate);
	
	public Map<String, Double> getComplexity(ConvertDate date);
	
	public Map<String, Double> getNCLOC(ConvertDate startDate, ConvertDate endDate);
	
	public Map<String, Double> getDeltaComplexity(ConvertDate startDate, ConvertDate endDate);

}
