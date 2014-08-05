package com.testing123.vaadin;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class ConvertDate implements Comparable<ConvertDate> {
	//private Calendar currentDate;
	//private String adjustedSonarFormat;
	private String shortFormat;
	private int year;
	private int month;
	private int day;
	
	public ConvertDate(String format) {
		String[] date = format.split("-");
		this.shortFormat = date[0] + "-" + date[1] + "-" + date[2];
		//checkRep();
	}
	
	/**
	 * Returns the adjusted string representation of this date from sonar
	 * 
	 * The adjustments made were changing all occurences of ':' to '-'
	 * 
	 * @return
	 */
//	public String getSonarFormat() {
//		//checkRep();
//		return adjustedSonarFormat;
//	}
	
	public String getDBFormat() {
		return shortFormat;
	}
	
	/**
	 * Returns the string representation of this date from the database
	 * 
	 * @return
	 */
//	private String getDBFormat() {
//		return adjustedSonarFormat.replace("-", "_");
//	}
	
	private void checkRep() {
		String[] date = shortFormat.split("-");
		if (date[0] == null || date[1] == null || date[2] == null) {
			throw new IllegalStateException("Date format corrupted");
		}
		if (month < 1 || month > 12) {
			throw new IllegalStateException("Date format corrupted");
		}
		if (day < 1 || day > 31) {
			throw new IllegalStateException("Date format corrupted");
		}
	}
	
	public String toString() {
		//checkRep();
		String[] splitted = shortFormat.split("-");
		return splitted[1] + "/" + splitted[2] + "/" + splitted[0];
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
		return toString().compareTo(o.toString());
	}
	
//	public static String convert(Calendar currentDate) {
//		String timeZoneWithoutGMT = String.format("%1$tZ", currentDate).substring(3);
//		String timeZone = "" + timeZoneWithoutGMT.substring(0,3) + timeZoneWithoutGMT.substring(4,6);
//		return String.format("%1$tY-%1$tm-%1$teT%1$tH-%1$tM-%1$tS%2$s ", currentDate, timeZone);
//	}
}
