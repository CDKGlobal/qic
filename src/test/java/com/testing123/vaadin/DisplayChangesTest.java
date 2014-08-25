package com.testing123.vaadin;

import static org.junit.Assert.*;


import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.testing123.controller.UIState;
import com.testing123.dataObjects.ConvertDate;
import com.testing123.dataObjects.RepoAndDirData;
import com.testing123.interfaces.DatabaseInterface;
import com.testing123.vaadin.DisplayChanges;

public class DisplayChangesTest {
	
	private UIState state;
	private DatabaseInterface DBIMock;
	
	@Before
	public void before(){
		state = new UIState();
		DBIMock = Mockito.mock(DatabaseInterface.class);
	}
	
	@Test
	public void testNullRepoAndDirDataReturnsNull(){
		state.setStart(new ConvertDate("2014-08-05"));
		state.setEnd(new ConvertDate("2014-08-07"));
		String fileKey = "returnsNull";
		RepoAndDirData n = new RepoAndDirData(null);
		Mockito.when(DBIMock.getRepoAndDirFromFileKey(fileKey)).thenReturn(n);
		assertEquals(null, new DisplayChanges().popUp(state,fileKey));
	}

}
