package com.testing123.downloader;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.testing123.dataObjects.ChangedData;

public class FisheyeUploader {


    public static void uploadFEToDatabase(Connection conn) {
        List<ChangedData> fisheyeDataList = new DownloadFisheyeData().getAllFisheyeUpdates();
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            for (ChangedData fisheyeData : fisheyeDataList) {
                stmt.execute("UPDATE allFileHistory3 SET "
                                + "churn = " + fisheyeData.getChurn() + ", "
                                + "authors = '" + fisheyeData.getAuthors() + "' "
                                + "WHERE file_id = " + fisheyeData.getFile_id() + " "
                                + "and dbdate = '" + fisheyeData.getDate() + "';");
                System.out.println("churn = '" + fisheyeData.getChurn() + "', "
                                + "authors = '" + fisheyeData.getAuthors() +
                                "file_id = " + fisheyeData.getFile_id() + " "
                                + "and dbdate = '" + fisheyeData.getDate());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
