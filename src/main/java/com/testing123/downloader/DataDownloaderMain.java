package com.testing123.downloader;

import java.sql.Connection;


public class DataDownloaderMain {
    public static void main(String[] args) {
        try {
            DatabaseConnector dbConnector = new DatabaseConnector();
            // builds a connection
            Connection conn = DatabaseConnector.getConnection();
            dbConnector.writeToTxtFileAndUpsertMetrics(conn);
            FisheyeUploader.uploadFEToDatabase(conn);
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        DataSupportMain.processDeltaCalculations();
        DataSupportMain.processAuthorsAndDates();

    }
}

