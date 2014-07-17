package com.testing123.vaadin;


import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.testing123.controller.UIState;

public class GetData implements Retrievable{
	
	private UIState state;
	
	@Override
	public Set<DataPoint> getData(UIState state) {
		this.state = state;
		return null;
	}

	public Set<DataPoint> getData() {
		Queryable mock = new MockQueryable();
		Map<String, Double> xMap = mock.getChurn(state.getStart(), state.getEnd());
		Map<String, Double> yMap = mock.getComplexity(state.getEnd());
		Set<DataPoint> dataSet = new HashSet<DataPoint>();
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
