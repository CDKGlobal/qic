package com.testing123.downloader;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.joda.time.DateTime;

import com.testing123.controller.SQLConnector;

public class DBDataCalculatorShort extends DBDeltaCalculator {
	
	@Override
	public void convertLatestMetrics() throws SQLException {
		SQLConnector conn = new SQLConnector();
		Connection c = conn.getConn();
		Statement st = c.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
		ResultSet rs = st.executeQuery("SELECT * from allFileHistory3 WHERE dbdate = '" 
				+ DataSupportMain.getFrmtDate(new DateTime()) + "' ORDER BY file_id ASC, dbdate ASC;");
		try {
			int i = 0;
			while (rs.next()) {
				SQLConnector conn2 = new SQLConnector();
				ResultSet rs2 = conn2.basicQuery("SELECT * from allFileHistory3 WHERE dbdate < '" 
					+ DataSupportMain.getFrmtDate(new DateTime()) + "' AND file_id = " + rs.getInt("file_id")
					+ " ORDER BY dbdate DESC LIMIT 1;");
				if (rs2.next()) {
					if (rs2.getString("file_id").equals(rs.getString("file_id"))) {
						double finalComp = rs.getDouble("complexity");
						if (rs.wasNull()) {
							continue;
						}
						double initialComp = rs2.getDouble("complexity");
						if (rs.wasNull()) {
							continue;
						}
						double deltaComp = finalComp - initialComp;
						rs.updateDouble("delta_complexity", deltaComp);
			            rs.updateRow();
			            System.out.println(deltaComp + " updated" + i++);
					}
				} else {
					rs.updateDouble("delta_complexity", 0.0);
		            rs.updateRow();
		            System.out.println(0.0 + " updated" + i++);
				}
				conn2.close();
			}

			st = c.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
	                ResultSet.CONCUR_UPDATABLE);
			st.executeUpdate("UPDATE allFileHistory3 SET delta_complexity = 0 WHERE dbdate = '" 
					+ DataSupportMain.getFrmtDate(new DateTime()) + "' AND delta_complexity IS NULL AND complexity != -1;");
			while (rs.next()) {
				rs.updateDouble("delta_complexity", rs.getDouble("complexity"));
	            rs.updateRow();
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
