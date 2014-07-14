package com.testing123.vaadin;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class ConvertDate implements Comparable<ConvertDate> {
	private Calendar currentDate;
	private String sonarFormat;
	
	public ConvertDate(String sonarFormat) {
		this.sonarFormat = sonarFormat;
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
		return sonarFormat;
	}
	
	private void checkRep() {
//		if (!sonarFormat.equals(convert(currentDate))) {
//			System.out.println(convert(currentDate));
//			System.out.println(sonarFormat);
//			throw new IllegalStateException("Sonar Format and Current Date not valid");
//		}
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
				+ ((sonarFormat == null) ? 0 : sonarFormat.hashCode());
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
		if (sonarFormat == null) {
			if (other.sonarFormat != null)
				return false;
		} else if (!sonarFormat.equals(other.sonarFormat))
			return false;
		return true;
	}

	@Override
	public int compareTo(ConvertDate o) {
		return sonarFormat.compareTo(o.getSonarFormat());
	}
	
	public static String convert(Calendar currentDate) {
		String timeZoneWithoutGMT = String.format("%1$tZ", currentDate).substring(3);
		String timeZone = "" + timeZoneWithoutGMT.substring(0,3) + timeZoneWithoutGMT.substring(4,6);
		return String.format("%1$tY-%1$tm-%1$teT%1$tH-%1$tM-%1$tS%2$s ", currentDate, timeZone);
	}
}
