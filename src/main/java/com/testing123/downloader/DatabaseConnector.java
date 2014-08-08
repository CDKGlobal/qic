package com.testing123.downloader;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.testing123.dataObjects.ConvertProject;
import com.testing123.vaadin.DatabaseInterface;
import com.testing123.vaadin.UseSQLDatabase;
import com.testing123.vaadin.WebData;

public class DatabaseConnector {

    private static ObjectMapper mapper;
    private static DatabaseInterface database;

    public DatabaseConnector() {
        this(new UseSQLDatabase());
    }
    public DatabaseConnector(DatabaseInterface DBI) {
    	database = DBI;
        mapper = new ObjectMapper();
    }

    public static Connection getConnection() {
        try {
            // Connection conn =
            // DriverManager.getConnection("jdbc:mysql://localhost/dataList4?" +
            // "user=root&password=password");
            Connection conn =
                            DriverManager.getConnection("jdbc:mysql://dc2pvpdc00059.vcac.dc2.dsghost.net:3306/dataList4?" +
                                            "user=root&password=password");
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
        List<ConvertProject> projectList = database.getAvailableProjects();
        Map<Integer, String> previousProjectMap = new HashMap<Integer, String>();
        for (ConvertProject project : projectList) {
            previousProjectMap.put(project.getID(), project.getPath());
        }
        return previousProjectMap;
    }


    public void WriteToTxtFileAndUpsertToThreeTables(Connection conn) {
        Statement stmt = null;
        new Downloader();
        List<WebData> projectList = Downloader.downloadProjectsAndStoreInList();
        Map<Integer, String> previousProjects = getPreviousProjects();
        System.out.println("Ok before conn.createStatement");
        String currentPath = makeFolder("Archives/", "projectList");
        try {

            Date nowDate = new Date();
            SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
            String currentDate = ft.format(nowDate);
            PrintWriter writer = new PrintWriter(currentPath + currentDate + ".txt");

            int i = 0;

            for (WebData project : projectList) {
                stmt = conn.createStatement();
                /*
                 * rs = stmt.execute("CREATE TABLE projectList (project_id INT NOT NULL,"
                 * + "project_key VARCHAR(170) NOT NULL, name VARCHAR(80) NOT NULL, "
                 * + "scope VARCHAR(5) NOT NULL, qualifier VARCHAR(5) NOT NULL, date VARCHAR(30) NOT NULL,"
                 * + " ncloc DECIMAL(8,1), complexity DECIMAL(8,1));");
                 * System.out.println(project.getId() + "_id");
                 */
                String projectPath = getProjectPath(project.getId());
                upsertProjectDataToDB(stmt, projectPath, project);
                //     }
                int depth = 1;
                String projectOwnLink;
                URL projectOwnURL;
                List<WebData> fileList = parseData(project, depth);
                System.out.println("project id" + project.getId());

                if (fileList.size() != 0) {
                    while (!fileList.get(0).getScope().equals("FIL")) {
                        depth ++;
                        fileList = parseData(project, depth);
                    }

                    for (WebData file : fileList) {
                        System.out.println("file key = " + file.getKey());
                        upsertFileDataToDB(stmt, project, file);
                        // System.out.println("file key = " + file.getKey());
                        upsertFileHistoryToDB(stmt, file, currentDate);
                        i++;
                        writeTxt(writer, file);
                    }
                }
            }
            writer.close();
            System.out.println(i + "  all done");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void upsertFileHistoryToDB(Statement stmt, WebData file, String currentDate) throws SQLException {
        String query = "INSERT INTO allFileHistory3("
                        + "file_id, "
                        + "file_key, "
                        + "ncloc, "
                        + "complexity, "
                        + "dbdate, "
                        + "delta_complexity) VALUES ("
                        + file.getId() + ", '"
                        + file.getKey() + "', ";
        // System.out.println(file.getKey());
        if (file.getMsr() == null) {
            query = query + "-1.0, -1.0, '";
        } else {
            query = query + file.getMsr().get(0).getVal() + ", "
                            + file.getMsr().get(1).getVal() + ", '";
        }
        query = query + currentDate + "', NULL) ON DUPLICATE KEY UPDATE "
                        + "file_key = values(file_key),"
                        + "ncloc = values(ncloc),"
                        + "complexity = values(complexity),"
                        + "dbdate = values(dbdate),"
                        + "delta_complexity = values(delta_complexity);";
        stmt.execute(query);
    }

    private void upsertFileDataToDB(Statement stmt, WebData project, WebData file) throws SQLException {
        stmt.execute("INSERT INTO allFileList("
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
                        + "file_key = values(file_key),"
                        + "name = values(name);");
    }

    private void upsertProjectDataToDB(Statement stmt, String projectPath, WebData project) throws SQLException {
        stmt.execute("INSERT INTO projectList(project_id, project_key, name, scope, qualifier, date, path) VALUES ("
                        + project.getId() + ", '"
                        + project.getKey() + "', '"
                        + project.getName() + "', '"
                        + project.getScope() + "', '"
                        + project.getQualifier() + "', '"
                        + project.getDate() + "', '"
                        + projectPath + "') ON DUPLICATE KEY UPDATE "
                        + "project_key = values(project_key), "
                        + "name = values(name), "
                        + "date = values(date);");
    }

    private List<WebData> parseData(WebData project, int depth) throws MalformedURLException, IOException, JsonParseException, JsonMappingException {
        String projectOwnLink = "http://sonar.cobalt.com/api/resources?resource=" + project.getKey() + "&depth=" + depth + "&metrics=ncloc,complexity&format=json";
        URL projectOwnURL = new URL(projectOwnLink);
        List<WebData> fileList = mapper.readValue(projectOwnURL, new TypeReference<List<WebData>>() {
        });
        return fileList;
    }

    private static String makeFolder(String path, String name) {
        File folder = new File(path + name);
        System.out.println(path + name);

        if (!folder.exists()) {
            System.out.println("creating directory: " + folder.getAbsolutePath());
            try {
                folder.mkdir();
            } catch (SecurityException se) {
                se.printStackTrace();
            }
        }
        return path + name + "/";
    }

    private void writeTxt(PrintWriter writer, WebData file) {
        try {
            writer.print(file.getId() + "\t" + file.getKey() + "\t" + file.getName() + "\t" + file.getScope()
                            + "\t" + file.getQualifier() + "\t" + file.getDate() + "\t" + file.getDBDate());
            if (file.getMsr() == null) {
                writer.println("\t-1.0 \t -1.0");
            } else {
                writer.println("\t" + file.getMsr().get(0).getVal() + "\t" + file.getMsr().get(1).getVal());
            }
            // System.out.println("writing file to txt: " + file.getKey());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getProjectPath(int index) {
        try {
            URL url = new URL("http://sonar.cobalt.com/dashboard/index/" + index);
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                if (inputLine.contains("perforce.")) {
                    String[] split = inputLine.split("\"");
                    if (split.length > 1) {
                        String[] split2 = split[1].split(".com");
                        if (split2.length > 0) {
                            String returnedString = "";
                            for (int i = 1; i < split2.length; i++) {
                                returnedString += split2[i];
                            }
                            return returnedString;
                        }
                    }
                }
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}