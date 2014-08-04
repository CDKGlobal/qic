package com.testing123.vaadin;

import java.util.Set;

public class CacheTag {
	public ConvertDate startDate;
	public ConvertDate endDate;
	public Set<ConvertProject> projects;
	
	public CacheTag(ConvertDate startDate, ConvertDate endDate, Set<ConvertProject> projects) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.projects = projects;
	}
	
	public boolean equals(CacheTag o) {
		if (startDate.equals(o.startDate) && endDate.equals(o.endDate) && projects.equals(o.projects)) {
			return true;
		}
		return false;
	}
}