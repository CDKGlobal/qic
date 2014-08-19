package com.testing123.vaadin;

import java.util.HashSet;
import java.util.Set;

import com.testing123.controller.UIState;
import com.testing123.controller.UIState.XAxis;
import com.testing123.controller.UIState.YAxis;
import com.testing123.dataObjects.CacheTag;
import com.testing123.dataObjects.ConvertDate;
import com.testing123.dataObjects.ConvertProject;
import com.testing123.dataObjects.DataPoint;
import com.testing123.dataObjects.DataPointSet;
import com.testing123.dataObjects.QueryData;

public class GetData {
	private QueryDatabase query;
	
	// Used to cache previous query
	private CacheTag tag;
	private Set<QueryData> cache;
	
	public GetData() {
		this.query = new QueryDatabase();
		Set<ConvertProject> emptyProjects = new HashSet<ConvertProject>();
		emptyProjects.add(new ConvertProject("", "", -1, ""));
		this.tag = new CacheTag(new ConvertDate("0000-01-01"), new ConvertDate("0000-01-01"), emptyProjects, false);
	}

	/**
	 * Requests data from the data layer
	 * 
	 * @return Set of DataPoint with the correct X and Y coordinates and details
	 */
	public DataPointSet requestData(UIState state) {
		CacheTag requested = new CacheTag(state.getStart(), state.getEnd(), state.getProjects(), state.getX() == XAxis.LINESOFCODE);
		Set<DataPoint> points = getDataFromBlock(state.getX(), state.getY(), state.getAuthorsFilter(), updateDataInCache(requested));
		if (state.getAuthorsFilter().size() == 0 || state.getX() == XAxis.LINESOFCODE) {
			return setSeries(points);
		} else {
			return setSeries(filterAuthors(points, state.getAuthorsFilter()));
		}
	}
	
	private DataPointSet setSeries(Set<DataPoint> points) {
		System.out.println(points.size());
		DataPointSet dataPoints = new DataPointSet();
		for (DataPoint point : points) {
			if (point.getXValue() < 0) {
				dataPoints.addNeg(point);
			} else {
				dataPoints.addPos(point);
			}
		}
		return dataPoints;
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
			this.cache = query.getDataSet(requested.getStartDate(), requested.getEndDate(), requested.getProjects(), 
					requested.isSingleMetric());
		}
		return this.cache;
	}

	private Set<DataPoint> getDataFromBlock(XAxis x, YAxis y, Set<String> authorsFilter, Set<QueryData> dataSet) {
		Set<DataPoint> results = new HashSet<DataPoint>();
		for (QueryData data : dataSet) {
			if (data.getMetric(x.getColName()) != 0.0) {
				DataPoint point = new DataPoint(data.getKey(), data.getMetric(x.getColName()), data.getMetric(y.getColName()), x);
				point.setAuthors(data.getAuthors());
				results.add(point);
			}
		}
		return results;
	}
}
