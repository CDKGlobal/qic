package com.testing123.vaadin;

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
	
	public void popUp(UIState state, String path){
		ConvertDate startDate = state.getStart();
		ConvertDate endDate = state.getEnd();
		fisheyeRevision(path,startDate,endDate);
	}
	
	public void fisheyeRevision(String fileKey, ConvertDate startDate, ConvertDate endDate){
		
		RepoAndDirData project = database.getRepoAndDirFromFileKey(fileKey);
		String repository = project.getRepository();
		String directory = project.getDirectory();
		
		
		ConvertPath path = new ConvertPath(fileKey);
		FisheyeData changesets = fisheye.getRevisionList(repository, directory, path, startDate.getDBFormat(), endDate.getDBFormat());
		
		String completePath = getPath(changesets);
		System.out.println("FisheyePath = " + completePath);
		changesets = fisheye.getRevisionList(repository, directory, completePath, startDate.getDBFormat(), endDate.getDBFormat());

		int firstRevision = getFirstRevision(changesets);
		int secondRevision = getSecondRevision(changesets);
		
		fisheye.popUpChangesInFisheye(repository, completePath, firstRevision, secondRevision);		
	
	}
	
	
	
//	private String format(String dbPath){
//		String [] split = dbPath.split(":");
//		int length = split.length;
//		return split[length-1].replaceAll("\\.", "/")+"**";
//	}
	
	private int getFirstRevision(FisheyeData data){
		int csidIndex = data.getHeadings().indexOf("csid");
		System.out.println(data.getHeadings().toString());
		System.out.println(data.getRow().toString());
		System.out.println("csidIndex = " + csidIndex);
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
