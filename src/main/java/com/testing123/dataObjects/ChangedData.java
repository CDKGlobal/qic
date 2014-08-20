package com.testing123.dataObjects;

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

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((authors == null) ? 0 : authors.hashCode());
		result = prime * result + churn;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + file_id;
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
		ChangedData other = (ChangedData) obj;
		if (authors == null) {
			if (other.authors != null)
				return false;
		} else if (!authors.equals(other.authors))
			return false;
		if (churn != other.churn)
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (file_id != other.file_id)
			return false;
		return true;
	}

	public void setAuthors(String authors) {
        this.authors = authors;
    }


}

