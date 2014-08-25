package com.testing123.ui;

public class Preferences {
	
	/**
	 * DB and VM location
	 */
//	public static final String DB_SERVER = "mysql://localhost:3306";		// used for VM
    public static final String DB_SERVER = "mysql://dc2pvpdc00059.vcac.dc2.dsghost.net:3306";

    //	public static final String URL = "localhost:8080/QIC2";
    public static final String URL = "qic.cobalt.com:8080";
    
    /**
     * Sonar and Fisheye Instance
     */
    public static final String FISHEYE_HOME = "http://fisheye.cobalt.com";
    public static final String SONAR_HOME = "http://sonar.cobalt.com";
    
    /**
     * Local backup setting
     */
    public static final String ARCHIVE_BACKUP = "Archives/projectList/";
    
    /**
     * DB settings
     */
    public static final String DB_USER = "root";
    public static final String DB_PASS = "password";
    public static final String DB_NAME = "dataList4";
    public static final String DB_NAME_TEST = "TestDatabase";
    public static final String PROJECT_TABLE = "projectList";
    public static final String STATIC_TABLE = "allFileList";
    public static final String DATA_TABLE = "allFileHistory3";

    /**
     * Sonar and Fisheye settings
     */
    public static final String SONAR_METRICS = "ncloc,complexity,violations";
    
    /**
     * Graph settings
     */
    public static final String GRPAH_COLOR = "#033F8D";
    public static final String FILL_COLOR_POS = "#FF0000";
    public static final String FILL_COLOR_NEG = "#009933";

    public static final String[] FISHEYE_REPOS = new String[] {
        "Advertising.Perforce", "Core.Perforce", "Intelligence.Perforce", "OpenPlatform.Perforce",
        "Owner.Perforce", "ProfessionalServices.Perforce", "ReleaseEngineering.Perforce",
        "Social.Perforce", "QIC.Perforce" };
    
    public static final String HELP_DESCRIPTION = 
    	"<p>The main function of QIC is to display the changes in Churn and Complexity "
    	+ "over two dates selected dates.  It can also show the number of non-commented "
    	+ "lines of code during a given date (specified in 'End Date').  For each file, it "
		+ "will plot the chosen value on the x axis, and its final complexity on the y-axis. </p>"
		+ "<p>You can filter by projects or authors to show only results specified by the "
		+ "filter</p>"
		+ "<p>Data points are clickable and will show the diff between the two revisions between"
		+ "the specified dates</p>";
}
