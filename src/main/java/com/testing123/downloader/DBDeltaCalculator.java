package com.testing123.downloader;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.joda.time.DateTime;

import com.testing123.controller.SQLConnector;
import com.testing123.dataObjects.ConvertDate;

public class DBDeltaCalculator {

	public void convertLatestMetrics() throws SQLException {
		SQLConnector conn = new SQLConnector();
		Connection c = conn.getConn();
		Statement st = c.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
		String query = "SELECT * from allFileHistory3 WHERE dbdate > '" 
				+ new ConvertDate(new DateTime().minusDays(21)).getDBFormat() + "' ORDER BY file_id ASC, dbdate ASC;";
		System.out.println(query);
		ResultSet rs = st.executeQuery(query);
		int i = 0;
		try {
			rs.next();
			int prevId = rs.getInt("file_id");
			String prevKey = rs.getString("file_key");
			double prevComp = rs.getDouble("complexity");
			double prevDC = rs.getDouble("delta_complexity");
			while (rs.next()) {
				System.out.println(i++ + " " + prevKey);
				int currId = rs.getInt("file_id");
				String currKey = rs.getString("file_key");
				double currComp = rs.getDouble("complexity");
				double currDC = rs.getDouble("delta_complexity");
				if (rs.wasNull()) {
					if (currId == prevId) {
						double deltaComp = currComp - prevComp;
						prevDC = deltaComp;
						rs.updateDouble("delta_complexity", deltaComp);
			            rs.updateRow();
			            System.out.println(deltaComp + " updated");
			            System.out.println(rs.getString("dbdate") + " updated");
			            System.out.println(rs.getString("file_key") + " updated");
					}
				} else {
					prevDC = currDC;
				}
				prevId = currId;
				prevKey = currKey;
				prevComp = currComp;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		st = c.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
		query = "SELECT * from allFileHistory3 WHERE dbdate > '" 
				+ new ConvertDate(new DateTime().minusDays(5)).getDBFormat() + "' ORDER BY file_id ASC, dbdate ASC;";
		System.out.println(query);
		rs = st.executeQuery(query);
		i = 0;
		try {
			rs.next();
			int prevId = rs.getInt("file_id");
			String prevKey = rs.getString("file_key");
			double prevIssues = rs.getDouble("issues");
			double prevDI = rs.getDouble("delta_issues");
			while (rs.next()) {
				System.out.println(i++ + " " + prevKey);
				int currId = rs.getInt("file_id");
				String currKey = rs.getString("file_key");
				double currIssues = rs.getDouble("issues");
				double currDC = rs.getDouble("delta_issues");
				if (rs.wasNull()) {
					if (currId == prevId) {
						double deltaComp = currIssues - prevIssues;
						prevDI = deltaComp;
						rs.updateDouble("delta_issues", deltaComp);
			            rs.updateRow();
			            System.out.println(deltaComp + " updated");
			            System.out.println(rs.getString("dbdate") + " updated");
			            System.out.println(rs.getString("file_key") + " updated");
					}
				} else {
					prevDI = currDC;
				}
				prevId = currId;
				prevKey = currKey;
				prevIssues = currIssues;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		conn.updateQuery("UPDATE allFileHistory3 SET delta_complexity = 0 WHERE dbdate = '" 
				+ new ConvertDate().getDBFormat() + "' AND delta_complexity IS NULL AND complexity != -1;");
		conn.updateQuery("UPDATE allFileHistory3 SET delta_issues = 0 WHERE dbdate = '" 
				+ new ConvertDate().getDBFormat() + "' AND delta_issues IS NULL;");
		conn.close();
	}

	public static String frmtStr(int a) {
		if (a < 10) {
			return "0" + a;
		} else {
			return a + "";
		}
	}
}
