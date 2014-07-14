package com.testing123.vaadin;

import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class FisheyeChurn {

	public static Set<DataPoint> getData(){
		
		Set<DataPoint> dataSet = new HashSet<DataPoint>();
		
		FisheyeData querriedData = FisheyeChurnGetJson();
		
		for (ItemData i : querriedData.getRow()) {
			if((Integer)i.getItem(3)==0){
				String pathName = (String)i.getItem(0);
				String[] split = pathName.split("/");
			    String name=split[split.length-1].replace(".java", "");
				
				dataSet.add(new DataPoint(name,(Integer)i.getItem(1),(Integer)i.getItem(2)));
			}
		}
		System.out.println();System.out.println(dataSet);
		return dataSet;
	}

	public static FisheyeData FisheyeChurnGetJson() {
		return FisheyeChurnGetJson("2013-07-07", "2014-07-08");
	}
	

	public static FisheyeData FisheyeChurnGetJson(String startDate, String endDate){
		
		ObjectMapper mapper = new ObjectMapper();

		String query = getQuery(startDate, endDate, "Platform");
		String link = getLink("Advertising", query);
		FisheyeData querriedData;
		
		try {
			URL projectURL = new URL(link);
			System.out.println(projectURL);
			querriedData = mapper.readValue(projectURL, new TypeReference<FisheyeData>() {
			});
			return querriedData;
		} catch (Exception e) {
			return new FisheyeData();
		}

	}
	
	public static String getLink(){
		String query = getQuery("2014-07-07", "2014-07-08","Platform");
		return getLink("Advertising", query);
	}

	private static String getQuery(String startDate, String endDate, String project) {
		String dateRange = "where%20date%20in%20[" + startDate + "," + endDate + "]";
		String directory = "and%20path%20like%20\\" + project + "/trunk/src/main/**";
		String java = "and%20path%20like%20*.java";
		String returned = "path,%20sum(linesAdded),%20sum(linesRemoved),%20count(isDeleted)%20";

		return "select%20revisions%20" + dateRange + "%20" + directory + "%20" + java + "%20group%20by%20file%20return%20" + returned;
	}
	
	private static String getLink(String directory, String query){
		String home = "http://fisheye.cobalt.com/rest-service-fe/search-v1/queryAsRows";
		String directoryPath = "/Advertising.Perforce" + ".json";
		return home + directoryPath + "?query=" + query + "&maxReturn=3000";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	

	
	
	
	
	
	
	
	
	
	
	
	

}
