package com.testing123.vaadin;

// Constructs a JSON class

public class JsonClass {
	public static class msr{
		private String key;
		private double val;
		private String frmt_val;
		
		public String getKey(){return key;}
		public double getVal(){return val;}
		public String getFrmt_val(){return frmt_val;}
	}
	
	
    private int id;
    private String key;
    private String name;
    private String scope;
    private String qualifier;
    private String lname;
    private double val;

    public void setId(int id) {
        this.id = id;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public void setQualifier(String qualifier) {
        this.qualifier = qualifier;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public void setLoc(double val) {
        this.val = val;
    }

    public int getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public String getScope() {
        return scope;
    }

    public String getQualifier() {
        return qualifier;
    }

    public String getLname() {
        return lname;

    }

    public double geLoc() {
        return val;
    }
}
