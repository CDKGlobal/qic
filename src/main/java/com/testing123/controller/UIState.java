package com.testing123.controller;

import com.testing123.vaadin.ConvertDate;

public class UIState {
	private ConvertDate start;
	private ConvertDate end;
	private boolean isDelta;
	private String x;
	private String y;
	private String grain;
	
	public UIState() {
		this(new ConvertDate("2014-07-07T06-09-17-0700"), new ConvertDate("2014-07-10T06-07-56-0700"));
	}
	
	public UIState(ConvertDate start, ConvertDate end) {
		this.start = start;
		this.end = end;
		this.isDelta = true;
		this.grain = "Project";
	}

	public ConvertDate getStart() {
		return start;
	}

	public void setStart(ConvertDate start) {
		this.start = start;
	}

	public ConvertDate getEnd() {
		return end;
	}

	public void setEnd(ConvertDate end) {
		this.end = end;
	}

	public String getGrain() {
		return grain;
	}

	public void setGrain(String grain) {
		this.grain = grain;
	}
	
	public enum Axis {
		COMPLEXITY, LINESOFCODE
	}
	
	public enum Grain {
		PROJECT, DIRECTORY, FILE
	}
}
