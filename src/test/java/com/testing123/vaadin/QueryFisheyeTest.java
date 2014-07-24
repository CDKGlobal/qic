package com.testing123.vaadin;



import static org.junit.Assert.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class QueryFisheyeTest {

	ConvertDate startDate;
	ConvertDate endDate;
	
	@Before
	public void setUp() {
		startDate = new ConvertDate("2014-07-07T06-09-17-0700");
		endDate = new ConvertDate("2014:07:10T06:07:56:0700");
	}
	
	@Test
	public void runsTest() {
		new QueryFisheye().getAuthorData(new FisheyeData());
	}
	
	
	@Test(expected = MalformedURLException.class)
	public void testInvalidUrlReturnsMalformedURLException() throws MalformedURLException{
		new QueryFisheye().getJSONFromFisheye(new URL(""));
	}
	
	@Test
	public void testEmptyQueryReturnsEmptyFisheyeData(){
		URL emptyURL = getURL("emptyFisheyeJson.json");
		FisheyeData empty =  new QueryFisheye().getJSONFromFisheye(emptyURL);
		assertEquals(0,empty.getHeadings().size());
		assertEquals(0,empty.getRow().size());
	}
	
	@Test
	public void testEmptyFishEyeReturnsEmptyMap(){
		FisheyeData data =  new FisheyeData();
		Map<String, List<String>> map = new QueryFisheye().getAuthorData(data);
		Map<String, Double> map2 = new QueryFisheye().getChurnData(data);
		assertTrue(map.isEmpty());
		assertTrue(map2.isEmpty());
	}
	
	@Test
	public void testChurn(){
		URL churn = getURL("exampleChurnJSON.json");
		FisheyeData churnData = new QueryFisheye().getJSONFromFisheye(churn);
		new QueryFisheye().getChurnData(churnData);
	}
	
	@Test
	public void testAuthors(){
		URL churn = getURL("exampleGetAuthorData");
		FisheyeData churnData = new QueryFisheye().getJSONFromFisheye(churn);
		new QueryFisheye().getAuthorData(churnData);
	}

	//Test more get JSON, get churn, get author
	
	private URL getURL(String fileName){
		return QueryFisheyeTest.class.getResource(fileName);
	}

}
