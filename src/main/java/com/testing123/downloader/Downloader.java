package com.testing123.downloader;

import java.net.URL;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.testing123.vaadin.WebData;

/**
 * Downloader downloads all the metric data available from Sonar at the time of execution.  To use,
 * make an instance of this class, and call downloadProject().
 * 
 * @author chenc
 */
public class Downloader {

    private static ObjectMapper mapper;

    public Downloader() {
        mapper = new ObjectMapper();
    }

    /**
     * Downloads all the metric data (including package/directory/file data) available
     * 
     * @param projectName the project name
     */

    public static List<WebData> downloadProjectsAndStoreInList(boolean... test) {
        String projectLink = "http://sonar.cobalt.com/api/resources?&depth=0&metrics=ncloc,complexity&format=json";
        URL projectURL;
        try {
            projectURL = new URL(projectLink);

            List<WebData> projectList = mapper.readValue(projectURL, new TypeReference<List<WebData>>() {
            });
            return projectList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
