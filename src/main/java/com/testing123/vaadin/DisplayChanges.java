package com.testing123.vaadin;

import java.net.MalformedURLException;
import java.net.URL;

import com.testing123.controller.UIState;
import com.testing123.dataObjects.ConvertPath;
import com.testing123.dataObjects.FisheyeInfo;
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
		FisheyeInfo revisionsToDiff = fisheye.extractFisheyeInfo(project, path, state.getStart(), state.getEnd());
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
}