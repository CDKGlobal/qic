package com.testing123.dataObjects;

import java.util.List;

public class ChangedData {
    @Override
    public String toString() {
        return "ChangedData [file_id=" + file_id + ", date=" + date + ", churn=" + churn + ", authors=" + authors + "]";
    }

    private int file_id;
    private String date;
    private int churn;
    private String authors;


    public ChangedData(int file_id, String date, int churn, String authors) {
        this.file_id = file_id;
        this.date = date;
        this.churn = churn;
        this.authors = authors;
    }

    public int getFile_id() {
        return file_id;
    }

    public void setFile_id(int file_id) {
        this.file_id = file_id;
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

