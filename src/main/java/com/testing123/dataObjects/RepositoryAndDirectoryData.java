package com.testing123.dataObjects;

public class RepositoryAndDirectoryData {

	private String repository;
	private String directory;
	
	public RepositoryAndDirectoryData(String repository, String directory) {
		super();
		this.repository = repository;
		this.directory = directory;
	}
	
	@Override
	public String toString() {
		return "[\trepository=" + repository + "\t directory=" + directory + "\t]";
	}

	public String getRepository() {
		return repository;
	}
	public String getDirectory() {
		return directory;
	}
	public void setRepository(String repository) {
		this.repository = repository;
	}
	public void setDirectory(String directory) {
		this.directory = directory;
	}
}
