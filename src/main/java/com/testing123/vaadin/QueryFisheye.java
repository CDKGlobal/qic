package com.testing123.vaadin;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author blumbeb Returns maps from fisheye Queries where the key is a modified
 *         version of the path name
 */
public class QueryFisheye {

	public Map<String, Double> getChurnData(ConvertDate startDate, ConvertDate endDate) {

		URL url = new FisheyeQuery("Advertising.Perforce", startDate, endDate).getChurnURL();
		FisheyeData querriedData = getJSONFromFisheye(startDate, endDate, url);

		int pathIndex = querriedData.getHeadings().indexOf("path");
		int sumLinesAddedIndex = querriedData.getHeadings().indexOf("sumLinesAdded");
		int sumLinesRemovedIndex = querriedData.getHeadings().indexOf("sumLinesRemoved");
		int isDeletedIndex = querriedData.getHeadings().indexOf("countIsDeleted");
		
		if(metricsExist(pathIndex,sumLinesAddedIndex,sumLinesRemovedIndex,isDeletedIndex)){
		return putChurnDataIntoAMap(querriedData, pathIndex, sumLinesAddedIndex, sumLinesRemovedIndex, isDeletedIndex);
		}
		return new TreeMap<String, Double>();
	}

	private Map<String, Double> putChurnDataIntoAMap(FisheyeData querriedData, int pathIndex, int sumLinesAddedIndex,
			int sumLinesRemovedIndex, int isDeletedIndex) {
		Map<String, Double> churnData = new TreeMap<String, Double>();
		for (ItemData i : querriedData.getRow()) {
			if (isNotDeleted(i, isDeletedIndex)) {
				String path = formatPath(i, pathIndex);
				int linesAdded = (Integer) i.getItem(sumLinesAddedIndex);
				int linesRemoved = (Integer) i.getItem(sumLinesRemovedIndex);
				double churn = linesAdded + linesRemoved;
				churnData.put(path, churn);
			}
		}
		return churnData;
	}

	public static Map<String, List<String>> getAuthorData(ConvertDate startDate, ConvertDate endDate) {
		Map<String, List<String>> authorListMap = new TreeMap<String, List<String>>();

		URL url = new FisheyeQuery("Advertising.Perforce", startDate, endDate).getAuthorsURL();
		FisheyeData querriedData = getJSONFromFisheye(startDate, endDate, url);

		int pathIndex = querriedData.getHeadings().indexOf("path");
		int authorIndex = querriedData.getHeadings().indexOf("author");
		int isDeletedIndex = querriedData.getHeadings().indexOf("isDeleted");
		
		if (metricsExist(pathIndex, authorIndex, isDeletedIndex)) {
			for (ItemData i : querriedData.getRow()) {
				if (isNotDeleted(isDeletedIndex, i)) {
					addAuthorToMap(authorListMap, pathIndex, authorIndex, i);
				}
			}
		}
		return authorListMap;
	}

	private static void addAuthorToMap(Map<String, List<String>> authorListMap, int pathIndex, int authorIndex, ItemData i) {
		String path = formatPath(i, pathIndex);
		String author = (String) i.getItem(authorIndex);
		if (authorListMap.containsKey(path)) {
			authorListMap.get(path).add(author);
		} else {
			ArrayList<String> list = new ArrayList<String>();
			list.add(author);
			authorListMap.put(path, list);
		}
	}

	private static boolean metricsExist(int... i) {
		for (int j : i)
			if (j == -1)
				return false;
		return true;
	}



	private static boolean isNotDeleted(int isDeletedIndex, ItemData i) {
		return i.getItem(isDeletedIndex).equals("false");
	}

	private boolean isNotDeleted(ItemData i, int isDeletedIndex) {
		return (Integer) i.getItem(isDeletedIndex) == 0;
	}

	private static String formatPath(ItemData item, int pathIndex) {
		String path = (String) item.getItem(pathIndex);
		String[] split = path.split("/");
		StringBuffer buf = new StringBuffer();
		buf.append(split[0].toLowerCase());
		for (int i = 5; i < split.length; ++i) {
			buf.append(".");
			buf.append(split[i]);
		}
		String pathName = buf.toString();
		pathName = pathName.substring(0, pathName.length() - 5);
		return pathName;
	}

	public static FisheyeData getJSONFromFisheye(ConvertDate startDate, ConvertDate endDate, URL url) {

		ObjectMapper mapper = new ObjectMapper();
		FisheyeData querriedData = new FisheyeData();

		try {
			querriedData = mapper.readValue(url, new TypeReference<FisheyeData>() {
			});
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return querriedData;
	}

}
