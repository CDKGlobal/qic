package com.testing123.vaadin;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.testing123.controller.UIState;
import com.testing123.controller.UIState.Axis;

public class GetData implements Retrievable {

	private UIState state;
	Queryable query;

	public GetData(Queryable queryInterface) {
		this.query = queryInterface;
	}
	
	/**
	 * 
	 * @return Set of DataPoint with the correct X and Y coordinates and details
	 */
	public Set<DataPoint> getData(UIState state) {
		this.state = state;
		ConvertDate startDate = state.getStart();
		ConvertDate endDate = state.getEnd();
		Map<String, Double> xMap;
		Map<String, Double> yMap;
		Set<DataPoint> dataSet = new HashSet<DataPoint>();
		
		Axis xAxis = state.getX();
		if (xAxis.equals(Axis.DELTA_COMPLEXITY)) {
			xMap = query.getDeltaComplexity(startDate, endDate);
			yMap = query.getComplexity(endDate);

		} else if (xAxis.equals(Axis.DELTA_LINESOFCODE)) {
			xMap = query.getChurn(startDate, endDate);
			yMap = query.getComplexity(startDate);

		} else if (xAxis.equals(Axis.LINESOFCODE)) {
			xMap = query.getNCLOC(startDate, endDate);
			yMap = query.getComplexity(startDate);

		} else {
			return new HashSet<DataPoint>();
		}
		
		
		Iterator<Entry<String, Double>> it = xMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, Double> xValues = it.next();
			String pathName = xValues.getKey();
			if (yMap.containsKey(pathName)) {
				dataSet.add(new DataPoint(pathName, xValues.getValue(), yMap.get(pathName)));
			}
			it.remove();
		}
		return dataSet;
	}


}
