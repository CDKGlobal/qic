package com.testing123.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.joda.time.DateTime;

import com.testing123.controller.UIState.XAxis;
import com.testing123.controller.UIState.YAxis;
import com.testing123.dataObjects.ConvertDate;
import com.testing123.dataObjects.ConvertProject;
import com.testing123.interfaces.DatabaseInterface;
import com.testing123.vaadin.UseSQLDatabase;

public class ParameterManager {
	
	private DatabaseInterface database;
	public static final String QSTART = "st";
	public static final String QEND = "end";
	public static final String QX = "x";
	public static final String QY = "y";
	public static final String QPROJECTS = "proj";
	public static final String QAUTH = "auth";
	
	public ParameterManager(DatabaseInterface DBI){
		database = DBI;
	}
	
	public ParameterManager(){
		this(new UseSQLDatabase());
	}
	
	public UIState getState(String start, String end, String x, String y, String proj, String auth) {
		UIState state;
		if (start != null && end != null && x != null) {
			if (start.equals("today")) {
				state = new UIState();
				state.setStart(new ConvertDate(new DateTime().minusDays(Integer.parseInt(end)).toDate()));
				state.setX(getXAxisMapping(x));
				state.setY(getYAxisMapping(y));
				setAuthorState(auth, state);
				setProjectState(proj, state);
				return state;
			}
			state = new UIState(new ConvertDate(start), new ConvertDate(end), getXAxisMapping(x));
			state.setY(getYAxisMapping(y));
			setAuthorState(auth, state);
			setProjectState(proj, state);
		} else {
			System.out.println("Fields were null, starting up as default state");
			state = new UIState();
		}
		return state;
	}

	private void setAuthorState(String auth, UIState state) {
		if (auth == null || auth.equals("")) {
			state.setAuthorsFilter(new HashSet<String>());
		} else {
			String[] authorsArray = auth.split(",");
			state.setAuthorsFilter(new HashSet<String>(Arrays.asList(authorsArray)));
		}
	}

	private void setProjectState(String proj, UIState state) {
		if (proj == null || proj.equals("")) {
			state.setProjects(new HashSet<ConvertProject>());
		} else {
			List<ConvertProject> projsList = database.getAvailableProjects();
			List<String> projsArray = Arrays.asList(proj.split(","));
			Set<ConvertProject> projsSet = new HashSet<ConvertProject>();
			for (ConvertProject project : projsList) {
				if (projsArray.contains("" + project.getID())) {
					projsSet.add(project);
				}
			}
			state.setProjects(projsSet);
		}
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
