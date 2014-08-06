package com.testing123.vaadin;

public class ChangedData {
    private String sonarPath;
    private String date;
    private int churn;
    private String authors;

    public ChangedData(String sonarPath, String date, int churn, String authors) {
        this.sonarPath = sonarPath;
        this.date = date;
        this.churn = churn;
        this.authors = authors;
    }

    public String getSonarPath() {
        return sonarPath;
    }

    public void setSonarPath(String sonarPath) {
        this.sonarPath = sonarPath;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getChurn() {
        return churn;
    }

    public void setChurn(int churn) {
        this.churn = churn;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }


}

