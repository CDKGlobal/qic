package com.testing123.downloader;

public class DataDownloaderMain {
    public static void main(String[] args) {
        try {
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

