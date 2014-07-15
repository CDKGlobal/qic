package com.testing123.vaadin;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class FisheyeChurn {

	public static Set<DataPoint> getData(ConvertDate startDate, ConvertDate endDate) {

		Set<DataPoint> dataSet = new HashSet<DataPoint>();

		FisheyeData querriedData = fisheyeChurnGetJson(startDate, endDate);

		for (ItemData i : querriedData.getRow()) {
			if ((Integer) i.getItem(3) == 0) {
				String pathName = (String) i.getItem(0);
				String[] split = pathName.split("/");
				String name = split[split.length - 1].replace(".java", "");

				dataSet.add(new DataPoint(name, (Integer) i.getItem(1), (Integer) i.getItem(2)));
			}
		}
		System.out.println();
		System.out.println(dataSet);
		return dataSet;
	}

	public static FisheyeData fisheyeChurnGetJson(ConvertDate startDate, ConvertDate endDate) {

		ObjectMapper mapper = new ObjectMapper();

		FisheyeQuery query = new FisheyeQuery("Advertising.Perforce", startDate, endDate);
		query.inProject("Platform");
		query.onlyJava();
		query.addReturn("path");
		query.addReturn("sum(linesAdded)");
		query.addReturn("sum(linesRemoved)");
		query.addReturn("count(isDeleted)");
		FisheyeData querriedData = new FisheyeData();;

		URL projectURL;
		try {
			projectURL = new URL(query.toString());
			querriedData = mapper.readValue(projectURL, new TypeReference<FisheyeData>() {
			});
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return querriedData;

	}

	public static String getLink() {
		String query = getQuery("2014-07-07", "2014-07-08", "Platform");
		return getLink("Advertising", query);
	}

	private static String getQuery(String startDate, String endDate, String project) {
		String dateRange = "where%20date%20in%20[" + startDate + "," + endDate + "]";
		String directory = "and%20path%20like%20\\" + project + "/trunk/src/main/**";
		String java = "and%20path%20like%20*.java";
		String returned = "path,%20sum(linesAdded),%20sum(linesRemoved),%20count(isDeleted)%20";

		return "select%20revisions%20" + dateRange + "%20" + directory + "%20" + java + "%20group%20by%20file%20return%20" + returned;
	}

	private static String getLink(String directory, String query) {
		String home = "http://fisheye.cobalt.com/rest-service-fe/search-v1/queryAsRows";
		String directoryPath = "/Advertising.Perforce" + ".json";
		return home + directoryPath + "?query=" + query + "&maxReturn=3000";
	}

}
