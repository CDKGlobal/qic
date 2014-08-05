package com.testing123.downloader;

import java.sql.Connection;
import java.util.List;

import com.testing123.vaadin.WebData;

public class DataDownloaderMain {
    public static void main(String[] args) {
        try {
            Downloader dl = new Downloader();
            // downloads everything from the platform project
            List<WebData> projectList = Downloader.downloadProjectsAndStoreInList();
            DatabaseConnector dbConnector = new DatabaseConnector();
            // builds a connection
            Connection conn = DatabaseConnector.getConnection();
            dbConnector.createDbAndLoadTableForProject(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
