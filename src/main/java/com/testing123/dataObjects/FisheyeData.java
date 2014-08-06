package com.testing123.dataObjects;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;


public class FisheyeData {

	public FisheyeData() {
		this.headings = new ArrayList<String>();
		this.row = new ArrayList<ItemData>();
	}

	private List<String> headings;
	
    @JsonProperty("row")
	private List<ItemData> row;
    
	public List<ItemData> getRow() {
		return row;
	}

	public void setRow(List<ItemData> row) {
		this.row = row;
	}

	public List<String> getHeadings() {
		return headings;
	}

	public void setHeadings(List<String> headings) {
		this.headings = headings;
	}
}

