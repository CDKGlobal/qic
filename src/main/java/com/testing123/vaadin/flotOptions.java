package com.testing123.vaadin;

public class flotOptions {
	private static String xaxis;
	private static String yaxis;
	

	
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
	
	public static void setXaxis(String xlable){
		xaxis = xlable;
	}
	
	public static void setYaxis(String ylable){
		yaxis = ylable;
	}
}
