package com.testing123.downloader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.testing123.controller.SQLConnector;
import com.testing123.ui.Preferences;
import com.testing123.vaadin.WebData;

public class DatabaseConnector {
	private SQLConnector conn;
	
	public DatabaseConnector() {
		this.conn = new SQLConnector();
	}

    public void writeToTxtFileAndUpsertMetrics() {
        try {
        	Downloader dl = new Downloader();
        	List<WebData> projectList = dl.downloadAndStoreInList("", 0, "?");
            System.out.println("Ok before conn.createStatement");
            String currentPath = Preferences.ARCHIVE_BACKUP;

            String currentDate = getTodayDate();
            PrintWriter writer = new PrintWriter(currentPath + currentDate + ".txt");
            int i = 0;
            for (WebData project : projectList) {
                String projectPath = getProjectPath(project.getId());
                upsertProjectDataToDB(projectPath, project);
                int depth = 1;
                String projectKey = project.getKey();
                List<WebData> fileList = dl.downloadAndStoreInList(projectKey, depth, "=");
                System.out.println("project id" + project.getId());
                if (fileList.size() != 0) {
                    while (!fileList.get(0).getScope().equals("FIL")) {
                        depth++;
                        fileList = dl.downloadAndStoreInList(projectKey, depth, "=");
                    }

                    for (WebData file : fileList) {
                        upsertFileDataToDB(project, file);
                        upsertFileHistoryToDB(file, currentDate);
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

    public String getTodayDate() {
        Date nowDate = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = ft.format(nowDate);
        return currentDate;
    }

    private void upsertFileHistoryToDB(WebData file, String currentDate) throws SQLException {
        String query = "INSERT INTO allFileHistory3("
                        + "file_id, "
                        + "file_key, "
                        + "ncloc, "
                        + "complexity, "
                        + "issues, "
                        + "dbdate, "
                        + "delta_complexity) VALUES ("
                        + file.getId() + ", '"
                        + file.getKey() + "', ";
        if (file.getMsr() == null) {
            query = query + "-1.0, -1.0, 0.0, '";
        } else if (file.getMsr().size() == 2) {
            query = query + file.getMsr().get(0).getVal() + ", "
                            + file.getMsr().get(1).getVal() + ", "
                            	+ "0.0, '";
        } else if (file.getMsr().size() == 3) {
        	query = query + file.getMsr().get(0).getVal() + ", "
                    + file.getMsr().get(1).getVal() + ", "
                    	+ file.getMsr().get(2).getVal() + ", '";
        } else {
        	throw new IllegalStateException("Msr size: " + file.getMsr().size());
        }
        query = query + currentDate + "', NULL) ON DUPLICATE KEY UPDATE "
                        + "file_key = values(file_key),"
                        + "ncloc = values(ncloc),"
                        + "complexity = values(complexity),"
                        + "dbdate = values(dbdate),"
                        + "issues = values(issues),"
                        + "delta_complexity = values(delta_complexity);";
        conn.updateQuery(query);
    }

    private void upsertFileDataToDB(WebData project, WebData file) throws SQLException {
        conn.updateQuery("INSERT INTO allFileList("
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

    private void upsertProjectDataToDB(String projectPath, WebData project) throws SQLException {
        conn.updateQuery("INSERT INTO projectList(project_id, project_key, name, scope, qualifier, date, path) VALUES ("
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

    private void writeTxt(PrintWriter writer, WebData file) {
        try {
            writer.print(file.getId() + "\t" + file.getKey() + "\t" + file.getName() + "\t" + file.getScope()
                            + "\t" + file.getQualifier() + "\t" + file.getDate() + "\t" + file.getDBDate());
            if (file.getMsr() == null) {
                writer.println("\t-1.0 \t -1.0 \t 0.0");
            } else if (file.getMsr().size() == 2) {
            	writer.println("\t" + file.getMsr().get(0).getVal() + "\t" + file.getMsr().get(1).getVal()
               		 + "\t 0.0");
            } else if (file.getMsr().size() == 3) {
                writer.println("\t" + file.getMsr().get(0).getVal() + "\t" + file.getMsr().get(1).getVal()
                		 + "\t" + file.getMsr().get(2).getVal());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static String getProjectPath(int index) {
        String projectPath = null;
		try {
			URL url = new URL(Preferences.SONAR_HOME + "/dashboard/index/" + index);
			InputStreamReader inputStream = new InputStreamReader(url.openStream());
			BufferedReader in = new BufferedReader(inputStream);
	        projectPath = parseForProjectPath(index, in);
	        inputStream.close();
	        in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
        return projectPath;
    }
    
    private static String parseForProjectPath(int index, BufferedReader in){
        String inputLine;
        String[] split;
        try {
			while ((inputLine = in.readLine()) != null) {
			    if (inputLine.contains("http://perforce.")) {
			        split = inputLine.split("(.com|//|(\">Sources))");
			        if (split.length > 2) {
			            return split[2];
			        }
			    } else if (inputLine.contains("scm:perforce:perforce.")) {
			        split = inputLine.split("(scm:perforce:perforce.|//|(</div>))");
			        if (split.length > 2) {
			            return "/" + split[2];
			        }
			    }
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return null;
    }

}
