package com.testing123.vaadin;

import java.util.List;
import java.util.Set;

import com.testing123.controller.UIState;

public interface Retrievable {
	
	/**
	 * 
	 * 
	 * @param state the current data and state required for the UI
	 * @return a set of DataPoints that represents the data required to plot the graph
	 */
	public Set<DataPoint> getData(UIState state, List<String> projects, List<String> authors);
	
	
}
