package com.testing123.downloader;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.testing123.vaadin.WebData;

public class DatabaseConnector {

    private static ObjectMapper mapper;

    public DatabaseConnector() {
        mapper = new ObjectMapper();
    }

    /*   public void connectAndExecute(String date) {
        try {

            // The newInstance() call is a work around for some
            // broken Java implementations
            // \Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection conn = getConnection();
            execute(conn, date);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }*/


    public static Connection getConnection() {
        try {
            // Properties connectionProps = new Properties();
            Connection conn =
                            DriverManager.getConnection("jdbc:mysql://localhost/dataList4?" +
                                            "user=root&password=password");
            // Do something with the Connection
            return conn;
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return null;
    }

    public static Map<Integer, String> getPreviousProjects() {
        try {
            Connection conn = getConnection();
            Statement stat = conn.createStatement();
            ResultSet rs = stat.executeQuery("Select project_id, path from projectList");
            Map<Integer, String> previousProjectMap = new HashMap<Integer, String>();
            while (rs.next()) {
                System.out.println("id = " + rs.getInt(1) + " path = " + rs.getString(2));
                previousProjectMap.put(rs.getInt(1), rs.getString(2));
            }
            return previousProjectMap;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public void createDbAndLoadTableForProject(Connection conn) {
        Statement stmt = null;
        boolean rs = true;
        List<WebData> projectList = Downloader.downloadProjectsAndStoreInList();
        Map<Integer, String> previousProjects = getPreviousProjects();
        System.out.println("Ok before conn.createStatement");
        int i = 0;
        try {
            for (WebData project : projectList) {
                stmt = conn.createStatement();
                /*  rs = stmt.execute("CREATE TABLE projectList (project_id INT NOT NULL,"
                            + "project_key VARCHAR(170) NOT NULL, name VARCHAR(80) NOT NULL, "
                            + "scope VARCHAR(5) NOT NULL, qualifier VARCHAR(5) NOT NULL, date VARCHAR(30) NOT NULL,"
                            + " ncloc DECIMAL(8,1), complexity DECIMAL(8,1));");

                              System.out.println(project.getId() + "_id");

               rs = stmt.execute("CREATE TABLE allFileList (project_id INT NOT NULL, file_id INT NOT NULL,"
                            + "file_key VARCHAR(170) NOT NULL, name VARCHAR(80) NOT NULL, "
                            + "scope VARCHAR(5) NOT NULL, qualifier VARCHAR(5) NOT NULL);");

            rs = stmt.execute("CREATE TABLE allFileHistory (file_id INT NOT NULL, file_key VARCHAR(160) NOT NULL, date VARCHAR(30) NOT NULL,"
                            + " ncloc DECIMAL(8,1), complexity DECIMAL(8,1));");*/
                //    List<Integer> previousProjectList = Downloader.gerPreviousProjectList("project_id", "projectList");
                //    if (!previousProjectList.contains(project.getId())) {
                rs = stmt.execute("INSERT INTO projectList(project_id, project_key, name, scope, qualifier, date, path) VALUES ("
                                + project.getId() + ", '"
                                + project.getKey() + "', '"
                                + project.getName() + "', '"
                                + project.getScope() + "', '"
                                + project.getQualifier() + "', '"
                                + project.getDate() + "', '"
                                + previousProjects.get(project.getId()) + "') ON DUPLICATE KEY UPDATE "
                                + "project_key = values(project_key), "
                                + "name = values(name), "
                                + "date = values(date);");
                //     }
                int depth = 1;
                String projectOwnLink = "http://sonar.cobalt.com/api/resources?resource=" + project.getKey() + "&depth=" + depth + "&metrics=ncloc,complexity&format=json";
                URL projectOwnURL = new URL(projectOwnLink);
                List<WebData> fileList = mapper.readValue(projectOwnURL, new TypeReference<List<WebData>>() {
                });
                System.out.println("project id" + project.getId());
                if (fileList.size() != 0) {
                    while (!fileList.get(0).getScope().equals("FIL")) {
                        depth ++;
                        projectOwnLink = "http://sonar.cobalt.com/api/resources?resource=" + project.getKey() + "&depth=" + depth + "&metrics=ncloc,complexity&format=json";
                        projectOwnURL = new URL(projectOwnLink);
                        fileList = mapper.readValue(projectOwnURL, new TypeReference<List<WebData>>() {
                        });
                    }
                    for (WebData file : fileList) {
                        rs = stmt.execute("INSERT INTO allFileList("
                                        + "project_Id, "
                                        + "file_id, "
                                        + "file_key,"
                                        + "name, "
                                        + "scope, "
                                        + "qualifier) VALUES ("
                                        + project.getId() + ", "
                                        + file.getId() + ", '"
                                        + file.getKey() + "', '"
                                        + file.getName() + "', '"
                                        + file.getScope() + "', '"
                                        + file.getQualifier() + "') ON DUPLICATE KEY UPDATE "
                                        + "file_id = values(file_id);");
                        System.out.println("file key = " + file.getKey());
                        String query = "INSERT INTO allFileHistory3("
                                        + "file_id, "
                                        + "file_key, "
                                        + "ncloc, "
                                        + "complexity, "
                                        + "dbdate, "
                                        + "delta_complexity) VALUES ("
                                        + file.getId() + ", '"
                                        + file.getKey() + "', ";
                        if (file.getMsr() == null) {
                            query = query + "-1.0, -1.0, '";
                        } else {
                            query = query + file.getMsr().get(0).getVal() + ", "
                                            + file.getMsr().get(1).getVal() + ", '";
                        }
                        query = query + file.getDBDate() + "', NULL) ON DUPLICATE KEY UPDATE "
                                        + "file_id = values(file_id), "
                                        + "file_key = values(file_key),"
                                        + "dbdate = values(dbdate);";
                        rs = stmt.execute(query);
                        i++;
                    }
                }
            }
            System.out.println(i + "  all done");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}