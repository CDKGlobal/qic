package com.testing123.vaadin;
/**
 * 
 * @author blumbeb
 *
 *This class holds the settings or the Flot graph including the scolor scheme and margins and such
 *It is also where you are able to set the Axis
 */
public class FlotOptions {
	private static String xaxis = "Change in Lines of Code";
	private static String yaxis = "Cyclomatic Complexity";
	
	/**
	 * Creates a string for flot to read as JSON
	 * @return String representation of your Flot Options
	 */
	public static String getString(){
	String options =
	"{" + 
	"series : {" + 
		"points: { " +
			"show: true, " + 
			"fill: true, " + 
			"fillColor: \"#033F8D\"" +
		"} " + 
	"}, " +		
	"colors : [\"#033F8D\", \"#033F8D\"]," +
	"yaxis : {" + 
        "show : true," + 
        "axisLabel : \'" + yaxis + "\'," + 
        "position: 'left'" + 
    "}, " +
    "xaxis : {" +
        "show : true," + 
        "axisLabel : \'" + xaxis + "\'," +
        "autoscaleMargin : .02" +
    "},"
			+
		"grid: {" 
			+ "hoverable: true" +
		"}" + 
	"}"; 
	
	return options;
	}
	
	public static void setXaxis(String xlabel){
		xaxis = xlabel;
	}
	
	public static void setYaxis(String ylabel){
		yaxis = ylabel;
	}
}
