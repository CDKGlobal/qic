package com.testing123.ui;

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
	public static String getString(Axis xaxis, Axis yaxis){
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
	        "axisLabel: \'" + yaxis.toString() + "\'," + 
	        "position: 'left'" + 
	    "}, " +
	    "xaxis : {" +
	        //"show : false," + 
	        //"axisLabel : \'" + xaxis.toString() + "\'," +
	        "autoscaleMargin: .02" +
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
}
