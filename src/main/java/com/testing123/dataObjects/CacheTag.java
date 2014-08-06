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
	
	public boolean equals(CacheTag o) {
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
}