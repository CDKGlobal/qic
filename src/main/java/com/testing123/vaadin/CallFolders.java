package com.testing123.vaadin;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.RecursiveTask;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;

/**
 * Runs API calls in threads
 * @author chenc
 *
 */
@SuppressWarnings("serial")
public class CallFolders extends RecursiveTask<MapHolder> {
	
	private int low;
	private int high;
	private MapHolder m;
	private List<WebData> fL;
	private static final int SEQUENTIAL_CUTOFF = 50;
	
	public CallFolders(int low, int high, MapHolder m, List<WebData> fL) {
		this.low = low;
		this.high = high;
		this.m = m;
		this.fL = fL;
	}
	
	@Override
	protected MapHolder compute() {
		if (high - low <= SEQUENTIAL_CUTOFF) {
			for (int i = low; i < high; i++) {
				try {
					WebData folder = fL.get(i);
					String currentFolder = "http://sonar.cobalt.com/api/resources?resource="
							+ folder.getKey()
							+ "&depth=1&" 
							+ "metrics=" + Reader2.metric
							+ "&format=json";
					System.out.println(currentFolder);
					URL filesLink = new URL(currentFolder);
					List<WebData> fileList;
					fileList = Reader2.mapper.readValue(filesLink, new TypeReference<List<WebData>>() {});
					
					for (WebData file : fileList) {
						if (fileList.size() != 0) {
							if (!file.getQualifier().equals("CLA")) {
								continue;
							} else if (m.fileData.contains(file.getName())) {
								file.setName(file.getName().trim() + " (2)");
							}
							m.fileData.add(new DataPoint(file.getName(), file.getMsr().get(1).getVal(), 
															file.getMsr().get(0).getVal()));
						}
					}
				} catch (JsonParseException e) {
					e.printStackTrace();
				} catch (JsonMappingException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			System.out.println("THREAD COMPLETE");
			return m;
		} else {
			int mid = low + (high - low) / 2;
			CallFolders left = new CallFolders(low, mid, m, fL);
			CallFolders right = new CallFolders(mid, high, m, fL);
			left.fork();
			MapHolder rightRes = right.compute();
			MapHolder leftRes = left.join();
			return new MapHolder(leftRes, rightRes);
		}
	}

}
