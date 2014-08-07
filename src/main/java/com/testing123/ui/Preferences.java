package com.testing123.ui;

import com.testing123.dataObjects.ConvertDate;

public class Preferences {
	
//	public static final String DB_SERVER = "mysql://localhost:3306";		// used for VM
	public static final String DB_SERVER = "mysql://dc2pvpdc00059.vcac.dc2.dsghost.net:3306";

	public static final String DB_USER = "root";
	public static final String DB_PASS = "password";
	public static final String DB_NAME = "dataList4";
	public static final String PROJECT_TABLE = "projectList";
	public static final String STATIC_TABLE = "allFileList"; 
	public static final String DATA_TABLE = "allFileHistory";
	
	public static final ConvertDate DEFAULT_START_DATE = new ConvertDate("2014-07-29");
	public static final ConvertDate DEFAULT_END_DATE = new ConvertDate("2014-07-30");
	
	public static final String GRPAH_COLOR = "#033F8D";
	public static final String FILL_COLOR_POS = "#FF0000";
	public static final String FILL_COLOR_NEG = "#009933";
	
	
	public static final String[] FISHEYE_REPOS = new String[] {
		"Advertising.Perforce", "Core.Perforce", "Intelligence.Perforce", "OpenPlatform.Perforce", 
		"Owner.Perforce", "ProfessionalServices.Perforce", "ReleaseEngineering.Perforce",
		"Social.Perforce", "QIC.Perforce" };
}
