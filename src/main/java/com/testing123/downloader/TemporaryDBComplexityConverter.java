package com.testing123.downloader;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.testing123.controller.SQLConnector;

public class TemporaryDBComplexityConverter {

	public static void main(String[] args) throws SQLException {
		SQLConnector conn = new SQLConnector();
		Connection c = conn.getConn();
		ResultSet rs = conn.basicQuery("select * from allFileHistory3 WHERE delta_complexity IS NULL ORDER BY file_key ASC, dbdate DESC;");
		try {
			int i = 0;
			while (rs.next()) {
				i++;
				String file_key = rs.getString("file_key");
				System.out.println(i + "/8668 " + file_key);
				
				SQLConnector conn2 = new SQLConnector();
				ResultSet rs2 = conn.basicQuery("SELECT * FROM allFileHistory3 WHERE file_key = '" + file_key + "' AND dbdate < '2014-08-03' ORDER BY dbdate DESC LIMIT 2;");
				double dbdatef;
				double dbdatei;
				if (rs2.next()) {
					dbdatef = rs2.getDouble("complexity");
				} else {
					continue;
				}
				if (rs2.next()) {
					dbdatei = rs2.getDouble("complexity");
				} else {
					continue;
				}
				double dC = dbdatef - dbdatei;
				System.out.println(dC);
				conn.updateQuery("UPDATE allFileHistory3 set delta_complexity = '" + dC + "' WHERE dbdate = '2014-08-02' AND file_key = '" + file_key + "';");
				conn2.close();
			}
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
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
