package com.testing123.vaadin;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.vaadin.annotations.JavaScript;
import com.vaadin.ui.AbstractJavaScriptComponent;


@JavaScript({"jquery.js","jquery.flot.js", "jquery.flot.axislabels.js", "flot_connector.js"})
public class Graph extends AbstractJavaScriptComponent {
	
	/**
	 * Sets properties of the graph (eg. show lines, axis labels, etc)
	 * 
	 * @requires options != null
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
	 * @requires source != null
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
	 * {@inheritDoc}
	 */
	@Override
	protected GraphState getState() {
		return (GraphState) super.getState();
	}
}
