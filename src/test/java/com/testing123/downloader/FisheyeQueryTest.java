package com.testing123.downloader;

import static org.junit.Assert.*;

import org.junit.Test;

public class FisheyeQueryTest {

	@Test
	public void test() {
		new FisheyeQuery().getRevisionsFromProject("Advertising.Perforce", "Platform","");
	}
	
	@Test
	public void TestPopUp(){
		new FisheyeQuery().popUpChangesInFisheye("QIC.Perforce", "src/main/java/com/testing123/downloader/DBDeltaCalculator.java", 806170, 806354);
	}

}
