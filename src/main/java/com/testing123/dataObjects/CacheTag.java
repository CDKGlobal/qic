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

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof CacheTag)) {
			return false;
		}
		CacheTag o = (CacheTag) other;
		if (startDate.equals(o.startDate) && endDate.equals(o.endDate) && projects.equals(o.projects) 
				&& singleMetric == o.singleMetric) {
			return true;
		}
		return false;
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

	
	public boolean isSingleMetric() {
		return singleMetric;
	}
	
	
}
