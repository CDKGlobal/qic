package com.testing123.downloader;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.testing123.dataObjects.ChangedData;

public class FisheyeUploader {



    public void uploadFEToDatabase(Connection conn) {
        List<ChangedData> fisheyeDataList = new DownloadFisheyeData().getAllFisheyeUpdates();
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            for (ChangedData fisheyeData : fisheyeDataList) {
                stmt.execute("UPDATE allFileHistory3 SET "
                                + "churn = '" + fisheyeData.getChurn() + "', "
                                + "authors = '" + fisheyeData.getAuthors() + "' "
                                + "WHERE file_key = '" + fisheyeData.getFile_key() + "' "
                                + "and dbdate = '" + fisheyeData.getDate() + "';");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
