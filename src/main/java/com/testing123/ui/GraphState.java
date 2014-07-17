package com.testing123.ui;

import org.json.JSONArray;
import org.json.JSONObject;

import com.vaadin.shared.ui.JavaScriptComponentState;

/**
 * GraphState is a class store the current state of Graph.
 * 
 * @author chenc
 */
@SuppressWarnings("serial")
public class GraphState extends JavaScriptComponentState {
	/** the JSONArray of data points to be plotted **/
	private JSONArray data;
	/** the JSONObject of settings for the graph **/
	private JSONObject options;

	/**
	 * Sets the options of for the graph.
	 * 
	 * @param data
	 */
	public void setData(JSONArray data) {
		this.data = data;
	}

	/**
	 * Sets the options of for the graph.
	 * 
	 * @param options
	 *            the JSONObject that has a format that the JavaScript flot
	 *            library can read
	 */
	public void setOptions(JSONObject options) {
		this.options = options;
	}

	/**
	 * Returns the current data stored for the graph
	 * 
	 * @return data
	 */
	public JSONArray getData() {
		return data;
	}

	/**
	 * Returns the current options stored for the graph
	 * 
	 * @return options
	 */
	public JSONObject getOptions() {
		return options;
	}
}
