package com.testing123.ui;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.vaadin.annotations.JavaScript;
import com.vaadin.ui.AbstractJavaScriptComponent;

/**
 * Graph represents a Javascript scatterplot, its options, and data.  
 * 
 * @author chenc
 */
@SuppressWarnings("serial")
@JavaScript({"jquery.js","jquery.flot.js", "jquery.flot.axislabels.js", "flot_connector.js"})
public class Graph extends AbstractJavaScriptComponent {
	
	/**
	 * Sets properties of the graph (eg. show lines, axis labels, etc)
	 * 
	 * @requires options != null, and source is a well formatted JSON data source
	 * @param options the JSONObject of options that the graph will have
	 */
	public void setOptions(String options) {
		JSONObject root;
		try {
			root = new JSONObject(options);
			getState().setOptions(root);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Sets the data that will be plotted
	 * 
	 * @requires source != null, and source is a well formatted JSON data source
	 * @param source a string of data that is in the format of a JSONArray
	 */
	public void setData(String source) {
		JSONArray data;
		try {
			data = new JSONArray(source);
			getState().setData(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns graph's current instance's state
	 * 
	 * @return the current state of the graph
	 */
	@Override
	protected GraphState getState() {
		return (GraphState) super.getState();
	}
}
