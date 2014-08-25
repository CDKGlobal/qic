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
		ResultSet rs = st.executeQuery("SELECT * from allFileHistory3 WHERE dbdate > '" 
				+ new ConvertDate(new DateTime().minusDays(21)).getDBFormat() + "' ORDER BY file_id ASC, dbdate ASC;");
		try {
			rs.next();
			int prevId = rs.getInt("file_id");
			double prevComp = rs.getDouble("complexity");
			double prevIssues = rs.getDouble("issues");
			while (rs.next()) {
				int currId = rs.getInt("file_id");
				double currComp = rs.getDouble("complexity");
				if (rs.wasNull()) {
					if (currId == prevId) {
						double deltaComp = currComp - prevComp;
						rs.updateDouble("delta_complexity", deltaComp);
					}
				}
				double currIssues = rs.getDouble("issues");
				if (rs.wasNull()) {
					if (currId == prevId) {
						double deltaIssues = currIssues - prevIssues;
						rs.updateDouble("delta_issues", deltaIssues);
					}
				}
	            rs.updateRow();
				prevId = currId;
				prevComp = currComp;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		conn.updateQuery("UPDATE allFileHistory3 SET delta_complexity = 0 WHERE dbdate = '" 
				+ new ConvertDate().getDBFormat() + "' AND delta_complexity IS NULL AND complexity != -1;");
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
