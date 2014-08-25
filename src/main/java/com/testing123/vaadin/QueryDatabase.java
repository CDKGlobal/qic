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
import com.testing123.ui.Preferences;

public class QueryDatabase {
    private static final Set<String> METRIC_COLUMNS = new HashSet<String>(Arrays.asList(new String[]{"ncloc", "churn",
                    "complexity", "delta_complexity"/* "issues", "delta_issues", "coverage", "delta_coverage" */}));

    private final SQLConnector conn;

    public QueryDatabase() {
        conn = new SQLConnector();
    }

    public Set<QueryData> getDataSet(ConvertDate startDate, ConvertDate endDate, Set<ConvertProject> projects,
                    boolean singleMetric) {
        ResultSet results = null;
        try {
            if (!singleMetric) {
                results = queryDeltaMetrics(startDate, endDate, projects);
            }
            if (singleMetric || results == null) {
                results = queryForStaticMetrics(projects, endDate);
            }
        } catch (Exception e) {
            System.out.println("Exception thrown in Query Database");
        }
        return results == null ? new HashSet<QueryData>() : processDBResults(results);
    }

    public Set<QueryData> getSignleMetricDataSet(ConvertDate startDate, ConvertDate endDate, Set<ConvertProject> projects) {
        ResultSet results = getSingleMetricResultSet(projects, endDate);
        return results == null ? new HashSet<QueryData>() : processDBResults(results);
    }

    private ResultSet getSingleMetricResultSet(Set<ConvertProject> projects, ConvertDate endDate) {
        ResultSet results = null;
        try {
            results = queryForStaticMetrics(projects, endDate);
        } catch (Exception e) {
            System.out.println("Exception thrown in Query Database");
        }
        return results;
    }

    private ResultSet queryForStaticMetrics(Set<ConvertProject> projects, ConvertDate endDate) {
        return conn
                        .basicQuery("SELECT a1.file_id, a1.file_key, afl.name, ncloc, complexity, delta_complexity, authors FROM "
                                        + "allFileHistory3 a1 "
                                        + "JOIN " + Preferences.STATIC_TABLE + " afl ON afl.file_id = a1.file_id WHERE qualifier != 'UTS' "
                                        + "AND afl.project_id IN " + projectIDSet(projects)
                                        + " AND a1.dbdate = (SELECT MAX(a2.dbdate) FROM allFileHistory3 a2 "
                                        + "WHERE a1.file_id = a2.file_id AND a2.dbdate <= '" + endDate.getDBFormat() 
                                        + "' ) GROUP BY a1.file_id;");
    }

    private Set<QueryData> processDBResults(ResultSet rs) {
        Set<QueryData> processed = new HashSet<QueryData>();
        try {
            populateAllItems(rs, processed);
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return processed;
    }

    private void populateAllItems(ResultSet rs, Set<QueryData> processed) throws SQLException {
        int counts = 0;
        while (rs.next()) {
            counts++;
            populateItemIfNoException(rs, processed);
        }
        System.out.println(counts + " processed");
    }

    private void populateItemIfNoException(ResultSet rs, Set<QueryData> processed) {
        try {
            processed.add(populateQueryData(rs));
        } catch (Exception e) {

        }
    }

    private QueryData populateQueryData(ResultSet rs) throws Exception {
        QueryData data = new QueryData();
        populateStringValues(rs, data);
        populateMetrics(rs, data);
        return data;
    }

    private void populateMetrics(ResultSet rs, QueryData data) {
        for (String column : METRIC_COLUMNS) {
            populateSingleMetric(data, column, rs);
        }
    }

    private void populateStringValues(ResultSet rs, QueryData data) throws SQLException, Exception {
        data.setKey(rs.getString("file_key"));
        data.setName(rs.getString("name"));
        data.setAuthors(extractAuthors(rs));
    }

    private List<String>  extractAuthors(ResultSet rs) throws Exception {
        List<String> authorSet = new ArrayList<String>();
        String authors = rs.getString("authors");
        if (!rs.wasNull()) {
            parseAndExtractAuthors(authorSet, authors);
        }
        return authorSet;
    }

    private void parseAndExtractAuthors(List<String> authorSet, String authors) {
        authors = authors.replace("[", ",").replace("]", ",");
        String[] authorArray = authors.split(",");
        for (String author : authorArray) {
            author = author.trim();
            if (author.length() > 0) {
                authorSet.add(author);
            }
        }
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
        StringBuilder set = new StringBuilder();
        set.append("(");
        set.append(projectsList.get(0).getID());
        for (int i = 1; i < projectsList.size(); i++) {
            set.append(",");
            set.append(projectsList.get(i).getID());
        }
        set.append(")");
        return set.toString();
    }


    private ResultSet queryDeltaMetrics(ConvertDate startDate, ConvertDate endDate, Set<ConvertProject> projects) {
        return conn.basicQuery("SELECT MAX(allFileHistory3.file_key) as file_key, static.complexity AS complexity, "
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
                        + "WHERE allFileList.project_id IN " + projectIDSet(projects) + " AND qualifier != 'UTS' AND "
                        + "allFileHistory3.dbdate = dates.maxdate) AS static ON "
                        + "allFileHistory3.file_id = static.file_id WHERE qualifier != 'UTS' AND "
                        + "allFileList.project_id IN " + projectIDSet(projects) + " AND dbdate <= "
                        + "'" + endDate.getDBFormat() + "' AND dbdate > '" + startDate.getDBFormat() + "' "
                        + "GROUP BY allFileHistory3.file_id;");
    }
}
