package com.testing123.vaadin;

import java.net.MalformedURLException;
import java.net.URL;

import com.testing123.controller.UIState;
import com.testing123.dataObjects.ConvertPath;
import com.testing123.dataObjects.FisheyeData;
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
		ConvertPath path = new ConvertPath(fileKey);
		FisheyeData changesets = fisheye.getRevisionList(project, path, state.getStart(), state.getEnd());
		return getURL(project.getRepositoryName(), new ExtractFisheyeInfo(changesets));
	}

	private URL getURL(String repository, ExtractFisheyeInfo data) {
		String fisheyeHomeLink = Preferences.FISHEYE_HOME;
		URL url = null;
		if(!data.exists()){
			return null;
		}
		String link = fisheyeHomeLink + "/browse/" + repository + "/" + data.getCompleteFisheyePath() + "?r1=" + data.getRevision1()
				+ "&r2=" + data.getRevision2();
		try {
			url = new URL(link);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return url;
	}
}