package com.testing123.vaadin;

import java.util.HashSet;
/**
 * 
 * 
 * @author chenc
 */
public class MapHolder {
	
	public HashSet<DataPoint> fileData;
	
	public MapHolder() {
		this.fileData = new HashSet<DataPoint>();
	}
	
	public MapHolder(MapHolder map1, MapHolder map2) {
		this.fileData = new HashSet<DataPoint>(map1.fileData);
		fileData.addAll(map2.fileData);
	}
}
