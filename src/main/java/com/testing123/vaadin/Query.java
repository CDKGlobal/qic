package com.testing123.vaadin;

import java.util.Map;

public class Query implements Queryable {

	@Override
	public Map<String, DataPoint> getChurn(ConvertDate startDate, ConvertDate endDate) {
		return new QueryFisheye().getChurnData(startDate, endDate);
	}

	@Override
	public Map<String, DataPoint> getComplexity(ConvertDate date) {
		return new QueryDatabase().getComplexity(date);
	}

	@Override
	public Map<String, DataPoint> getNCLOC(ConvertDate date) {
		return new QueryDatabase().getNCLOC(date);
	}

	@Override
	public Map<String, DataPoint> getDeltaComplexity(ConvertDate startDate, ConvertDate endDate) {
		return new QueryDatabase().getDeltaComplexity(startDate, endDate);
	}

}
