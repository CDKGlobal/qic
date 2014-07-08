package com.testing123.downloader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.testing123.vaadin.DataPoint;
import com.testing123.vaadin.WebData;

public class Uploader {
	private ObjectMapper mapper;
	private List<WebData> startData;
	private List<WebData> endData;

	public Uploader() {
		this.mapper = new ObjectMapper();
		this.startData = null;
		this.endData = null;
	}

	public void uploadFileData(String startDate, String endDate, String projectName) {
		String filePath = "/" + projectName + "/files.json";
		this.startData = uploadFile(startDate + filePath);
		this.endData = uploadFile(endDate + filePath);
	}
	
	private List<WebData> uploadFile(String path) {
		try {
			return mapper.readValue(path, new TypeReference<List<WebData>>() {});
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void getFileDifferenceList() {
		if (startData == null || endData == null) {
			System.out.println("NO DATA IN UPLOADER");
			return;
		}
		Set<DataPoint> fileData = new HashSet<DataPoint>();
		if (startData.size() != endData.size()) {
			System.out.println("NUMBER OF FILES DIFFERENT");
			return;
		}
		//Set<WebData> endDataSet = new HashSet<WebData>(endData);
		for (WebData startFile : startData) {
			if (startData.size() != 0) {
				if (!"CLA".equals(startFile.getQualifier())) {
					// continues if the current file is not a java file
					continue;
				} else if (fileData.contains(startFile.getName())) {
					// appends a string to the end of the file name if another file
					// with the same name already exists
					startFile.setName(startFile.getName().trim() + " (2)");
				}
//				fileData.add(new DataPoint(startFile.getName(), endDataSet.get(startFile.getMsr().get(1).getVal(), 
//												startFile.getMsr().get(0).getVal()));
			}
		}
	}
}
