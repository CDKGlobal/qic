package com.testing123.vaadin;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Retriever {

	private static final ObjectMapper mapper = new ObjectMapper();;

	public static Set<DataPoint> getData(String startDate, String endDate) {
		return privateGetData(startDate, endDate);
	}

	private static Set<DataPoint> privateGetData(String startDate,
			String endDate) {

		Map<String, Double[]> map = new HashMap<String, Double[]>();
		List<WebData> dataList;
		Set<DataPoint> dataSet = new HashSet<DataPoint>();

		String home = System.getProperty("user.home");
		String absolutePath = home
				+ "/Perforce/chenc_sea-chenc-m_qic/Playpen/QIC2/Archives/";

		dataList = getDataList(absolutePath, startDate);

		for (WebData file : dataList) {
			if (!dataList.isEmpty() && "CLA".equals(file.getQualifier())) {
				Double[] array = new Double[2];
				array[0] = file.getMsr().get(0).getVal();// Lines of code
				array[1] = file.getMsr().get(1).getVal();// Complexity
				map.put(file.getKey(), array);
			}
		}

		dataList = getDataList(absolutePath, endDate);

		for (WebData file : dataList) {
			if (!dataList.isEmpty() && "CLA".equals(file.getQualifier())) {
				String key = file.getKey();
				if (map.containsKey(key)) {
					double val0 = file.getMsr().get(0).getVal()
							- map.get(key)[0];
					// if(val0!=0 || val1!=0){
					dataSet.add(new DataPoint(file.getName(), Math.abs(val0),
							map.get(key)[1]));
					// }
				} else {
					dataSet.add(new DataPoint(file.getName(), file.getMsr()
							.get(0).getVal(), file.getMsr().get(1).getVal()));
				}
			}
		}

		return dataSet;
	}

	private static List<WebData> getDataList(String path, String date) {
		try {
			return mapper.readValue(
					new File(path + date + "/17271/files.json"),
					new TypeReference<List<WebData>>() {
					});
		} catch (Exception e) {
			return new ArrayList<WebData>();
		} 
	}
	
}
