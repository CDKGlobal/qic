package com.testing123.vaadin;

import java.net.URL;
import java.util.List;
import java.util.concurrent.RecursiveTask;

import com.fasterxml.jackson.core.type.TypeReference;

/**
 * Runs API calls in threads, in order to speed up the time it takes to accumulate the files
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
	
	/**
	 * Constructs a new CallFolders object so the Fork-Join framework can use it
	 * 
	 * @param low the folder cutoff
	 * @param high the folder cutoff
	 * @param m the MapHolder to store the data at the end
	 * @param fL the list of folders to be opened
	 */
	public CallFolders(int low, int high, MapHolder m, List<WebData> fL) {
		this.low = low;
		this.high = high;
		this.m = m;
		this.fL = fL;
	}
	
	/**
	 * Runs the program in parallel
	 */
	@Override
	protected MapHolder compute() {
		// intuitively marks each folder from 1...n
		// if the difference in the current portion of folders is greater than the cutoff,
		// it will compute this function linearly.
		if (high - low <= SEQUENTIAL_CUTOFF) {
			for (int i = low; i < high; i++) {
				try {
					// Gets the current folder being opened and prepares the url
					WebData folder = fL.get(i);
					String currentFolder = "http://sonar.cobalt.com/api/resources?resource="
							+ folder.getKey()
							+ "&depth=1&" 
							+ "metrics=" + Reader2.metric1 + "," + Reader2.metric2 + "&format=json";
					//System.out.println(currentFolder);
					
					// puts all the files in that folders into a list of WebData
					URL filesLink = new URL(currentFolder);
					List<WebData> fileList;
					fileList = Reader2.mapper.readValue(filesLink, new TypeReference<List<WebData>>() {});
					
					// Goes through the list of all the files and creates a DataPoint to put into a map
					for (WebData file : fileList) {
						if (fileList.size() != 0) {
							if (!file.getQualifier().equals("CLA")) {
								// continues if the current file is not a java file
								continue;
							} else if (m.fileData.contains(file.getName())) {
								// appends a string to the end of the file name if another file
								// with the same name already exists
								file.setName(file.getName().trim() + " (2)");
							}
							m.fileData.add(new DataPoint(file.getName(), file.getMsr().get(1).getVal(), 
															file.getMsr().get(0).getVal()));
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return m;
		} else {
			// Splits the current computation into two threads.
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
