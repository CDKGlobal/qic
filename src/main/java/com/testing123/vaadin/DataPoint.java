package com.testing123.vaadin;

/**
 * DataPoint represents an object that contains a file's information.  It allows this data
 * to be stored so it can be easily plotted onto a graph
 * 
 */
public class DataPoint {
    private String key;
    private double yValue;
    private double xValue;

    /**
     * Constructs a new DataPoint object with specified parameters
     * 
     * @param key the key of the file
     * @param complexity the complexity of the file
     * @param lineOfCode the number of lines of code in the file
     */
    public DataPoint(String key, double xValue, double yValue) {
        this.key = key;
        this.yValue = yValue;
        this.xValue = xValue;
    }

    /**
     * Constructs a null DataPoint object that represents a file that does not exist.
     */
    public DataPoint() {
        this(null, -1, -1);
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setYValue(double yValue) {
        this.yValue = yValue;
    }

    public void setXValue(double xValue) {
        this.xValue = xValue;
    }

    public String getKey() {
        return key;
    }

    public double getYValue() {
        return yValue;
    }

    public double getXValue() {
        return xValue;
    }

    @Override
    public String toString() {
        return "[" + xValue + ", " + yValue + ", \"" + key + "\"]";
    }
}
