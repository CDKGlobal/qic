package com.testing123.vaadin;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.testing123.controller.UIState;
import com.testing123.controller.UIState.XAxis;
import com.testing123.controller.UIState.YAxis;
import com.testing123.dataObjects.CacheTag;
import com.testing123.dataObjects.ConvertDate;
import com.testing123.dataObjects.ConvertProject;
import com.testing123.dataObjects.DataPoint;
import com.testing123.dataObjects.QueryData;
import com.testing123.ui.Preferences;

public class GetData {
	private QueryDatabase query;
	private Set<String> repositories;
	
	// Used to cache previous query
	private CacheTag tag;
	private Set<QueryData> cache;
	
	public GetData() {
		this.query = new QueryDatabase();
		this.repositories = new HashSet<String>((Arrays.asList(Preferences.FISHEYE_REPOS)));
		Set<ConvertProject> emptyProjects = new HashSet<ConvertProject>();
		emptyProjects.add(new ConvertProject("", "", -1, ""));
		this.tag = new CacheTag(new ConvertDate("0000-00-00"), new ConvertDate("0000-00-00"), emptyProjects, false);
	}

	/**
	 * Requests data from the data layer
	 * 
	 * @return Set of DataPoint with the correct X and Y coordinates and details
	 */
	public Set<DataPoint> requestData(UIState state) {
		CacheTag requested = new CacheTag(state.getStart(), state.getEnd(), state.getProjects(), state.getX() == XAxis.LINESOFCODE);
		Set<DataPoint> points = getDataFromBlock(state.getX(), state.getY(), state.getAuthorsFilter(), updateDataInCache(requested));
		if (state.getAuthorsFilter().size() == 0 || state.getX() == XAxis.LINESOFCODE) {
			return points;
		} else {
			return filterAuthors(points, state.getAuthorsFilter());
		}
	}
	
	public Set<DataPoint> filterAuthors(Set<DataPoint> points, Set<String> authors) {
		Set<DataPoint> filteredPoints = new HashSet<DataPoint>();
		for (DataPoint point : points) {
			for (String editedAuthor : point.getAuthors()) {
				if (authors.contains(editedAuthor)) {
					filteredPoints.add(point);
				}
			}
		}
		return filteredPoints;
	}

	private Set<QueryData> updateDataInCache(CacheTag requested) {
		if (tag.equals(requested)) {
			System.out.println("Cache Hit");
		} else {
			System.out.println("Cache Miss");
			this.tag = requested;
			this.cache = query.getDataSet(requested.getStartDate(), requested.getEndDate(), requested.getProjects());
		}
		return this.cache;
	}

	private Set<DataPoint> getDataFromBlock(XAxis x, YAxis y, Set<String> authorsFilter, Set<QueryData> dataSet) {
		Set<DataPoint> results = new HashSet<DataPoint>();
		for (QueryData data : dataSet) {
			if (data.getMetric(x.getColName()) != 0.0) {
				DataPoint point = new DataPoint(data.getKey(), data.getMetric(x.getColName()), data.getMetric(y.getColName()));
				results.add(point);
			}
		}
		return results;
	}
		
//		Map<String, Double> yMetric = query.getDateRangeMetrics(startDate, endDate, projects);
//		
//		// Non-delta metric, requires only one query on end date
//		if (xAxis.equals(XAxis.LINESOFCODE)) {
//			return query.getNCLOC(endDate, projects);
//		}
		
		// delta metric, requires two queries


//		
//		if (xAxis.equals(XAxis.DELTA_COMPLEXITY)) {
//			xMap = query.getDeltaComplexity(startDate, endDate, authors, projects);
//		} else if (xAxis.equals(XAxis.DELTA_LINESOFCODE)) {
//			xMap = getChurn(state, startDate, endDate);
//		} else {
//			return new HashSet<DataPoint>();
//		}
//		Set<DataPoint> dataSet = aggregator(xMap, yMap);
//		dataSet = addAuthorsToDataSet(dataSet, state);
//
//		return dataSet;

//	private Map<String, Double> getChurn(UIState state, ConvertDate startDate, ConvertDate endDate) {
//		Map<String, Double> allMaps = new HashMap<String, Double>();
//		for (ConvertProject project : state.getProjects()) {
//			String repo = getRepositoryName(project);
//			if (repositories.contains(repo)) {
//				String projectName = getProjectName(project);
//				Map<String, Double> xMap = query.getChurn(repo, startDate, endDate, projectName);
//				allMaps.putAll(xMap);
//			}
//		}
//		return allMaps;
//	}

//	/**
//	 * Takes the maps for the x and y coordinates and puts them in a set
//	 * 
//	 * @param xMap
//	 * @param yMap
//	 * @return
//	 */
//	private Set<DataPoint> aggregator(Map<String, Double> xMap, Map<String, Double> yMap) {
//		Set<DataPoint> dataSet = new HashSet<DataPoint>();
//		for (Map.Entry<String, Double> xValues : xMap.entrySet()) {
//			String pathName = xValues.getKey();
//			System.out.println("X = " + pathName);
//			if (yMap.containsKey(pathName)) {
//				DataPoint current = new DataPoint(xValues.getKey(), xValues.getValue(), yMap.get(pathName));
//				dataSet.add(current);
//			}
//		}
//		for (Map.Entry<String, Double> yValues : yMap.entrySet()) {
//			String pathName = yValues.getKey();
//			System.out.println("Y = " + pathName);
//
//		}
//		return dataSet;
//	}

//	private Set<DataPoint> addAuthorsToDataSet(Set<DataPoint> dataSet, UIState state) {
//		Set<DataPoint> filteredDataSet = new HashSet<DataPoint>();
//		ConvertDate start = state.getStart();
//		ConvertDate end = state.getEnd();
//		Set<String> authorsSet = state.getAuthorsFilter();
//		for (ConvertProject project : state.getProjects()) {
//			String repo = getRepositoryName(project);
//			if (repositories.contains(repo)) {
//				String projectName = getProjectName(project);
//				Map<String, List<String>> authors = query.getAuthors(repo, start, end, authorsSet, projectName);
//				for (DataPoint point : dataSet) {
//					String pathName = point.getKey();
//					if (authors.containsKey(pathName)) {
//						point.setAuthors(authors.get(pathName));
//						filteredDataSet.add(point);
//					}
//				}
//			}
//		}
//		return filteredDataSet;
//	}

//	private String getRepositoryName(ConvertProject project) {
//		String path = project.getPath();
//		String[] split = path.split("/");
//		if(split.length>1){
//			return split[1] + ".Perforce";
//		}
//		return "";
//	}
	
//	private String getProjectName(ConvertProject project){
//		String path = project.getPath();
//		String[] split = path.split("/");
//		if(split.length>2){
//			int index = path.indexOf('/', 1);
//			return path.substring(index+1);
//		}
//		return "";
//	}
}
