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

public class retriever {
	
	public static ObjectMapper mapper;

	public static Set<DataPoint> getData() {
		
		mapper = new ObjectMapper();
		Map<String, Double[]> map = new HashMap<String, Double[]>();
		@SuppressWarnings("unchecked")
		List<WebData> dataList = (List<WebData>) mapper;
		@SuppressWarnings("unchecked")
		List<WebData> dataList2 = (List<WebData>) mapper;
		Set<DataPoint> dataSet = new HashSet<DataPoint>();
		
		
			String home = System.getProperty("user.home");
			String absolutePath = home + "/Perforce/chenc_sea-chenc-m_qic/Playpen/QIC2/";
			try {
				dataList = mapper.readValue(new File(absolutePath +
						"2014-07-07T06:09:17-0700/17271/files.json"),
						new TypeReference<List<WebData>>() {
						});

			dataList2 = mapper.readValue(new File(absolutePath +
					"2014-07-08T06:07:31-0700/17271/files.json"),
					new TypeReference<List<WebData>>() {
					});
			
			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
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
		
		return dataSet;
	}
}
