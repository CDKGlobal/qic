package com.testing123.vaadin;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class ConvertDate implements Comparable<ConvertDate> {
	private Calendar currentDate;
	private String adjustedSonarFormat;
	
	public ConvertDate(String sonarFormat) {
		this.adjustedSonarFormat = sonarFormat.replace(":", "-");
		int year = Integer.parseInt(sonarFormat.substring(0, 4));
		int month = Integer.parseInt(sonarFormat.substring(5, 7))-1;
		int day = Integer.parseInt(sonarFormat.substring(8, 10));
		int hour = Integer.parseInt(sonarFormat.substring(11, 13));
		int minute = Integer.parseInt(sonarFormat.substring(14, 16));
		int second = Integer.parseInt(sonarFormat.substring(17, 19));
		String timeZone = "GMT" + sonarFormat.substring(19);
		Calendar currentDate = new GregorianCalendar( year, month, day, hour, minute, second);
		currentDate.setTimeZone(TimeZone.getTimeZone(timeZone));
		this.currentDate = currentDate;
		checkRep();
	}
	
	public String getSonarFormat() {
		checkRep();
		return adjustedSonarFormat;
	}
	
	private void checkRep() {
		if (adjustedSonarFormat.contains(":")) {
			System.out.println(adjustedSonarFormat);
			throw new IllegalStateException("Sonar Format not valid");
		}
	}
	
	public String toString() {
		checkRep();
		return currentDate.getTime().toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((adjustedSonarFormat == null) ? 0 : adjustedSonarFormat.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConvertDate other = (ConvertDate) obj;
		if (adjustedSonarFormat == null) {
			if (other.adjustedSonarFormat != null)
				return false;
		} else if (!adjustedSonarFormat.equals(other.adjustedSonarFormat))
			return false;
		return true;
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
