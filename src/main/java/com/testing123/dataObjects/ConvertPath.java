package com.testing123.dataObjects;

import java.util.Arrays;
import java.util.List;

public class ConvertPath {
	
	
	final String originalInput;
	final List<String> path;
	final boolean originallySonar;
	
	public ConvertPath(String inputPath){
		this.originalInput = inputPath.trim();
		String[] split = originalInput.split(":");
		if (split.length>2){
			originallySonar=true;
			this.path = Arrays.asList(split[2].split("[/\\.]"));
		}else{
			originallySonar=false;
			this.path = Arrays.asList(inputPath.split("[/\\.]"));
		}
	}
	
	public String getOriginalPath(){
		return originalInput;
	}
	
	public String getSonarPath(){
		if(originallySonar){
			return originalInput;
		}
		StringBuffer buff = getPath('.');
		int length = buff.length();
		return getPath('.').substring(0, length-5).toString();
	}
	
	public String getFisheyePath(){
		if(!originallySonar){
			return originalInput;
		}
		StringBuffer buff = getPath('/');
		int lastSlashIndex = buff.lastIndexOf("/");
		int lastJavaIndex = buff.lastIndexOf("java");
		if(lastJavaIndex == lastSlashIndex + 1){
			buff.replace(lastSlashIndex, lastSlashIndex + 1, ".");
		}else{
			buff.append(".java");
		}
		return buff.toString();
	}
	
	private StringBuffer getPath(char c){
		StringBuffer buff = new StringBuffer();
		for (String s: path){
			buff.append(s);
			buff.append(c);
		}
		buff.deleteCharAt(buff.length()-1);
		return buff;
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

//		System.out.println(path);
//		System.out.println(other.path);
		if(path.containsAll(other.path) || other.path.containsAll(path)){
			return true;
		} else if (!path.equals(other.path))
			return false;
		return true;
	}


	
	
	
}
