package com.testing123.vaadin;

import com.testing123.dataObjects.DataPoint;
import com.testing123.dataObjects.DataPointSet;
import com.testing123.dataObjects.FooterData;

public class FooterSummary {
	
	public static FooterData getFooterData(DataPointSet points){
		FooterData data = new FooterData();
		for(DataPoint point : points.getNegVals()){
			data.addNegative(point.getXValue());
		}
		for(DataPoint point: points.getPosVals()){
			data.addPositive(point.getXValue());
		}
		return data;
	}
	
	public static FooterData getFooterDataByFile(DataPointSet points){
		FooterData data = new FooterData();
		data.addNegative(points.getNegVals().size() * -1);
		data.addPositive(points.getPosVals().size());
		data.setTotal();
		return data;
	}
}
