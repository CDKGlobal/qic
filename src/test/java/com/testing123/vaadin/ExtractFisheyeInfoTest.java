package com.testing123.vaadin;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
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
	public void testEmptyRevisionList(){
		ExtractFisheyeInfo info = getInfo("emptyRevisionList.json");
		assertFalse(info.exists());
	}

	@Test
	public void testExampleData() {
		ExtractFisheyeInfo info = getInfo("revisiondataExample.json");
		assertEquals(795170, info.getRevision1());
		assertEquals(806118, info.getRevision2());
		assertEquals("src/main/java/com/testing123/vaadin/ConvertDate.java", info.getCompleteFisheyePath());
		assertTrue(info.exists());
	}
	
	private ExtractFisheyeInfo getInfo(String file){
		FisheyeData data = getJSONFromFisheye(file);
		return new ExtractFisheyeInfo(data);
	}
	private FisheyeData getJSONFromFisheye(String file) {

		ObjectMapper mapper = new ObjectMapper();
		FisheyeData querriedData = new FisheyeData();
		URL inputStream = ExtractFisheyeInfoTest.class.getClassLoader().getResource(file);
		//System.out.println(file);
		//System.out.println(inputStream);
		File f = new File(inputStream.getFile());
		try {
			querriedData = mapper.readValue( f, new TypeReference<FisheyeData>() {
			});
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return querriedData;
	}
}
