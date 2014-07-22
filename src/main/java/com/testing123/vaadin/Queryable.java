package com.testing123.vaadin;

import java.util.Map;

public interface Queryable {
	
	public Map<String, DataPoint> getChurn(ConvertDate startDate, ConvertDate endDate);
	
	public Map<String, DataPoint> getComplexity(ConvertDate date);
	
	public Map<String, DataPoint> getNCLOC(ConvertDate date);
	
	public Map<String, DataPoint> getDeltaComplexity(ConvertDate startDate, ConvertDate endDate);

}
