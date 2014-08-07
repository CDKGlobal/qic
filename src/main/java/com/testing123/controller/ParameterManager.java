package com.testing123.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.testing123.controller.UIState.XAxis;
import com.testing123.controller.UIState.YAxis;
import com.testing123.dataObjects.ConvertDate;
import com.testing123.dataObjects.ConvertProject;

public class ParameterManager {
	public static final String QSTART = "st";
	public static final String QEND = "end";
	public static final String QX = "x";
	public static final String QY = "y";
	public static final String QPROJECTS = "proj";
	public static final String QAUTH = "auth";
	
	public UIState getState(String start, String end, String x, String y, String proj, String auth) {
		UIState state;
		if (start != null && end != null && x != null) {
			state = new UIState(new ConvertDate(start), new ConvertDate(end), getXAxisMapping(x));
			state.setY(getYAxisMapping(y));
			setAuthorState(auth, state);
			setProjectState(proj, state);
		} else {
			state = new UIState();
		}
		return state;
	}

	private void setAuthorState(String auth, UIState state) {
		if (auth.equals("")) {
			state.setAuthorsFilter(new HashSet<String>());
		} else {
			String[] authorsArray = auth.split(",");
			state.setAuthorsFilter(new HashSet<String>(Arrays.asList(authorsArray)));
		}
	}

	private void setProjectState(String proj, UIState state) {
		List<ConvertProject> projsList = AvailableResources.getAvailableProjects();
		List<String> projsArray = Arrays.asList(proj.split(","));
		Set<ConvertProject> projsSet = new HashSet<ConvertProject>();
		for (ConvertProject project : projsList) {
			if (projsArray.contains("" + project.getID())) {
				projsSet.add(project);
			}
		}
		state.setProjects(projsSet);
	}	
	
	private static XAxis getXAxisMapping(String tag) {
		List<XAxis> xAxisList = XAxis.possibleValues();
		Map<String, XAxis> mapping = new HashMap<String, XAxis>();
		for (XAxis axis : xAxisList) {
			mapping.put(axis.getColName(), axis);
		}
		return mapping.get(tag);
	}
	
	private static YAxis getYAxisMapping(String tag) {
		List<YAxis> yAxisList = YAxis.possibleValues();
		Map<String, YAxis> mapping = new HashMap<String, YAxis>();
		for (YAxis axis : yAxisList) {
			mapping.put(axis.getColName(), axis);
		}
		return mapping.get(tag);
	}
}
