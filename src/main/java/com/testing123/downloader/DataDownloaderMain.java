package com.testing123.downloader;

public class DataDownloaderMain {

    public static void main(String[] args) {
        Downloader dl = new Downloader();
        // downloads everything from the platform project
        dl.downloadProjects("platform");
        DatabaseConnector dbConnector = new DatabaseConnector();
        String date = Downloader.today;
        dbConnector.connectAndExecute(date);
    }
}
