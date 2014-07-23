package com.testing123.vaadin;

import java.util.List;
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
	public Map<String, Double> getNCLOC(ConvertDate date) {
		return new QueryDatabase().getNCLOC(date);
	}

	@Override
	public Map<String, Double> getDeltaComplexity(ConvertDate startDate, ConvertDate endDate) {
		return new QueryDatabase().getDeltaComplexity(startDate, endDate);
	}

	@Override
	public Map<String, List<String>> getAuthors(ConvertDate startDate, ConvertDate endDate) {
		return new QueryFisheye().getAuthorData(startDate, endDate);
	}

}
