package com.testing123.vaadin;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.junit.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.testing123.dataObjects.FisheyeData;

public class ExtractFisheyeInfoTest {
	
	@Test
	public void testemptyFisheyeData(){
		FisheyeData data = new FisheyeData();
		ExtractFisheyeInfo info = new ExtractFisheyeInfo(data);
		assertFalse(info.exists());
	}
	
	@Test
	public void testEmptyRevisionList() throws JsonMappingException, IOException, Exception{
		ExtractFisheyeInfo info = getInfo("emptyRevisionList.json");
		assertFalse(info.exists());
	}

	@Test
	public void testExampleData() throws JsonMappingException, IOException, Exception {
		ExtractFisheyeInfo info = getInfo("revisiondataExample.json");
		assertEquals(795170, info.getRevision1());
		assertEquals(806118, info.getRevision2());
		assertEquals("src/main/java/com/testing123/vaadin/ConvertDate.java", info.getCompleteFisheyePath());
		assertTrue(info.exists());
	}
	
	private ExtractFisheyeInfo getInfo(String file) throws JsonMappingException, IOException, Exception{
		FisheyeData data = getJSONFromFisheye(file);
		return new ExtractFisheyeInfo(data);
	}
	
	private FisheyeData getJSONFromFisheye(String file) throws Exception, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		FisheyeData querriedData = new FisheyeData();
		URL inputStream = ExtractFisheyeInfoTest.class.getClassLoader().getResource(file);
		File f = new File(inputStream.getFile());
		querriedData = mapper.readValue( f, new TypeReference<FisheyeData>() {});	
		return querriedData;
	}
}
