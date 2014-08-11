package com.testing123.dataObjects;

import java.util.Arrays;
import java.util.List;

public class ConvertPath {
	
	
	String originalInput;
	List<String> path;
	String artifactID;
	String project;
	
	public ConvertPath(String inputPath){
		this.originalInput = inputPath;
		String[] split = inputPath.trim().split(":");
		if (split.length>2){
			this.artifactID = split[0];
			this.project = split[1];
			this.path = Arrays.asList(split[2].split("[/\\.]"));
		}else{
			this.path = Arrays.asList(inputPath.split("[/\\.]"));
		}
	}
	
	public String getOriginalPath(){
		return originalInput;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((path == null) ? 0 : path.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConvertPath other = (ConvertPath) obj;
		if (path == null || other.path == null)
				return false;
		System.out.println(path);
		System.out.println(other.path);
		if(path.containsAll(other.path) || other.path.containsAll(path)){
			return true;
		} else if (!path.equals(other.path))
			return false;
		return true;
	}


	
	
	
}
