package com.testing123.vaadin;

import java.util.Map;

public interface Queryable {
	
	public Map<String, Double> getInstantaneousValue(ConvertDate date, String qualifier, String scope, String path, String metric);

}
