package com.testing123.vaadin;

public class ConvertProject {
	private String name;
	private String key;

	public ConvertProject(String name, String key) {
		this.name = name;
		this.key = key;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	public String getName() {
		return name;
	}

	public String getKey() {
		return key;
	}
	
	public String getRepository(){
		return name.split("/")[0];
	}
}