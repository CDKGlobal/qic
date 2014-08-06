package com.testing123.dataObjects;

import java.util.List;

public class ChangedData {
    private String file_key;
    private String date;
    private int churn;
    private String authors;


    public ChangedData(String file_key, String date, int churn, String authors) {
        this.file_key = file_key;
        this.date = date;
        this.churn = churn;
        this.authors = authors;
    }

    public String getFile_key() {
        return file_key;
    }

    public void setFile_key(String file_key) {
        this.file_key = file_key;
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

    public String getAuthors(List<String> authorsList) {

        StringBuffer buff = new StringBuffer();
        for (String auth : authorsList) {
            buff.append(",");
            buff.append(auth);
        }
        return buff.toString();
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }


}

