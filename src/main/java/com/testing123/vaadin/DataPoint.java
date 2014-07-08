package com.testing123.vaadin;

/**
 * DataPoint represents an object that contains a file's information.  It allows this data
 * to be stored so it can be easily plotted onto a graph
 * 
 */
public class DataPoint {
    private String key;
    private double complexity;
    private double lineOfCode;

    /**
     * Constructs a new DataPoint object with specified parameters
     * 
     * @param key the key of the file
     * @param complexity the complexity of the file
     * @param lineOfCode the number of lines of code in the file
     */
    public DataPoint(String key, double lineOfCode, double complexity) {
        this.key = key;
        this.complexity = complexity;
        this.lineOfCode = lineOfCode;
    }

    /**
     * Constructs a null DataPoint object that represents a file that does not exist.
     */
    public DataPoint() {
        this(null, -1, -1);
    }

    /**
     * Sets the key of the file
     * 
     * @param key the string representing the key
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * Sets the complexity of the file
     * 
     * @param complexity a double value representing the file's complexity
     */
    public void setComplexity(double complexity) {
        this.complexity = complexity;
    }

    /**
     * Sets the number of lines of code of the file
     * 
     * @param lineOfCode a double value representing the number of lines of code
     */
    public void setLineOfCode(double lineOfCode) {
        this.lineOfCode = lineOfCode;
    }

    /**
     * Returns the key of the file
     * 
     * @return key
     */
    public String getKey() {
        return key;
    }

    /**
     * Returns the complexity of the file
     * 
     * @return complexity
     */
    public double getComplexity() {
        return complexity;
    }

    /**
     * Returns the number of lines of code in the file
     * 
     * @return lineOfCode
     */
    public double getLineOfCode() {
        return lineOfCode;
    }

    /**
     * Returns a string representation of this object.
     * 
     * @return a string representing this DataPoint
     */
    @Override
    public String toString() {
        return "[" + lineOfCode + ", " + complexity + ", \"" + key + "\"]";
    }
}
