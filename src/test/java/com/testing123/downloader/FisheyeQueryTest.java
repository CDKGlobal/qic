package com.testing123.downloader;

import static org.junit.Assert.*;

import org.junit.Test;

public class FisheyeQueryTest {

	@Test
	public void test() {
		new FisheyeQuery().getRevisionsFromProject("Advertising.Perforce", "Platform","");
	}

}
