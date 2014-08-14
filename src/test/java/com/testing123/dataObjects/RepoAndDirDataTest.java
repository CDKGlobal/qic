package com.testing123.dataObjects;

import static org.junit.Assert.*;

import org.junit.Test;

public class RepoAndDirDataTest {
	
	@Test
	public void testPassedInNullReturnNullForParameters(){
		RepoAndDirData empty = new RepoAndDirData(null);
		assertEquals(null,empty.getRepositoryName());
		assertEquals(null,empty.getDirectoryName());
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
