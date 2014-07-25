package com.testing123.downloader;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Scanner;

public class DataDownloaderMain {
    public static void main(String[] args) {
        try {
            Downloader dl = new Downloader();
            // downloads everything from the platform project
            ArrayList<Integer> idList = formerIdList();
            dl.downloadProjects("platform", idList);
            DatabaseConnector dbConnector = new DatabaseConnector();
            String todayDate = Downloader.today;
            // builds a connection
            Connection conn = dbConnector.getConnection();
            dbConnector.createDbAndLoadTable(conn, todayDate, "platform");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static ArrayList<Integer> formerIdList() throws FileNotFoundException {
        ArrayList<Integer> idList = new ArrayList<Integer>();
        Scanner input = new Scanner(new File("src/main/java/com/testing123/downloader/files0723.txt"));
        while (input.hasNextLine()) {
            String line = input.nextLine();
            Scanner lineScanner = new Scanner(line);
            int id = lineScanner.nextInt();
            idList.add(id);
        }
        return idList;

    }
}
