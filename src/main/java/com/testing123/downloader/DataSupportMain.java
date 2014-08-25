package com.testing123.downloader;

import java.sql.SQLException;

public class DataSupportMain {

	public static void processAuthorsAndDates() {
		DownloadAuthors authorDl = new DownloadAuthors();
		authorDl.addDateStamp();
		authorDl.addAuthors();
	}

	public static void processDeltaCalculations() {
		DBDeltaCalculator calc = new DBDeltaCalculator();
		try {
			calc.convertLatestMetrics();
		} catch (SQLException e) {
			System.out.println("Conversion of Metrics Failed!");
		}
	}
}
