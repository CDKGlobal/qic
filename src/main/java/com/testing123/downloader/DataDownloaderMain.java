package com.testing123.downloader;

import java.sql.Connection;

public class DataDownloaderMain {
    public static void main(String[] args) {
        try {
            Downloader dl = new Downloader();
            // downloads everything from the platform project
            dl.downloadProjects("platform");
            DatabaseConnector dbConnector = new DatabaseConnector();
            String todayDate = Downloader.today;
            // builds a connection
            Connection conn = dbConnector.getConnection();
            dbConnector.createDbAndLoadTable(conn, todayDate);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
