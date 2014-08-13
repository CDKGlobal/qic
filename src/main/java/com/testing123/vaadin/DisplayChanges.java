package com.testing123.vaadin;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import com.testing123.controller.UIState;
import com.testing123.dataObjects.ConvertDate;
import com.testing123.dataObjects.ConvertPath;
import com.testing123.dataObjects.FisheyeData;
import com.testing123.dataObjects.ItemData;
import com.testing123.dataObjects.RepoAndDirData;
import com.testing123.downloader.FisheyeQuery;
import com.testing123.interfaces.DatabaseInterface;
import com.testing123.interfaces.FisheyeInterface;
import com.testing123.ui.Preferences;

public class DisplayChanges {
	
	private FisheyeInterface fisheye;
	private DatabaseInterface database;
	
	public DisplayChanges(){
		this(new FisheyeQuery(), new UseSQLDatabase());
	}
	
	public DisplayChanges(FisheyeInterface FI, DatabaseInterface DI){
		this.fisheye = FI;
		this.database = DI;
	}
	
	public URL popUp(UIState state, String path){
		ConvertDate startDate = state.getStart();
		ConvertDate endDate = state.getEnd();
		return fisheyeRevision(path,startDate,endDate);	
	}
	
	public URL fisheyeRevision(String fileKey, ConvertDate startDate, ConvertDate endDate){
		
		RepoAndDirData project = database.getRepoAndDirFromFileKey(fileKey);
		String repository = project.getRepositoryName();
		String directory = project.getDirectoryName();
		
		
		ConvertPath path = new ConvertPath(fileKey);
		FisheyeData changesets = fisheye.getRevisionList(repository, directory, path, startDate.getDBFormat(), endDate.getDBFormat());
		
		String completePath = getPath(changesets);
		int firstRevision = getFirstRevision(changesets);
		int secondRevision = getSecondRevision(changesets);
		
		//popUpChangesInFisheye(repository, completePath, firstRevision, secondRevision);		
		return getURL(repository, completePath, firstRevision, secondRevision);
	}
	
	private URL getURL(String repository, String fisheyePath, int revision1, int revision2) {
		String fisheyeHomeLink = Preferences.FISHEYE_HOME;
		String link = fisheyeHomeLink + "/browse/" + repository + "/" + fisheyePath +"?r1=" + revision1 + "&r2=" + revision2;
		URL url = null;
		try {
			url = new URL(link);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return url;
	}

	private int getFirstRevision(FisheyeData data){
		int csidIndex = data.getHeadings().indexOf("csid");
		if (csidIndex != -1){
			List<ItemData> revisionList = data.getRow();
			String csid = (String) revisionList.get(0).getItem(csidIndex);
			System.out.println("First csid = " + csid);
			return Integer.parseInt(csid);
			
		}
		return -1;
	}
	
	private int getSecondRevision(FisheyeData data){
		int csidIndex = data.getHeadings().indexOf("csid");
		if (csidIndex != -1){
			List<ItemData> revisionList = data.getRow();
			int length = revisionList.size();
			String csid = (String) revisionList.get(length-1).getItem(csidIndex);
			System.out.println("First csid = " + csid);
			return Integer.parseInt(csid);
		}
		return -1;
	}
	
	private String getPath(FisheyeData data){
			int pathIndex = data.getHeadings().indexOf("path");
			if (pathIndex != -1){
				for(ItemData i:data.getRow()){
					return (String) i.getItem(pathIndex); 
				};
			}
			return "";
	}
}
