package com.testing123.vaadin;

// Constructs a JSON class

public class JsonClass {
    private int id;
    private String key;
    private String name;
    private String scope;
    private String qualifier;
    private String lname;
    private double lineOfCode;

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

    public void setLoc(double lineOfCode) {
        this.lineOfCode = lineOfCode;
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
        return lineOfCode;
    }
}
