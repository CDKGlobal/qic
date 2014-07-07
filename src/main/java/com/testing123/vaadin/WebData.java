package com.testing123.vaadin;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * A class that will store all of the data from the API
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class WebData {
	
	@JsonIgnoreProperties({ "scope", "lname","lang","creationDate"})
    private int id;
    private String key;
    private String name;
    private String qualifier;
    private String date;
    private List<Msr> msr;

    public void setId(int id) {
        this.id = id;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQualifier(String qualifier) {
        this.qualifier = qualifier;
    }

    public void setDate(String date) {this.date = date;}
    
    public void setMsr(List<Msr> msr) {
        this.msr = msr;
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

    public String getQualifier() {
        return qualifier;
    }

    public String getDate() {return date;}
    
    public List<Msr> getMsr() {
    	return msr;
    }
}
