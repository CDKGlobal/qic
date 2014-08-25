package com.testing123.dataObjects;

import java.util.Date;

import org.joda.time.DateTime;

import com.testing123.vaadin.RegexUtil;

public class ConvertDate implements Comparable<ConvertDate> {
	private String shortFormat;
	private int year;
	private int month;
	private int day;
	
	public ConvertDate(){
		this(new DateTime());
	}
	
	public ConvertDate(String format) {
		String[] date = format.split("-");
		this.year = Integer.parseInt(date[0]);
		this.month = Integer.parseInt(date[1]);
		this.day = Integer.parseInt(date[2]);
		this.shortFormat = String.format("%04d-%02d-%02d", year, month, day);
		checkRep();
	}
	
	public ConvertDate(Date d) {
		this(new DateTime(d));
	}
	
	public ConvertDate(DateTime date){
		this.year = date.getYear();
		this.month = date.getMonthOfYear();
		this.day = date.getDayOfMonth();
		this.shortFormat = String.format("%04d-%02d-%02d", year, month, day);
		checkRep();
	}
	
	public ConvertDate(int year, int month, int day) {
		this(year + "-" + month + "-" + day);
	}
	
	public int getYear() {
		return year;
	}

	public int getMonth() {
		return month;
	}

	public int getDay() {
		return day;
	}

	public DateTime getDateTime() {
		return new DateTime(year, month, day, 0, 0);
	}
	
	public String getDBFormat() {
		return shortFormat;
	}
	
	public String getDBFormatPlusOne(){
		return String.format("%04d-%02d-%02d", year, month, day+1);
	}
	
	private void checkRep() {
		if (!RegexUtil.isCorrectDateFormat(shortFormat)){
			throw new IllegalStateException("Incorrect Date Format");
		}
	}
	
	public String toString() {
		return String.format("%02d/%02d/%04d", month, day, year);
	}

	/**
	 * HashCode override required for the Vaadin UI
	 * 
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((shortFormat == null) ? 0 : shortFormat
						.hashCode());
		result = prime * result;
		return result;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof ConvertDate)) {
			return false;
		}
		ConvertDate other = (ConvertDate) o;
		return toString().equals(other.toString());
	}

	@Override
	public int compareTo(ConvertDate o) {
		if (year != o.year) {
			return ((Integer) year).compareTo((Integer) o.year);
		} else if (month != o.month) {
			return ((Integer) month).compareTo((Integer) o.month);
		} else {
			return ((Integer) day).compareTo((Integer) o.day);
		}
	}
}
