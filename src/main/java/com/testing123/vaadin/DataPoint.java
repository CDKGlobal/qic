package com.testing123.vaadin;

public class DataPoint {
    private String key;
    private double complexity;
    private double lineOfCode;

    public DataPoint(String key, double complexity, double lineOfCode) {
        this.key = key;
        this.complexity = complexity;
        this.lineOfCode = lineOfCode;
    }

    public DataPoint() {
        this(null, -1, -1);
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setComplexity(double complexity) {
        this.complexity = complexity;
    }

    public void setLineOfCode(double lineOfCode) {
        this.lineOfCode = lineOfCode;
    }

    public String getKey() {
        return key;
    }

    public double getComplexity() {
        return complexity;
    }

    public double getLineOfCode() {
        return lineOfCode;
    }

    @Override
    public String toString() {
        return "[" + lineOfCode + ", " + complexity + ", \"" + key + " \"]";
    }

}
