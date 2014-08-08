package com.testing123.vaadin;

import java.util.List;

import com.testing123.controller.UIState;
import com.testing123.dataObjects.ConvertDate;
import com.testing123.dataObjects.FisheyeData;
import com.testing123.dataObjects.ItemData;
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
	
	public void fisheyeRevision(String dbPath, ConvertDate startDate, ConvertDate endDate){
		int projectID = database.getProjectID(dbPath);
		System.out.println("Project ID = " + projectID);
		String projectPath = database.getProjectPath(projectID);
		System.out.println("pj path = " + projectPath);
		String repository = getRepositoryName(projectPath);
		String directory = getDirectoryName(projectPath);
		String formattedPath = format(dbPath);
		FisheyeData changesets = fisheye.getRevisionList(repository, directory, formattedPath, startDate.getDBFormat(), endDate.getDBFormat());
		
		String path = getPath(changesets);
		System.out.println("path = " + path);
		changesets = fisheye.getRevisionList(repository, directory, path, startDate.getDBFormat(), endDate.getDBFormat());

		int firstRevision = getFirstRevision(changesets);
		int secondRevision = getSecondRevision(changesets);
		
		fisheye.popUpChangesInFisheye(repository, path, firstRevision, secondRevision);
	}
	
	
	
	private String format(String dbPath){
		String [] split = dbPath.split(":");
		int length = split.length;
		return split[length-1].replaceAll("\\.", "/")+"**";
	}
	
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

	private String getRepositoryName(String path) {
		String[] split = path.split("/");
		if (split.length > 1) {
			return split[1] + ".Perforce";
		}
		return "";
	}
	
	private String getDirectoryName(String path) {
		String[] split = path.split("/");
		if (split.length > 2) {
			int index = path.indexOf('/', 1);
			return path.substring(index + 1);
		}
		return "";
	}
	
	//pass in DB path return URL or pop up new page
	//method(dbpath, start,end){
	//use dbpath to get project id
	//use project id to get pj-path
	//use pj-path to get set of revision changes
	//take first and last csid to get url
	//popup new page
}
