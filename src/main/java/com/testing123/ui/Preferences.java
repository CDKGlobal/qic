package com.testing123.ui;

import com.testing123.controller.SQLConnector;
import com.testing123.vaadin.ConvertDate;

public class Preferences {
	
	public static final String DB_SERVER = "mysql://dc2pvpdc00059.vcac.dc2.dsghost.net:3306";
	public static final String DB_USER = SQLConnector.getUser();
	public static final String DB_PASS = "password";
	public static final String DB_NAME = "dataList";

	// Set Default Start Date in UIState
	// Set Default End Date in UIState
	
	public static final String GRPAH_COLOR = "#033F8D";
	public static final String FILL_COLOR = "#033F8D";
	
	public static final String[] FISHEYE_REPOS = new String[] {
		"Advertising.Perforce", "Core.Perforce", "Intelligence.Perforce", "OpenPlatform.Perforce", 
		"Owner.Perforce", "ProfessionalServices.Perforce", "ReleaseEngineering.Perforce",
		"Social.Perforce", "QIC.Perforce" };
}
