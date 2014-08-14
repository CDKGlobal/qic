package com.testing123.dataObjects;

import static org.junit.Assert.*;

import org.junit.Test;

public class RepoAndDirDataTest {
	
	@Test
	public void testPassedInNullReturnNullForParameters(){
		RepoAndDirData isNull = new RepoAndDirData(null);
		assertEquals(null,isNull.getRepositoryName());
		assertEquals(null,isNull.getDirectoryName());
		assertEquals(new RepoAndDirData(null),isNull);
	}
	
	@Test
	public void testEmptyReturnsEmptyStrings(){
		RepoAndDirData empty = new RepoAndDirData("");
		assertEquals("",empty.getRepositoryName());
		assertEquals("",empty.getDirectoryName());
	}

	@Test
	public void testSameples() {
		RepoAndDirData bigScreen = new RepoAndDirData("/Advertising/bigscreen/trunk/ ");
		assertEquals("Advertising.Perforce",bigScreen.getRepositoryName());
		assertEquals("bigscreen/trunk/",bigScreen.getDirectoryName());
		
		RepoAndDirData qic = new RepoAndDirData("/QIC/");
		assertEquals("QIC.Perforce",qic.getRepositoryName());
		assertEquals("",qic.getDirectoryName());
	}

}
