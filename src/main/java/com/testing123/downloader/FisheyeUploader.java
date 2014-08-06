package com.testing123.downloader;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.testing123.vaadin.ChangedData;

public class FisheyeUploader {

    public Connection getConnection() {
        return DatabaseConnector.getConnection();
    }

    public List<ChangedData> getFisheyeList() {
        return new DownloadFisheyeData().getAllFisheyeUpdates();
    }

    public void uploadFEToDatabase(Connection conn, List<ChangedData> fisheyeDataList) {
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            for (ChangedData fisheyeData : fisheyeDataList) {

            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
