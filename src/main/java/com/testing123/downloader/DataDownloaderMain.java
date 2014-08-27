package com.testing123.downloader;

public class DataDownloaderMain {
    public static void main(String[] args) {
        try {
        	System.out.println("version 8/27  10:22");
            DatabaseConnector dbConnector = new DatabaseConnector();
            dbConnector.writeToTxtFileAndUpsertMetrics();
            FisheyeUploader.uploadFEToDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        DataSupportMain.processDeltaCalculations();
        DataSupportMain.processAuthorsAndDates();
    }
}

