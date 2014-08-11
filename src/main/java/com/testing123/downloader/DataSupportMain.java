package com.testing123.downloader;

import java.sql.SQLException;

import org.joda.time.DateTime;

public class DataSupportMain {

	public static void processAuthorsAndDates() {
		DownloadAuthors authorDl = new DownloadAuthors();
		authorDl.addDateStamp();
		authorDl.addAuthors();
	}

	public static void processDeltaCalculations() {
		DBDeltaCalculator calc = new DBDataCalculatorShort();
		try {
			calc.convertLatestMetrics();
		} catch (SQLException e) {
			System.out.println("Conversion of Metrics Failed!");
		}
	}
	
	public static String getFrmtDate(DateTime dt) {
		return dt.getYear() + "-" + frmtDigit(dt.getMonthOfYear()) + "-" + frmtDigit(dt.getDayOfMonth());
	}
	
	private static String frmtDigit(int a) {
		if (a < 10) {
			return "0" + a;
		} else {
			return a + "";
		}
	}
}
