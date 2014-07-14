package com.testing123.downloader;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.testing123.vaadin.WebData;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Downloader downloads all the metric data available from Sonar at the time of execution.  To use, 
 * make an instance of this class, and call downloadProject().
 * 
 * @author chenc
 */
public class Downloader {
	private ObjectMapper mapper;

	public Downloader() {
		this.mapper = new ObjectMapper();
	}
	
	/**
	 * Downloads all the metric data (including package/directory/file data) available
	 * 
	 * @param projectName the project name
	 */
	public void downloadProjects(String projectName) {
		String projectLink = "http://sonar.cobalt.com/api/resources?resource=com.cobalt.dap:" + projectName + "&depth=0&metrics=ncloc,complexity&format=json";
		try {
			URL projectURL = new URL(projectLink);
			List<WebData> projectList = mapper.readValue(projectURL, new TypeReference<List<WebData>>() {});
			
			// makes a folder with today's date on it
			String currentPath = makeFolder("Archives/", projectList.get(0).getDate().replace(":", "-"));
			
			
			// creates projects
			writeJson(currentPath, projectList, projectList.get(0).getId() + "");
			currentPath = makeFolder(currentPath, projectList.get(0).getId() + "");
			
			System.out.println("WRITING FOLDERS");
			// creates directories
			URL folderURL = new URL("http://sonar.cobalt.com/api/resources?resource=com.cobalt.dap:" + projectName + "&depth=-1&metrics=ncloc,complexity&scopes=DIR&format=json");
			List<WebData> folderList = mapper.readValue(folderURL, new TypeReference<List<WebData>>() {});
			writeJson(currentPath, folderList, "folders");
			
			System.out.println("WRITING FILES");
			List<WebData> fileList = new ArrayList<WebData>();
			for (WebData folder : folderList) {
				String currentFolder = "http://sonar.cobalt.com/api/resources?resource="
						+ folder.getKey()
						+ "&depth=1&" 
						+ "metrics=ncloc,complexity&format=json";
				URL filesLink = new URL(currentFolder);
				List<WebData> currentList = mapper.readValue(filesLink, new TypeReference<List<WebData>>() {});
				for (WebData b : currentList) {
					fileList.add(b);
				}
			}
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
					System.out.println("writing file: " + file.getKey());
				}
			}
			System.out.println(i);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private String makeFolder(String path, String name) {
		File folder = new File(path + name);
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
