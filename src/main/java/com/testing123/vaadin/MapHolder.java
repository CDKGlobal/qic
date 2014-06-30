package com.testing123.vaadin;

import java.util.HashMap;
import java.util.Map;
/**
 * 
 * 
 * @author chenc
 */
public class MapHolder {
	
	public Map<String, Double> fileData;
	
	public MapHolder() {
		this.fileData = new HashMap<String, Double>();
	}
	
	public MapHolder(MapHolder map1, MapHolder map2) {
		this.fileData = new HashMap<String, Double>(map1.fileData);
		fileData.putAll(map2.fileData);
	}
}
