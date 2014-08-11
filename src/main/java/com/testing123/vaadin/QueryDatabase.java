package com.testing123.vaadin;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.testing123.controller.SQLConnector;
import com.testing123.dataObjects.ConvertDate;
import com.testing123.dataObjects.ConvertProject;
import com.testing123.dataObjects.QueryData;

public class QueryDatabase {
	private SQLConnector conn;
	
	public QueryDatabase() {
		this.conn = new SQLConnector();
	}
	
	public Set<QueryData> getDataSet(ConvertDate startDate, ConvertDate endDate, Set<ConvertProject> projects) {
		ResultSet results = null;
		try {
			results = conn.basicQuery("SELECT MAX(allFileHistory3.file_key) as file_key, static.complexity AS complexity, "
					+ "static.ncloc AS ncloc, allFileList.name, COALESCE(SUM(delta_complexity), 0) AS delta_complexity, "
					+ "COALESCE(GROUP_CONCAT(authors, \"\"), \"\") as authors, COALESCE(SUM(churn), 0) AS churn "
				+ "FROM allFileHistory3 "
				+ "JOIN allFileList ON allFileList.file_id = allFileHistory3.file_id "
				+ "INNER JOIN (SELECT allFileHistory3.file_id, complexity, ncloc "
					+ "FROM allFileHistory3 "
					+ "JOIN allFileList ON allFileList.file_id = allFileHistory3.file_id "
					+ "INNER JOIN (SELECT file_id, MAX(dbdate) AS maxdate FROM allFileHistory3 "
						+ "WHERE dbdate <= '" + endDate.getDBFormat() + "' AND dbdate > '" + 
							startDate.getDBFormat() + "' GROUP BY file_id) dates "
						+ "ON allFileHistory3.file_id = dates.file_id "
						+ "WHERE allFileList.project_id IN " + projectIDSet(projects) + " AND qualifier = 'CLA' AND "
						+ "allFileHistory3.dbdate = dates.maxdate) AS static ON "
						+ "allFileHistory3.file_id = static.file_id WHERE qualifier = 'CLA' AND "
						+ "allFileList.project_id IN " + projectIDSet(projects) + " AND dbdate <= "
						+ "'" + endDate.getDBFormat() + "' AND dbdate > '" + startDate.getDBFormat() + "' "
						+ "GROUP BY allFileHistory3.file_id;");
			
//			results = conn.basicQuery("SELECT allFileHistory3.file_key, static.complexity AS complexity, "
//					+ "static.ncloc AS ncloc, allFileList.name, COALESCE(SUM(delta_complexity), 0) AS "
//				+ "delta_complexity, COALESCE(GROUP_CONCAT(authors, \"\"), \"\") as authors, COALESCE(SUM(churn), 0) "
//				+ "AS churn "
//			+ "FROM allFileHistory3 "
//			+ "JOIN allFileList ON allFileList.file_id = allFileHistory3.file_id "
//			+ "INNER JOIN (SELECT allFileHistory3.file_id, complexity, ncloc FROM allFileHistory3 "
//			+ "JOIN allFileList ON allFileList.file_id = allFileHistory3.file_id WHERE project_id IN "
//				+ projectIDSet(projects) + " "
//			+ "ORDER BY allFileHistory3.dbdate DESC) AS static "
//			+ "WHERE qualifier = 'CLA' AND allFileList.project_id IN " + projectIDSet(projects) + " AND dbdate <= '" 
//				+ endDate.getDBFormat() + "' "
//				+ "AND dbdate > '" + startDate.getDBFormat() + "' AND static.file_id = allFileHistory3.file_id "
//			+ "GROUP BY allFileHistory3.file_id;");
			if (results == null) {
				results = conn
						.basicQuery("SELECT a1.file_id, a1.file_key, afl.name, ncloc, complexity, delta_complexity, authors FROM "
								+ "allFileHistory3 a1 "
								+ "JOIN allFileList afl ON afl.file_id = a1.file_id WHERE qualifier = 'CLA' "
								+ "AND afl.project_id IN " + projectIDSet(projects)
								+ " AND a1.dbdate = (SELECT MAX(a2.dbdate) FROM allFileHistory3 a2 "
								+ "WHERE a1.file_id = a2.file_id) GROUP BY a1.file_id;");
			}
			if (results == null) {
				return new HashSet<QueryData>();
			}
		} catch (Exception e) {
			System.out.println("Exception thrown in Query Database");
			return new HashSet<QueryData>();
		}
		return processDBResults(results);
	}
	
	private Set<QueryData> processDBResults(ResultSet rs) {
		Set<QueryData> processed = new HashSet<QueryData>();
		try {
			int counts = 0;
			while (rs.next()) {
				counts++;
				QueryData data = populateQueryData(rs);
				if (data != null) {
					processed.add(data);
				}
			}
			System.out.println(counts + " processed");
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return processed;
	}

	private QueryData populateQueryData(ResultSet rs) throws SQLException {
		QueryData data = new QueryData();
		Set<String> columns = new HashSet<String>(Arrays.asList(new String[]{"ncloc", "churn", 
				"complexity", "delta_complexity", /*"issues", "delta_issues", "coverage", "delta_coverage"*/}));
		try {
			data.setKey(rs.getString("file_key"));
			data.setName(rs.getString("name"));
			extractAuthors(data, rs);
//			data.setIssues(rs.getDouble("issues"));
//			data.setDeltaIssues(rs.getDouble("deltaIssues"));
//			data.setCoverage(rs.getDouble("coverage"));
//			data.setDeltaCoverage(rs.getDouble("deltaCoverage"));
		} catch (Exception e) {
			System.out.println("Error populating string fields");
			return null;
		}
		for (String column : columns) {
			populateSingleMetric(data, column, rs);
		}
		return data;
	}
	
	private void extractAuthors(QueryData data, ResultSet rs) throws Exception {
		List<String> authorSet = new ArrayList<String>();
		String authors = rs.getString("authors").replace("[", ",").replace("]", ",");
		String[] authorArray = authors.split(",");
		for (String author : authorArray) {
			author = author.trim();
			if (author.length() > 0) {
				authorSet.add(author);
			}
		}
		data.setAuthors(authorSet);
	}

	private void populateSingleMetric(QueryData data, String metric, ResultSet rs) {
		try {
			data.setMetric(metric, rs.getDouble(metric));
		} catch (Exception e) {
			System.out.println("Exception thrown when populating QueryData: " + metric);
		}
	}

	/**
	 * Converts the projects into a database readable format
	 * 
	 * @param projects
	 * @return a String representation of the set of projects
	 */
	private String projectIDSet(Set<ConvertProject> projects) {
		List<ConvertProject> projectsList = new ArrayList<ConvertProject>(projects);
		if (projects.size() == 0) {
			return "()";
		}
		String set = "(" + projectsList.get(0).getID();
		for (int i = 1; i < projectsList.size(); i++) {
			set = set + "," + projectsList.get(i).getID();
		}
		set = set + ")";
		return set;
	}
}
