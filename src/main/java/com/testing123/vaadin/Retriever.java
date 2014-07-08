package com.testing123.vaadin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Retriever {
	
	public static ObjectMapper mapper;

	public static Set<DataPoint> getData(String startDate, String endDate) {
		
		mapper = new ObjectMapper();
		Map<String, Double[]> map = new HashMap<String, Double[]>();
		List<WebData> dataList;
		List<WebData> dataList2;
		Set<DataPoint> dataSet = new HashSet<DataPoint>();
		
		
			String home = System.getProperty("user.home");
			String absolutePath = home + "/Perforce/chenc_sea-chenc-m_qic/Playpen/QIC2/";
			try {
				dataList = mapper.readValue(new File(absolutePath + startDate +
						"/17271/files.json"),
						new TypeReference<List<WebData>>() {
						});

			dataList2 = mapper.readValue(new File(absolutePath + endDate +
					"/17271/files.json"),
					new TypeReference<List<WebData>>() {
					});
			for (WebData file : dataList) {
				if (dataList.size() != 0) {
					if ("CLA".equals(file.getQualifier())){
						Double[] array = new Double[2];
						array[0]=file.getMsr().get(0).getVal();//Lines of code
						array[1]=file.getMsr().get(1).getVal();//Complexity
						map.put(file.getKey(), array);
					}
				}
			}
			
			for(WebData file: dataList2){
				if (dataList.size() != 0 && "CLA".equals(file.getQualifier())) {
						String key = file.getKey();
						if(map.containsKey(key)){
							double val0 = file.getMsr().get(0).getVal() - map.get(key)[0];
//							if(val0!=0 || val1!=0){
								dataSet.add(new DataPoint(file.getName(), Math.abs(val0), map.get(key)[1]));
//							}
						}else
							dataSet.add(new DataPoint(file.getName(), file.getMsr().get(0).getVal(), file.getMsr().get(1).getVal()));
				}
			}
			
			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		return dataSet;
	}
}
