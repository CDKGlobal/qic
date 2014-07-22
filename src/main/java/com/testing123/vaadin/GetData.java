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
	private Queryable query;

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
		Map<String, DataPoint> xMap;
		Map<String, DataPoint> yMap;
		
		Axis xAxis = state.getX();
		if (xAxis.equals(Axis.DELTA_COMPLEXITY)) {
			xMap = query.getDeltaComplexity(startDate, endDate);
			yMap = query.getComplexity(endDate);

		} else if (xAxis.equals(Axis.DELTA_LINESOFCODE)) {
			xMap = query.getChurn(startDate, endDate);
			yMap = query.getComplexity(endDate);

		} else if (xAxis.equals(Axis.LINESOFCODE)) {
			xMap = query.getNCLOC(startDate);
			yMap = query.getComplexity(endDate);

		} else {
			return new HashSet<DataPoint>();
		}
		
		return aggregator(xMap, yMap);
	}
	
	/**
	 * Takes the maps for the x and y coordinates and puts them in a set
	 * @param xMap
	 * @param yMap
	 * @return
	 */
	private Set<DataPoint> aggregator(Map<String, DataPoint> xMap, Map<String, DataPoint> yMap){
		Set<DataPoint> dataSet = new HashSet<DataPoint>(xMap.values());
		//Iterator<Entry<String, Double>> it = xMap.entrySet().iterator();
		//while (it.hasNext()) {
		for (Map.Entry<String, DataPoint> xValues : xMap.entrySet()) {
			String pathName = xValues.getKey();
			if (yMap.containsKey(pathName)) {
				DataPoint current = xValues.getValue();
				current.setYValue(yMap.get(pathName).getXValue());
				dataSet.add(current);
			}
		}
		return dataSet;
	}
	
	/**
	private Set<DataPoint> useTolerances(Set<DataPoint> dataSet){
		
		Iterator<DataPoint> iter = s.iterator();
		while (iter.hasNext()){
			DataPoint point = iter.next();
			if(point.getLineOfCode()<xTolerance || point.getComplexity() < yTolerance){
				iter.remove();
			}
		}**/
	

}
