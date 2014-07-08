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
import com.testing123.downloader.Downloader;

public class retriever {
	
	public static ObjectMapper mapper;

	public static Set<DataPoint> getData() {
		mapper = new ObjectMapper();
		Map<String, Double[]> map = new HashMap<String, Double[]>();
		List<WebData> dataList;
		List<WebData> dataList2;
		Set<DataPoint> dataSet = new HashSet<DataPoint>();
		try {
			String home = System.getProperty("user.home");
			String absolutePath = home + "/Perforce/chenc_sea-chenc-m_qic/Playpen/QIC2/";
			dataList = mapper.readValue(new File(absolutePath +
					"2014-07-07T06:09:17-0700/17271/files.json"),
					new TypeReference<List<WebData>>() {
					});
			dataList2 = mapper.readValue(new File(absolutePath +
					"2014-07-08T06:07:31-0700/17271/files.json"),
					new TypeReference<List<WebData>>() {
					});
			for (WebData file : dataList) {
				if (dataList.size() != 0) {
					if ("CLA".equals(file.getQualifier())){
						Double[] array = new Double[2];
						array[0]=file.getMsr().get(0).getVal();
						array[1]=file.getMsr().get(1).getVal();
						map.put(file.getKey(), array);
					}
					//dataSet.add(new DataPoint(file.getName(), file.getMsr().get(1)
							//.getVal(), file.getMsr().get(0).getVal()));
				}
			}
			
			for(WebData file: dataList2){
				if (dataList.size() != 0) {
					if ("CLA".equals(file.getQualifier())){
						String key = file.getKey();
						if(map.containsKey(key)){
							double val0 = file.getMsr().get(0).getVal() - map.get(key)[0];
							double val1 = file.getMsr().get(1).getVal() - map.get(key)[1];
//							if(val0!=0 || val1!=0){
								dataSet.add(new DataPoint(file.getName(), Math.abs(val0), map.get(key)[1]));
								//System.out.println(file.getKey());
								//System.out.println(""+file.getName() + "   "+ file.getMsr().get(0).getVal()+ "   " + file.getMsr().get(1).getVal());
								//System.out.println(""+key +"    "+map.get(key)[0]+"   "+ map.get(key)[1]);
							//}else{
								//System.out.println(file.getName());
//							}
						}else{
							dataSet.add(new DataPoint(file.getName(), file.getMsr().get(0).getVal(), file.getMsr().get(1).getVal()));
							//System.out.println("File only in two = " + file.getKey());
						}
					}

				}
			}
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}


		//String returnedString = "[" + dataSet + "]";
		return dataSet;
	}
}
