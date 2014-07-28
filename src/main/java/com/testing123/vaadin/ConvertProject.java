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
		String[] split = key.split(".");
		String repository = split[0];
		repository = repository.substring(0, 1).toUpperCase() + repository.substring(1);
		repository = repository + ".Perforce";
		return repository;
	}
}