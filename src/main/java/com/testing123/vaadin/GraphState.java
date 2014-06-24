package com.testing123.vaadin;

import org.json.JSONArray;
import org.json.JSONObject;

import com.vaadin.shared.ui.JavaScriptComponentState;

public class GraphState extends JavaScriptComponentState{
	private JSONArray data;
	private JSONObject options;

	public void setOptions(JSONObject options) {
		this.options = options;
	}
	
    public JSONArray getData() {
        return data;
    }
    
    public JSONObject getOptions() {
    	return options;
    }
 
    public void setData(JSONArray data) {
        this.data = data;
    }
}
