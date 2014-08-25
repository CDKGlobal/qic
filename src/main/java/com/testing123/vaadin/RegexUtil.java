package com.testing123.vaadin;

public class RegexUtil {
	
	public static boolean isCorrectDateFormat(String date){
		if(date==null){
			return false;
		}
		return date.matches("^[0-9]{4}-([0][1-9]|[1][0-2])-([0][1-9]|[1-22][0-9]|[3][0-1])$");
	}
	
	public static boolean isRevisionData(String line) {
		if(line==null){
			return false;
		}
		return line.matches(javaFilePath() + "," + author() + "," + integer() + "," + integer() + "," + booleanString());	
	}
	
	private static String booleanString() {
		return ("\"(true|false)\"");
	}

	private static String javaFilePath(){
		return ("\"[-\\.0-9a-zA-Z\\/]*(.java)\"");
	}
	
	private static String author(){
		return "\"[a-zA-Z]*\"";
	}
	
	private static String integer(){
		return "[-0-9]*";
	}
	
	
}
