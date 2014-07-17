package com.testing123.vaadin;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class QueryFisheye {
	
	public Map<String, Double> getInstantaneousValue(ConvertDate date, String qualifier, String scope, String path, String metric) {
		return getChurnData(date,date );
	}

	public Map<String,Double> getChurnData(ConvertDate startDate, ConvertDate endDate) {
		Map<String,Double> churnData = new TreeMap<String,Double>();
		FisheyeData querriedData = getJSONFromFisheye(startDate, endDate);
		int pathIndex = querriedData.getHeadings().indexOf("path");
		int sumLinesAddedIndex = querriedData.getHeadings().indexOf("sumLinesAdded");
		int sumLinesRemovedIndex = querriedData.getHeadings().indexOf("sumLinesRemoved");
		int isDeletedIndex = querriedData.getHeadings().indexOf("countIsDeleted");
		
		for (ItemData i : querriedData.getRow()) {
			if (isNotDeleted(i, isDeletedIndex)) {
				String path = formatPath(i,pathIndex);
				int churn = (Integer) i.getItem(sumLinesAddedIndex) + (Integer) i.getItem(sumLinesRemovedIndex);
				churnData.put(path,(double) churn);
			}
		}
		System.out.println(churnData.entrySet().toString());
		return churnData;
	}
	
	private boolean isNotDeleted(ItemData i, int isDeletedIndex){
		return (Integer) i.getItem(isDeletedIndex) == 0 ;
	}
	
	private String formatPath(ItemData i, int pathIndex){
		String path = (String) i.getItem(pathIndex);
		String[] split = path.split("/");
		String pathName = split[split.length - 1].replace(".java", "");
		return pathName;
	}

	public static FisheyeData getJSONFromFisheye(ConvertDate startDate, ConvertDate endDate) {

		ObjectMapper mapper = new ObjectMapper();
		URL url = new FisheyeQuery("Advertising.Perforce", startDate, endDate).getChurn();
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
