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
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((directory == null) ? 0 : directory.hashCode());
		result = prime * result + ((repository == null) ? 0 : repository.hashCode());
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
		RepoAndDirData other = (RepoAndDirData) obj;
		if (directory == null) {
			if (other.directory != null)
				return false;
		} else if (!directory.equals(other.directory))
			return false;
		if (repository == null) {
			if (other.repository != null)
				return false;
		} else if (!repository.equals(other.repository))
			return false;
		return true;
	}
}
