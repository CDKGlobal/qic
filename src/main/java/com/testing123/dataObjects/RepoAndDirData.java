package com.testing123.dataObjects;

public class RepoAndDirData {

	String repository;
	String directory;

	public RepoAndDirData(String projectPath) {
		if (projectPath != null) {
			String trimmedProjectPath = projectPath.trim();
			repository = getRepositoryName(trimmedProjectPath);
			directory = getDirectoryName(trimmedProjectPath);
		}
	}

	public String getRepositoryName() {
		return repository;
	}

	public String getDirectoryName() {
		return directory;
	}

	private String getRepositoryName(String path) {
		String[] split = path.split("/");
		if (split.length > 1) {
			return split[1] + ".Perforce";
		}
		return "";
	}

	private String getDirectoryName(String path) {
		String[] split = path.split("/");
		if (split.length > 2) {
			int index = path.indexOf('/', 1);
			return path.substring(index + 1);
		}
		return "";
	}

}
