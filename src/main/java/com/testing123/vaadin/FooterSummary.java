package com.testing123.vaadin;

import com.testing123.controller.UIState.XAxis;
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
	
	public static FooterData getFooterDataByFile(DataPointSet points, XAxis x){
		return null;	
	}
}
