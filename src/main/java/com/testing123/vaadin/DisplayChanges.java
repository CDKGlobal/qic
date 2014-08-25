package com.testing123.vaadin;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import com.testing123.controller.UIState;
import com.testing123.dataObjects.ConvertDate;
import com.testing123.dataObjects.ConvertPath;
import com.testing123.dataObjects.FisheyeData;
import com.testing123.dataObjects.FisheyeInfo;
import com.testing123.dataObjects.ItemData;
import com.testing123.dataObjects.RepoAndDirData;
import com.testing123.downloader.FisheyeQuery;
import com.testing123.interfaces.DatabaseInterface;
import com.testing123.interfaces.FisheyeInterface;
import com.testing123.ui.Preferences;

public class DisplayChanges {

	private FisheyeInterface fisheye;
	private DatabaseInterface database;

	public DisplayChanges() {
		this(new FisheyeQuery(), new UseSQLDatabase());
	}

	public DisplayChanges(FisheyeInterface FI, DatabaseInterface DI) {
		this.fisheye = FI;
		this.database = DI;
	}

	public URL popUp(UIState state, String fileKey) {
		RepoAndDirData project = database.getRepoAndDirFromFileKey(fileKey);
		if(project.equals(new RepoAndDirData(null)))
			return null;
		ConvertPath path = new ConvertPath(fileKey);
		FisheyeInfo revisionsToDiff = extractFisheyeInfo(project, path, state.getStart(), state.getEnd());
		return getURL(project.getRepositoryName(), revisionsToDiff);
	}
	
	private URL getURL(String repository, FisheyeInfo info) {
		String fisheyeHomeLink = Preferences.FISHEYE_HOME;
		URL url = null;
		String link = fisheyeHomeLink + "/browse/" + repository + "/" + info.getCompleteFisheyePath() + "?r1=" + info.getLatestRevision()
				+ "&r2=" + info.getStartingRevision();
		System.out.println(link);
		try {
			url = new URL(link);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return url;
	}
	
	public FisheyeInfo extractFisheyeInfo(RepoAndDirData project, ConvertPath path, ConvertDate startDate, ConvertDate endDate) {
		FisheyeData data;
		data = fisheye.getrevision(project, path, endDate.getDBFormatPlusOne());
		FisheyeInfo info = extractInfoFromFisheye(data);
		data = fisheye.getrevision(project, path, startDate.getDBFormat());
		info.setStartingRevision(extractInfoFromFisheye(data));
		return info;
	}
	
	private FisheyeInfo extractInfoFromFisheye(FisheyeData changesets) {
		int csidIndex = getIndex(changesets, "csid");
		int pathIndex = getIndex(changesets, "path");
		List<ItemData> revisionList = changesets.getRow();
		int numOfRevisions = revisionList.size();
		if (csidIndex != -1 && pathIndex != -1 && numOfRevisions > 0) {
			int revision = Integer.parseInt((String) revisionList.get(0).getItem(csidIndex));
			String completeFisheyePath = (String) revisionList.get(0).getItem(pathIndex);
			return new FisheyeInfo(revision, completeFisheyePath);
		}else{
			return null;
		}
	}
	
	private int getIndex(FisheyeData data, String key) {
		return data.getHeadings().indexOf(key);
	}
}