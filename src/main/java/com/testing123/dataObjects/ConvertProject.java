package com.testing123.dataObjects;

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ConvertProject))
			return false;
		ConvertProject other = (ConvertProject) obj;
		if (id != other.id)
			return false;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		return true;
	}
}