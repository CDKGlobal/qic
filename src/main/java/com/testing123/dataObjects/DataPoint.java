package com.testing123.dataObjects;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.testing123.controller.UIState.XAxis;

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
	private XAxis xAxis;
	/**
	 * Constructs a null DataPoint object that represents a file that does not
	 * exist.
	 */
	public DataPoint() {
		this(null, -1, -1, XAxis.DELTA_COMPLEXITY);
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
		this(key, xValue, yValue, XAxis.DELTA_COMPLEXITY);
	}

	public DataPoint(String key, double xValue, double yValue, XAxis x) {
		this.key = key;
		this.xValue = xValue;
		this.authors = new HashSet<String>();
		this.yValue = yValue;
		this.xAxis = x;
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
	
	public Set<String> getAuthors() {
		return new HashSet<String>(authors);
	}

	@Override
	public String toString() {
		return "[" + xValue + ", " + yValue + ", \"" + key + "\", \"" + formattedAuthors() + "\",\"" + getXLable() + "\"]";

	}

	private String getXLable() {
		if(xAxis.equals(XAxis.DELTA_COMPLEXITY)){
			return "Changed Complexity: ";
		}else if(xAxis.equals(XAxis.DELTA_LINESOFCODE)){
			return "Modified Lines Of Code: ";
		}else if(xAxis.equals(XAxis.LINESOFCODE)){
			return "Total Non-Comment Lines Of Code: ";
		}
		return "Value: ";
	}

	private String formattedAuthors() {
		StringBuffer buf = new StringBuffer();
		if (!authors.isEmpty()) {
			buf.append("Authors: ");
			for (String name : authors) {
				buf.append(name);
				buf.append(" ");
			}
		}
		return buf.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((authors == null) ? 0 : authors.hashCode());
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		long temp;
		temp = Double.doubleToLongBits(xValue);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(yValue);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DataPoint other = (DataPoint) obj;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		if (Double.doubleToLongBits(xValue) != Double.doubleToLongBits(other.xValue))
			return false;
		if (Double.doubleToLongBits(yValue) != Double.doubleToLongBits(other.yValue))
			return false;
		return true;
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

}
