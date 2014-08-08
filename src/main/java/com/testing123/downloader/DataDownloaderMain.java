package com.testing123.downloader;

import java.sql.Connection;


public class DataDownloaderMain {
    public static void main(String[] args) {
        try {
            DatabaseConnector dbConnector = new DatabaseConnector();
            // builds a connection
            Connection conn = DatabaseConnector.getConnection();
            dbConnector.WriteToTxtFileAndUpsertToThreeTables(conn);
            FisheyeUploader fe = new FisheyeUploader();
            fe.uploadFEToDatabase(conn);
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

