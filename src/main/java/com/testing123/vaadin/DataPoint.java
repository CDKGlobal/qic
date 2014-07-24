package com.testing123.vaadin;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * DataPoint represents an object that contains a file's information. It allows
 * this data to be stored so it can be easily plotted onto a graph
 * 
 */
public class DataPoint implements Comparable<DataPoint> {
	private String key;
	private double yValue;
	private double xValue;
	private Set<String> authors;

	/**
	 * Constructs a null DataPoint object that represents a file that does not
	 * exist.
	 */
	public DataPoint() {
		this(null, -1, -1);
	}

	/**
	 * Constructs a new DataPoint object with specified parameters
	 * 
	 * @param key
	 *            the key of the file
	 * @param complexity
	 *            the complexity of the file
	 * @param lineOfCode
	 *            the number of lines of code in the file
	 */
	public DataPoint(String key, double xValue, double yValue) {
		this.key = key;
		this.xValue = xValue;
		this.authors = new HashSet<String>();
		this.yValue = yValue;
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
		StringBuffer buf = new StringBuffer();
		buf.append("Authors: ");
		for (String name : authors) {
			buf.append(name);
			buf.append(" ");
		}
		return buf.toString();
	}

	@Override
	public int compareTo(DataPoint other) {
		int compare = this.key.compareTo(other.key);
		if (compare == 0) {
			compare = (int) (this.xValue - other.xValue);
		}
		if (compare == 0) {
			compare = (int) (this.yValue - other.yValue);
		}
		return compare;
	}
	
	@Override
	  public boolean equals(Object obj) { 
		if(obj.getClass().equals(new DataPoint().getClass()))
			return this.compareTo((DataPoint)obj)==0;
		return false;
	}
	
}
