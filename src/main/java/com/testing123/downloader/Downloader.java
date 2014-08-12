package com.testing123.downloader;

import java.net.URL;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.testing123.vaadin.WebData;

/**
 * To use,
 * make an instance of this class, and call downloadProject().
 * 
 */
public class Downloader {

    private static ObjectMapper mapper;

    public Downloader() {
        mapper = new ObjectMapper();
    }

    /**
     * @param projectName the project name
     */

    public static List<WebData> downloadAndStoreInList(String projectKey, int depth, String distinguisher) {
        try {
            String link = "http://sonar.cobalt.com/api/resources" + distinguisher + projectKey + "&depth=" + depth + "&metrics=ncloc,complexity&format=json";
            URL ownURL = new URL(link);
            System.out.println("ok");
            System.out.println(link);
            List<WebData> list = mapper.readValue(ownURL, new TypeReference<List<WebData>>() {
            });
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
