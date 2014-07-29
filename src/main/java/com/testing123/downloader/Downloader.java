package com.testing123.downloader;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.testing123.controller.SQLConnector;
import com.testing123.vaadin.WebData;

/**
 * Downloader downloads all the metric data available from Sonar at the time of execution.  To use,
 * make an instance of this class, and call downloadProject().
 * 
 * @author chenc
 */
public class Downloader {
    public static String today;

    private final ObjectMapper mapper;

    public Downloader() {
        mapper = new ObjectMapper();
    }

    /**
     * Downloads all the metric data (including package/directory/file data) available
     * 
     * @param projectName the project name
     */

    public static ResultSet getConnectionFromDB(String name) {
        ResultSet results = null;
        try {
            results = SQLConnector.dataQuery("", "SELECT id FROM " + name + ";");

            return results;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Integer> gerPreviousProjectList(String name) {
        ResultSet rs = Downloader.getConnectionFromDB(name);
        List<Integer> previousProjectList = new ArrayList<Integer>();
        try {
            while (rs.next()) {
                System.out.println(rs.getInt(1));
                previousProjectList.add(rs.getInt(1));
            }
            return previousProjectList;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public List<WebData> downloadProjectsAndWrite(boolean... test) {
        String projectLink = "http://sonar.cobalt.com/api/resources?&depth=0&metrics=ncloc,complexity&format=json";
        // List<Integer> previousProjectList = gerPreviousProjectList("projectList");
        URL projectURL;
        try {
            projectURL = new URL(projectLink);

            List<WebData> projectList = mapper.readValue(projectURL, new TypeReference<List<WebData>>() {
            });

            // makes a folder with today's date on it
            // today = projectList.get(0).getDate().replace(":", "-");
            String currentPath = null;
            // if (test[0]) {
            // currentPath = makeFolder("Tester/", today);
            // } else {
            currentPath = makeFolder("Archives/", "projectList");
            // }
            // creates projects
            // writeJson(currentPath, projectList, "projectList");
            // writeTxt(currentPath, projectList, "projectList");

            System.out.println("Ok before processing projects");
            PrintWriter writerToProjectList = new PrintWriter(currentPath + "projectList.txt");

            for (WebData project : projectList) {
                System.out.println("Ok before making folder");
                currentPath = makeFolder("Archives/projectList/", project.getId() + "");
                String projectOwnLink = "http://sonar.cobalt.com/api/resources?resource=" + project.getKey() + "&depth=2&metrics=ncloc,complexity&format=json";
                URL projectOwnURL = new URL(projectOwnLink);
                List<WebData> fileList = mapper.readValue(projectOwnURL, new TypeReference<List<WebData>>() {
                });

                PrintWriter writerToFile = new PrintWriter(currentPath + project.getId() + ".txt");
                PrintWriter writerToFileHistory = new PrintWriter(currentPath + project.getId() + "History.txt");
                List<WebData> currentList = new ArrayList<WebData>();

                // if (!previousProjectList.contains(project.getId())) {
                writerToProjectList.print(project.getId() + "\t" + project.getKey() + "\t" + project.getName() + "\t" + project.getScope()
                                + "\t" + project.getQualifier() + "\t" + project.getDate());
                if (project.getMsr() == null) {
                    writerToProjectList.println("\t-1.0 \t -1.0");
                } else {
                    writerToProjectList.println("\t" + project.getMsr().get(0).getVal() + "\t" + project.getMsr().get(1).getVal());
                }
                // }

                for (WebData file : fileList) {
                    currentList.add(file);
                    System.out.println(file.getId());

                    // gerPreviousProjectList("");

                    //       if (!idList.contains(file.getId())) {
                    System.out.println(file.getId());
                    writerToFile.println(project.getId() + "\t" + file.getId() + "\t" + file.getKey() + "\t" + file.getName() + "\t" + file.getScope()
                                    + "\t" + file.getQualifier());
                    writerToFileHistory.print(file.getId() + "\t" + file.getKey() + "\t" + file.getDate());
                    if (file.getMsr() == null) {
                        writerToFileHistory.println("\t-1.0 \t -1.0");
                    } else {
                        writerToFileHistory.println("\t" + file.getMsr().get(0).getVal() + "\t" + file.getMsr().get(1).getVal());
                    }
                    /*   } else {
                        writerToFileHistory.print(file.getId() + "\t" + file.getDate());
                        if (file.getMsr() == null) {
                            writerToFileHistory.println("\t-1.0 \t-1.0");
                        } else {
                            writerToFileHistory.println("\t" + file.getMsr().get(0).getVal() + "\t" + file.getMsr().get(1).getVal());
                        }
                    }*/
                }
                writerToFile.close();
                writerToFileHistory.close();
                // writeJson(currentPath, currentList, project.getId() + "");
                // writeTxt(currentPath, currentList, project.getId() + "");
            }

            writerToProjectList.close();
            return projectList;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }



    public void downloadProjects(String projectName, ArrayList<Integer> idList, boolean... test) {
        try {
            String projectLink = "http://sonar.cobalt.com/api/resources?resource=com.cobalt.dap:" + projectName +
                            "&depth=0&metrics=ncloc,complexity&format=json";
            URL projectURL = new URL(projectLink);
            List<WebData> projectList = mapper.readValue(projectURL, new TypeReference<List<WebData>>() {
            });
            // makes a folder with today's date on it
            today = projectList.get(0).getDate().replace(":", "-");
            String currentPath = null;
            // if (test[0]) {
            // currentPath = makeFolder("Tester/", today);
            // } else {
            currentPath = makeFolder("Archives/", today);
            // }
            currentPath = makeFolder(currentPath, projectList.get(0).getId() + "");
            System.out.println("WRITING FOLDERS");
            // creates directories
            URL folderURL = new URL("http://sonar.cobalt.com/api/resources?resource=com.cobalt.dap:" + projectName +
                            "&depth=-1&metrics=ncloc,complexity&scopes=DIR&format=json");
            List<WebData> folderList = mapper.readValue(folderURL, new TypeReference<List<WebData>>() {
            });
            writeJson(currentPath, folderList, "folders");
            writeTxt(currentPath, folderList, "folders");
            System.out.println("WRITING FILES");
            List<WebData> fileList = new ArrayList<WebData>();
            PrintWriter writerToFileList = new PrintWriter(currentPath + "files.txt");
            PrintWriter writerToFileHistory = new PrintWriter(currentPath + "filesHistory.txt");
            for (WebData folder : folderList) {
                String currentFolder = "http://sonar.cobalt.com/api/resources?resource="
                                + folder.getKey()
                                + "&depth=1&"
                                + "metrics=ncloc,complexity&format=json";
                URL filesLink = new URL(currentFolder);
                List<WebData> currentList = mapper.readValue(filesLink, new TypeReference<List<WebData>>() {
                });
                // PrintWriter writerToFileList = new PrintWriter(currentPath + "files.txt");
                // PrintWriter writerToFileHistory = new PrintWriter(currentPath + "filesHistory.txt");
                for (WebData file : currentList) {
                    fileList.add(file);
                    // System.out.println(file.getId());
                    if (!idList.contains(file.getId())) {
                        System.out.println(file.getId());
                        writerToFileList.println(file.getId() + "\t" + file.getKey() + "\t" + file.getName() + "\t" + file.getScope()
                                        + "\t" + file.getQualifier());
                        writerToFileHistory.print(file.getId() + "\t" + file.getDate());
                        if (file.getMsr() == null) {
                            writerToFileHistory.println("\t-1.0 \t -1.0");
                        } else {
                            writerToFileHistory.println("\t" + file.getMsr().get(0).getVal() + "\t" + file.getMsr().get(1).getVal());
                        }
                    } else {
                        writerToFileHistory.print(file.getId() + "\t" + file.getDate());
                        if (file.getMsr() == null) {
                            writerToFileHistory.println("\t-1.0 \t-1.0");
                        } else {
                            writerToFileHistory.println("\t" + file.getMsr().get(0).getVal() + "\t" + file.getMsr().get(1).getVal());
                        }
                    }
                }
            }
            writerToFileList.close();
            writerToFileHistory.close();
            // System.out.println("writer done, starts writing json");
            writeJson(currentPath, fileList, "files");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void writeJson(String path, List<WebData> data, String name) {
        String filePath = path + name;
        try {
            int i = 0;
            for (WebData file : data) {
                mapper.writeValue(new File(filePath + ".json"), data);
                if ("CLA".equals(file.getQualifier())) {
                    i++;
                }
            }
            // System.out.println(i);
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeTxt(String path, List<WebData> data, String name) {
        String filePath = path + name;
        try {
            PrintWriter writer = new PrintWriter(filePath + ".txt");
            for (WebData file : data) {
                writer.print(file.getId() + "\t" + file.getKey() + "\t" + file.getName() + "\t" + file.getScope()
                                + "\t" + file.getQualifier() + "\t" + file.getDate());
                if (file.getMsr() == null) {
                    writer.println("\t-1.0 \t -1.0");
                } else {
                    writer.println("\t" + file.getMsr().get(0).getVal() + "\t" + file.getMsr().get(1).getVal());
                }
            }
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String makeFolder(String path, String name) {
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
}
