package com.testing123.downloader;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.joda.time.DateTime;

import com.testing123.controller.SQLConnector;

public class DBDeltaCalculator {

	public void convertLatestMetrics() throws SQLException {
		SQLConnector conn = new SQLConnector();
		Connection c = conn.getConn();
		Statement st = c.createStatement();
		st = c.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
		ResultSet rs = st.executeQuery("select * from allFileHistory3 ORDER BY file_key ASC, dbdate ASC;");
		try {
			rs.next();
			int prevId = rs.getInt("file_id");
			String prevKey = rs.getString("file_key");
			double prevComp = rs.getDouble("complexity");
			double prevDC = rs.getDouble("delta_complexity");
			int i = 0;
			while (rs.next()) {
				System.out.println(i++ + " " + prevKey);
				int currId = rs.getInt("file_id");
				String currKey = rs.getString("file_key");
				double currComp = rs.getDouble("complexity");
				double currDC = rs.getDouble("delta_complexity");
				if (rs.wasNull()) {
					if (currId == prevId && prevKey.equals(currKey)) {
						double deltaComp = currComp - prevComp;
						prevDC = deltaComp;
						rs.updateDouble("delta_complexity", deltaComp);
			            rs.updateRow();
			            System.out.println(deltaComp + " updated");
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
		conn.updateQuery("UPDATE allFileHistory3 SET delta_complexity = 0 WHERE dbdate = '" 
				+ DataSupportMain.getFrmtDate(new DateTime()) + "' AND delta_complexity IS NULL AND complexity = -1;");
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
