package com.testing123.vaadin;

public class RegexUtil {
	
	public static boolean isRevisionData(String line) {
		if(line==null){
			return false;
		}
		return line.matches(javaFilePath() + "," + author() + "," + integer() + "," + integer());	
	}
	
	private static String javaFilePath(){
		return ("\"[-\\.0-9a-zA-Z\\/]*[.java]\"");
	}
	
	private static String author(){
		return "\"[a-zA-Z]*\"";
	}
	
	private static String integer(){
		return "[0-9]*";
	}
	
	
}
