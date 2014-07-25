package com.testing123.vaadin;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Query implements Queryable {

	@Override
	public Map<String, Double> getChurn(ConvertDate startDate, ConvertDate endDate) {
		URL url = new FisheyeQuery("Advertising.Perforce", startDate, endDate).getChurnURL();
		FisheyeData querriedData = new QueryFisheye().getJSONFromFisheye(url);
		return new QueryFisheye().getChurnData(querriedData);
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
	public Map<String, List<String>> getAuthors(ConvertDate startDate, ConvertDate endDate, Set<String> setOfAuthorNames) {
		URL url = new FisheyeQuery("Advertising.Perforce", startDate, endDate).getAuthorsURL(setOfAuthorNames);
		FisheyeData querriedData = new QueryFisheye().getJSONFromFisheye(url);
		return new QueryFisheye().getAuthorData(querriedData);
	}

}
