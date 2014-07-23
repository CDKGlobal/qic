package com.testing123.vaadin;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * DataPoint represents an object that contains a file's information.  It allows this data
 * to be stored so it can be easily plotted onto a graph
 * 
 */
public class DataPoint {
    private String key;
    private double yValue;
    private double xValue;
	private int linesAdded;
    private int linesRemoved;
    private Set<String> authors;

    /**
     * Constructs a null DataPoint object that represents a file that does not exist.
     */
    public DataPoint() {
        this(null, -1, -1);
    }
    
    public DataPoint(String key, double xValue) {
    	this.key = key;
    	this.xValue = xValue;
    	this.authors = new HashSet<String>();
    }
    
    /**
     * Constructs a new DataPoint object with specified parameters
     * 
     * @param key the key of the file
     * @param complexity the complexity of the file
     * @param lineOfCode the number of lines of code in the file
     */
    public DataPoint(String key, double xValue, double yValue) {
        this(key, xValue);
        this.yValue = yValue;
    }
    
    public DataPoint(String key, int linesAdded, int linesRemoved){
    	this(key, linesAdded + linesRemoved);
    	this.linesAdded = linesAdded;
    	this.linesRemoved = linesRemoved;
    }

    public String getKey() {
        return key;
    }
    
    public void setKey(String key) {
        this.key = key;
    }

    public double getXValue() {
        return xValue;
    }

    public void setXValue(double xValue) {
        this.xValue = xValue;
    }

    public double getYValue() {
        return yValue;
    }
    
    public void setYValue(double yValue) {
        this.yValue = yValue;
    }
    
    public double getLinesAdded() {
		return linesAdded;
	}

	public void setLinesAdded(int linesAdded) {
		this.linesAdded = linesAdded;
	}

	public double getLinesRemoved() {
		return linesRemoved;
	}

	public void setLinesRemoved(int linesRemoved) {
		this.linesRemoved = linesRemoved;
	}
    
    public void addAuthor(String name) {
    	this.authors.add(name);
    }
    
    public void setAuthors(List<String> authors) {
    	this.authors.addAll(authors);
    }

    @Override
    public String toString() {
        return "[" + xValue + ", " + yValue + ", \"" + key + "\", \"" + formattedAuthors() + "\"]";

    }
    
    private String formattedAuthors() {
    	String authorsList = "Authors: ";
    	for (String name : authors) {
    		authorsList += name + " ";
    	}
    	return authorsList;
    }
}
