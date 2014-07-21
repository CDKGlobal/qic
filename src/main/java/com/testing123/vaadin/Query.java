package com.testing123.vaadin;

import java.util.Map;

public class Query implements Queryable {

	@Override
	public Map<String, Double> getChurn(ConvertDate startDate, ConvertDate endDate) {
		return new QueryFisheye().getChurnData(startDate, endDate);
	}

	@Override
	public Map<String, Double> getComplexity(ConvertDate date) {
		return new QueryDatabase().getComplexity(date);
	}

	@Override
	public Map<String, Double> getNCLOC(ConvertDate startDate, ConvertDate endDate) {
		//return new QueryDatabase().getNCLOC(startDate, endDate);
		return null;
	}

	@Override
	public Map<String, Double> getDeltaComplexity(ConvertDate startDate, ConvertDate endDate) {
		//return new QueryDatabase().getDeltaComplexity(startDate, endDate);
		return null;
	}

}
