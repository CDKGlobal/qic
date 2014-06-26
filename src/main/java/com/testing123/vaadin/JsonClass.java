package com.testing123.vaadin;

// Constructs a JSON class

public class JsonClass {
	
	public static class msr{
		private String key;
		private double val;
		private String frmt_val;
		
		public String setKey(){return key;}
		public double setVal(){return val;}
		public String setFrmt_val(){return frmt_val;}
		
		public void getKey(String key){this.key = key;}
		public void getVal(double val){this.val = val;}
		public void getFrmt_val(String frmt_val){this.frmt_val = frmt_val;}
	}
	
	
    private int id;
    private String key;
    private String name;
    private String scope;
    private String qualifier;
    private String lname;

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
}
