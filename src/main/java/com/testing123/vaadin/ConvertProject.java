package com.testing123.vaadin;

public class ConvertProject {
	private String name;
	private String key;
	private int id;
	private String path;

	public ConvertProject(String name, String key, int id, String path) {
		this.name = name;
		this.key = key;
		this.id = id;
		this.path = path;
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
	
	public int getID() {
		return id;
	}
	
	public String getPath(){
		return path;
	}
}