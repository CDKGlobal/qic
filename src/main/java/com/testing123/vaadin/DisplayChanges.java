package com.testing123.vaadin;

import java.net.MalformedURLException;
import java.net.URL;

import com.testing123.controller.UIState;
import com.testing123.dataObjects.ConvertDate;
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

	public URL popUp(UIState state, String path) {
		ConvertDate startDate = state.getStart();
		ConvertDate endDate = state.getEnd();
		return fisheyeRevision(path, startDate, endDate);
	}

	public URL fisheyeRevision(String fileKey, ConvertDate startDate, ConvertDate endDate) {

		RepoAndDirData project = database.getRepoAndDirFromFileKey(fileKey);
		String repository = project.getRepositoryName();
		String directory = project.getDirectoryName();

		ConvertPath path = new ConvertPath(fileKey);
		FisheyeData changesets = fisheye.getRevisionList(repository, directory, path, startDate.getDBFormat(), endDate.getDBFormat());

		return getURL(repository, new ExtractFisheyeInfo(changesets));
	}

	private URL getURL(String repository, ExtractFisheyeInfo data) {
		String fisheyeHomeLink = Preferences.FISHEYE_HOME;
		String link = fisheyeHomeLink + "/browse/" + repository + "/" + data.getCompleteFisheyePath() + "?r1=" + data.getRevision1()
				+ "&r2=" + data.getRevision2();
		URL url = null;
		try {
			url = new URL(link);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return url;
	}
}