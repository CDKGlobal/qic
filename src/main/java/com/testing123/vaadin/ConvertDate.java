package com.testing123.vaadin;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class ConvertDate {
	
	public static Calendar convert(String sonarFormat) {		
		int year = Integer.parseInt(sonarFormat.substring(0, 4));
		int month = Integer.parseInt(sonarFormat.substring(5, 7))-1;
		int day = Integer.parseInt(sonarFormat.substring(8, 10));
		int hour = Integer.parseInt(sonarFormat.substring(11, 13));
		int minute = Integer.parseInt(sonarFormat.substring(14, 16));
		int second = Integer.parseInt(sonarFormat.substring(17, 19));
		String timeZone = "GMT" + sonarFormat.substring(19);
		Calendar currentDate = new GregorianCalendar( year, month, day, hour, minute, second);
		currentDate.setTimeZone(TimeZone.getTimeZone(timeZone));
		return currentDate;
	}
	
	public static String convert(Calendar date){
		String timeZoneWithoutGMT = String.format("%1$tZ", date).substring(3);
		String timeZone = "" + timeZoneWithoutGMT.substring(0,3) + timeZoneWithoutGMT.substring(4,6);
		return String.format("%1$tY-%1$tm-%1$teT%1$tH-%1$tM-%1$tS%2$s ", date, timeZone);
	}

}
