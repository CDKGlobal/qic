package com.testing123.vaadin;

import java.util.Map;

public class Query implements Queryable {

	@Override
	public Map<String, Double> getChurn(ConvertDate startDate, ConvertDate endDate) {
		return new QueryFisheye().getChurnData(startDate, endDate);
	}

	@Override
	public Map<String, Double> getComplexity(ConvertDate date) {
		return new QueryDatabase().getInstantaneousValue(state)
	}

}
