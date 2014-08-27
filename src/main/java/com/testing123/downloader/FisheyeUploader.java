package com.testing123.downloader;

import java.sql.SQLException;
import java.util.List;

import com.testing123.controller.SQLConnector;
import com.testing123.dataObjects.ChangedData;

public class FisheyeUploader {


    public static void uploadFEToDatabase() {
        List<ChangedData> fisheyeDataList = new DownloadFisheyeData().getAllFisheyeUpdates();
        try {
        	SQLConnector conn = new SQLConnector();
            for (ChangedData fisheyeData : fisheyeDataList) {
            	/*if (fisheyeData.getIsDeleted()) {
            		conn.updateQuery("UPDATE allFileHistory3 SET "
                            + "churn = " + fisheyeData.getChurn() + ", "
                            + "authors = '" + fisheyeData.getAuthors() + "', "
                            + "complexity = 0"
                            + "WHERE file_id = " + fisheyeData.getFile_id() + " "
                            + "and dbdate = '" + fisheyeData.getDate() + "';"); 
                  System.out.println("churn = '" + fisheyeData.getChurn() + "', "
                  + "authors = '" + fisheyeData.getAuthors() +
                  "file_id = " + fisheyeData.getFile_id() + " "
                  + "and dbdate = '" + fisheyeData.getDate()
                  + " and complexity = 0;");
            	} else {*/
            		conn.updateQuery("UPDATE allFileHistory3 SET "
                                + "churn = " + fisheyeData.getChurn() + ", "
                                + "authors = '" + fisheyeData.getAuthors() + "' "
                                + "WHERE file_id = " + fisheyeData.getFile_id() + " "
                                + "and dbdate = '" + fisheyeData.getDate() + "';");
            	//}
            }
        	conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
