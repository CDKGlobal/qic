package com.testing123.downloader;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.testing123.controller.SQLConnector;

public class TemporaryDBConverter {
	
	public static void main(String[] args) throws SQLException {
		SQLConnector conn = new SQLConnector();
		ResultSet rs = conn.basicQuery("SELECT file_key, date, dbDate, complexity FROM allFileHistory WHERE NOT date LIKE CONCAT(dbDate, 'T%') ORDER BY file_id ASC");
		try {
			int i = 0;
			while (rs.next()) {
				i++;
				String key = rs.getString("file_key");
				System.out.println(i + " " + key);
				String date = rs.getString("date");
				String[] frmtDate = date.split("T");
				System.out.println(frmtDate[0]);
				//double comp = rs.getDouble("complexity");
				conn.updateQuery("UPDATE allFileHistory set dbdate = '" + frmtDate[0] + "' WHERE file_key = '" + key + "' AND date LIKE '" + frmtDate[0] + "T%';");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
