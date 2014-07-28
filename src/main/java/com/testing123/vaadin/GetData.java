package com.testing123.vaadin;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.testing123.controller.UIState;
import com.testing123.controller.UIState.Axis;

public class GetData implements Retrievable {

	private UIState state;
	private Queryable query;

	private Set<String> repositories = new HashSet<String>();

	public GetData(Queryable queryInterface) {
		this.query = queryInterface;
		repositories.addAll(Arrays.asList(new String[] { "Advertising.Perforce", "Core.Perforce", "Intelligence.Perforce",
				"OpenPlatform.Perforce", "Owner.Perforce", "ProfessionalServices.Perforce", "ReleaseEngineering.Perforce",
				"Social.Perforce" }));
	}

	/**
	 * 
	 * @return Set of DataPoint with the correct X and Y coordinates and details
	 */
	@Override
	public Set<DataPoint> getData(UIState state) {
		this.state = state;
		ConvertDate startDate = state.getStart();
		ConvertDate endDate = state.getEnd();
		Map<String, Double> xMap;
		Map<String, Double> yMap;
		boolean authorsRequired = true;

		Axis xAxis = state.getX();
		if (xAxis.equals(Axis.DELTA_COMPLEXITY)) {
			xMap = query.getDeltaComplexity(startDate, endDate);
			yMap = query.getComplexity(endDate);

		} else if (xAxis.equals(Axis.DELTA_LINESOFCODE)) {
			xMap = getChurn(state);
			xMap = query.getChurn("Advertising.Perforce", startDate, endDate, "Platform");
			yMap = query.getComplexity(endDate);

		} else if (xAxis.equals(Axis.LINESOFCODE)) {
			xMap = query.getNCLOC(startDate);
			yMap = query.getComplexity(startDate);
			authorsRequired = false;
		} else {
			return new HashSet<DataPoint>();
		}
		Set<DataPoint> dataSet = aggregator(xMap, yMap);
		System.out.println("dataSet sixe = " + dataSet.size());
		if (authorsRequired)
			dataSet = addAuthorsToDataSet(dataSet);

		return dataSet;
	}

	private Map<String, Double> getChurn(UIState state) {
		Map<String, Double> allMaps = new HashMap<String, Double>();
		ConvertDate startDate = state.getStart();
		ConvertDate endDate = state.getEnd();
		for (ConvertProject project : state.getProjects()) {
			String repo = getRepository(project);
			if (!repo.isEmpty()) {
				String projectName = "Platform";
				Map<String, Double> xMap = query.getChurn(repo, startDate, endDate, projectName);
				allMaps.putAll(xMap);
			}
		}
		return allMaps;
	}

	/**
	 * Takes the maps for the x and y coordinates and puts them in a set
	 * 
	 * @param xMap
	 * @param yMap
	 * @return
	 */
	private Set<DataPoint> aggregator(Map<String, Double> xMap, Map<String, Double> yMap) {
		Set<DataPoint> dataSet = new HashSet<DataPoint>();
		for (Map.Entry<String, Double> xValues : xMap.entrySet()) {
			String pathName = xValues.getKey();
			if (yMap.containsKey(pathName)) {
				DataPoint current = new DataPoint(xValues.getKey(), xValues.getValue(), yMap.get(pathName));
				dataSet.add(current);
			}
		}
		return dataSet;
	}

	private Set<DataPoint> addAuthorsToDataSet(Set<DataPoint> dataSet) {
		Set<DataPoint> filteredDataSet = new HashSet<DataPoint>();
		ConvertDate start = state.getStart();
		ConvertDate end = state.getEnd();
		Set<String> authorsSet = state.getAuthorsFilter();
		for (ConvertProject project : state.getProjects()) {
			String repo = getRepository(project);
			if (!repo.isEmpty()) {
				String projectName = "Platform";
				Map<String, List<String>> authors = query.getAuthors(repo, start, end, authorsSet, projectName);
				for (DataPoint point : dataSet) {
					String pathName = point.getKey();
					if (authors.containsKey(pathName)) {
						point.setAuthors(authors.get(pathName));
						filteredDataSet.add(point);
					}
				}
			}
		}
		return filteredDataSet;
	}

	private String getRepository(ConvertProject project) {
		String name = project.getName();
		String repo = name.split("/")[0].trim() + ".Perforce";
		System.out.println("repo = " + repo);
		if (repositories.contains(repo)) {
			return repo;
		} else {
			return "";
		}
	}
}
