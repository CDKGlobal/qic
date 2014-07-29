package com.testing123.ui;

import com.testing123.controller.UIState;
import com.testing123.controller.UIState.Axis;

/**
 * 
 * @author blumbeb
 *
 *This class holds the settings or the Flot graph including the scolor scheme and margins and such
 *It is also where you are able to set the Axis
 */
public class FlotOptions {
	
	/**
	 * Creates a string for flot to read as JSON
	 * @return String representation of your Flot Options
	 */
	public static String getString(UIState state){
	String options =
		"{" + 
		"series : {" + 
			"points: { " +
				"show: true, " + 
				"fill: true, " + 
				"fillColor: \"" + Preferences.FILL_COLOR + "\"" +
			"} " + 
		"}, " +		
		"colors : [\"" + Preferences.GRPAH_COLOR + "\", \"" + Preferences.GRPAH_COLOR + "\"]," +
		"yaxis : {" + 
	        "show : true," + 
	        "axisLabel: \'" + state.getY().toString() + "\'," + 
	        "position: 'left'" + 
	        //"zoomRange: [-10, 10000]," +
			//"panRange: [-10, 10000]" +
	    "}, " +
	    "xaxis : {" +
	        //"show : false," + 
	        //"axisLabel : \'" + xaxis.toString() + "\'," +
	        "autoscaleMargin: .02" +
	        //"zoomRange: " + getXRange(state) + "," +
			//"panRange: " + getXRange(state) +
	    "}," +
		"grid: {" 
				+ "hoverable: true,"
				+ "clickable: true" +
		"}," + 
		"zoom: { interactive: true }," +
		"pan: { interactive: true }" +
		//"selection: { mode: \"xy\" }" +
		"}";
		return options;
	}
	
	private static String getXRange(UIState state) {
		if (state.getX() == Axis.LINESOFCODE) {
			return "[-10, 60000]";
		} else if (state.getX() == Axis.DELTA_COMPLEXITY) {
			return "[-200, 200]";
		} else if (state.getX() == Axis.DELTA_LINESOFCODE) {
			return "[-10, 4000]";
		} else {
			return "[-100000, 100000]";
		}
	}
}
