package com.testing123.vaadin;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.testing123.controller.UIState;
import com.testing123.controller.UIState.Axis;

public class GetData implements Retrievable {

	private UIState state;
	private Queryable query;

	public GetData(Queryable queryInterface) {
		this.query = queryInterface;
	}
	
	/**
	 * 
	 * @return Set of DataPoint with the correct X and Y coordinates and details
	 */
	@Override
	public Set<DataPoint> getData(UIState state) {
		this.state = state;
		ConvertDate startDate = state.getStart();
		ConvertDate endDate = state.getEnd();
		Map<String, Double> xMap;
		Map<String, Double> yMap;
		boolean authorsRequired = true;
		
		Axis xAxis = state.getX();
		if (xAxis.equals(Axis.DELTA_COMPLEXITY)) {
			xMap = query.getDeltaComplexity(startDate, endDate);
			yMap = query.getComplexity(endDate);

		} else if (xAxis.equals(Axis.DELTA_LINESOFCODE)) {
			xMap = query.getChurn(startDate, endDate, "Platform");
			yMap = query.getComplexity(endDate);

		} else if (xAxis.equals(Axis.LINESOFCODE)) {
			xMap = query.getNCLOC(startDate);
			yMap = query.getComplexity(startDate);
			authorsRequired = false;
		} else {
			return new HashSet<DataPoint>();
		}
		Set<DataPoint> dataSet = aggregator(xMap, yMap);
		if (authorsRequired) 
			dataSet = addAuthorsToDataSet(dataSet);
		
		return dataSet;
	}
	
	/**
	 * Takes the maps for the x and y coordinates and puts them in a set
	 * @param xMap
	 * @param yMap
	 * @return
	 */
	private Set<DataPoint> aggregator(Map<String, Double> xMap, Map<String, Double> yMap){
		Set<DataPoint> dataSet = new HashSet<DataPoint>();
		for (Map.Entry<String, Double> xValues : xMap.entrySet()) {
			String pathName = xValues.getKey();
			if (yMap.containsKey(pathName)) {
				DataPoint current = new DataPoint(xValues.getKey(), xValues.getValue(), yMap.get(pathName));
				dataSet.add(current);
			}
		}
		return dataSet;
	}


	private Set<DataPoint> addAuthorsToDataSet(Set<DataPoint> dataSet) {
		Map<String, List<String>> authors = query.getAuthors(state.getStart(), state.getEnd(), state.getAuthorsFilter(), "Platform");
		Set<DataPoint> filteredDataSet = new HashSet<DataPoint>();
		for(DataPoint point : dataSet){
			String pathName = point.getKey();
			if(authors.containsKey(pathName)){
				point.setAuthors(authors.get(pathName));
				filteredDataSet.add(point);
			}
		}
		return filteredDataSet;
	}
}
