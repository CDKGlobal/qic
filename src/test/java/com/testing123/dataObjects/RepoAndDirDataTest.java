package com.testing123.dataObjects;

import static org.junit.Assert.*;

import org.junit.Test;

public class RepoAndDirDataTest {
	
	@Test
	public void testEmptyReturnsEmptyStrings(){
		RepoAndDirData empty = new RepoAndDirData("");
		assertEquals("",empty.getRepository());
		assertEquals("",empty.getDirectory());
	}

	@Test
	public void testSameples() {
		RepoAndDirData bigScreen = new RepoAndDirData("/Advertising/bigscreen/trunk/ ");
		assertEquals("Advertising.Perforce",bigScreen.getRepository());
		assertEquals("bigscreen/trunk/",bigScreen.getDirectory());
		
		RepoAndDirData qic = new RepoAndDirData("/QIC/");
		assertEquals("QIC.Perforce",qic.getRepository());
		assertEquals("",qic.getDirectory());
	}

}
