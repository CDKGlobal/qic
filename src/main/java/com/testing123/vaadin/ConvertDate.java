package com.testing123.vaadin;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class ConvertDate implements Comparable<ConvertDate> {
	private Calendar currentDate;
	private String adjustedSonarFormat;
	
	public ConvertDate(String sonarFormat) {
		this.adjustedSonarFormat = sonarFormat.replace(":", "-");
		if (!adjustedSonarFormat.matches("[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}-[0-9]{2}-[0-9]{2}-[0-9]{2}00")) {
			throw new IllegalArgumentException("Passed date into ConvertDate is not Sonar format");
		}
		int year = Integer.parseInt(sonarFormat.substring(0, 4));
		int month = Integer.parseInt(sonarFormat.substring(5, 7))-1;
		int day = Integer.parseInt(sonarFormat.substring(8, 10));
		int hour = Integer.parseInt(sonarFormat.substring(11, 13));
		int minute = Integer.parseInt(sonarFormat.substring(14, 16));
		int second = Integer.parseInt(sonarFormat.substring(17, 19));
		String timeZone = "GMT" + sonarFormat.substring(19);
		Calendar currentDate = new GregorianCalendar(year, month, day, hour, minute, second);
		currentDate.setTimeZone(TimeZone.getTimeZone(timeZone));
		this.currentDate = currentDate;
		checkRep();
	}
	
	/**
	 * Returns the adjusted string representation of this date from sonar
	 * 
	 * The adjustments made were changing all occurences of ':' to '-'
	 * 
	 * @return
	 */
	public String getSonarFormat() {
		checkRep();
		return adjustedSonarFormat;
	}
	
	/**
	 * Returns the string representation of this date from the database
	 * 
	 * @return
	 */
	public String getDBFormat() {
		return adjustedSonarFormat.replace("-", "_");
	}
	
	public boolean verify() {
		if (!adjustedSonarFormat.matches("[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}-[0-9]{2}-[0-9]{2}-[0-9]{2}00")) {
			return false;
		}
		if (adjustedSonarFormat.contains(":")) {
			return false;
		}
		return true;
	}
	
	private void checkRep() {
		verify();
		if (adjustedSonarFormat.contains(":")) {
			System.out.println(adjustedSonarFormat);
			throw new IllegalStateException("Sonar Format not valid");
		}
	}
	
	public String toString() {
		checkRep();
		return currentDate.getTime().toString();
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
				+ ((adjustedSonarFormat == null) ? 0 : adjustedSonarFormat
						.hashCode());
		result = prime * result
				+ ((currentDate == null) ? 0 : currentDate.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof ConvertDate)) {
			return false;
		}
		ConvertDate other = (ConvertDate) o;
		return getSonarFormat().equals(other.getSonarFormat()) && toString().equals(other.toString());
	}

	@Override
	public int compareTo(ConvertDate o) {
		return adjustedSonarFormat.compareTo(o.getSonarFormat());
	}
	
	public static String convert(Calendar currentDate) {
		String timeZoneWithoutGMT = String.format("%1$tZ", currentDate).substring(3);
		String timeZone = "" + timeZoneWithoutGMT.substring(0,3) + timeZoneWithoutGMT.substring(4,6);
		return String.format("%1$tY-%1$tm-%1$teT%1$tH-%1$tM-%1$tS%2$s ", currentDate, timeZone);
	}
}
