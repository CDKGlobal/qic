package com.testing123.dataObjects;

public class ConvertDate implements Comparable<ConvertDate> {
	private String shortFormat;
	private int year;
	private int month;
	private int day;
	
	public ConvertDate(String format) {
		String[] date = format.split("-");
		this.year = Integer.parseInt(date[0]);
		this.month = Integer.parseInt(date[1]);
		this.day = Integer.parseInt(date[2]);
		this.shortFormat = String.format("%04d-%02d-%02d", year, month, day);
		//this.shortFormat = "" + year + "-" + month + "-" + day;
		checkRep();
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

	public String getDBFormat() {
		return shortFormat;
	}
	
	private void checkRep() {
		String[] date = shortFormat.split("-");
		if (date.length != 3) {
			throw new IllegalStateException("Date format corrupted");
		}
		if (date[0] == null || date[1] == null || date[2] == null) {
			throw new IllegalStateException("Date format corrupted");
		}
		if (month < 1 || month > 12) {
			throw new IllegalStateException("Date format corrupted: month - " + month);
		}
		if (day < 1 || day > 31) {
			throw new IllegalStateException("Date format corrupted: day - " + day);
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
