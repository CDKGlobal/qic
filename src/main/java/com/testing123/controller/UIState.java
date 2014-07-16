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

	public String getX() {
		return x;
	}

	public void setX(String x) {
		this.x = x;
	}

	public String getY() {
		return y;
	}

	public void setY(String y) {
		this.y = y;
	}
	
	public String getGrain() {
		return grain;
	}

	public void setGrain(String grain) {
		this.grain = grain;
	}
	
	public void verifyState() {
		System.out.println("CURRENT STATE:");
		System.out.println("Start Date: " + start.toString());
		System.out.println("End Date: " + end.toString());
		System.out.println("Displaying Change: " + isDelta);
		System.out.println("Granularity: " + grain);
		System.out.println();
	}
	
	public enum Axis {
		COMPLEXITY, LINESOFCODE
	}
	
	public enum Grain {
		PROJECT, DIRECTORY, FILE
	}
}