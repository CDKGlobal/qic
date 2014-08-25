package com.testing123.downloader;

import java.net.URL;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.testing123.ui.Preferences;
import com.testing123.vaadin.WebData;

/**
 * To use,
 * make an instance of this class, and call downloadProject().
 * 
 */
public class Downloader {

    private ObjectMapper mapper;

    public Downloader() {
        mapper = new ObjectMapper();
    }

    /**
     * @param projectName the project name
     */

    public List<WebData> downloadAndStoreInList(String projectKey, int depth, String distinguisher) {
        try {
            String link = Preferences.SONAR_HOME + "/api/resources?resource" + distinguisher + projectKey + "&depth=" + depth + "&metrics=ncloc,complexity&format=json";
            URL ownURL = new URL(link);

            List<WebData> list = mapper.readValue(ownURL, new TypeReference<List<WebData>>() {
            });
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
