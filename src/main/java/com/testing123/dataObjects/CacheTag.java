package com.testing123.dataObjects;

import java.util.Set;

public class CacheTag {
	private ConvertDate startDate;
	private ConvertDate endDate;
	private Set<ConvertProject> projects;
	private boolean singleMetric;
	
	public CacheTag(ConvertDate startDate, ConvertDate endDate, Set<ConvertProject> projects, boolean singleMetric) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.projects = projects;
		this.singleMetric = singleMetric;
	}

	public ConvertDate getStartDate() {
		return startDate;
	}
	
	public ConvertDate getEndDate() {
		return endDate;
	}
	
	public Set<ConvertProject> getProjects() {
		return projects;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result + ((projects == null) ? 0 : projects.hashCode());
		result = prime * result + (singleMetric ? 1231 : 1237);
		result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
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
		CacheTag other = (CacheTag) obj;
		if (endDate == null) {
			if (other.endDate != null)
				return false;
		} else if (!endDate.equals(other.endDate))
			return false;
		if (projects == null) {
			if (other.projects != null)
				return false;
		} else if (!projects.equals(other.projects))
			return false;
		if (singleMetric != other.singleMetric)
			return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
			return false;
		return true;
	}
	
	
}