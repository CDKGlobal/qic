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
		this.grain = "Files";
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

	public boolean getIsDelta() {
		return isDelta;
	}
	
	public void setIsDelta(boolean isDelta) {
		this.isDelta = isDelta;
	}

	public String getGrain() {
		return grain;
	}

	public void setGrain(String grain) {
		this.grain = grain;
	}
	
	public void verifyState() {
		System.out.println("CURRENT STATE:");
		System.out.println(start.toString());
		System.out.println(end.toString());
		System.out.println(isDelta);
		System.out.println(grain);
		System.out.println();
	}
	
	public enum Axis {
		COMPLEXITY, LINESOFCODE
	}
	
	public enum Grain {
		PROJECT, DIRECTORY, FILE
	}
}
