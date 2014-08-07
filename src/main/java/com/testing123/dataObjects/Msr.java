package com.testing123.dataObjects;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * 
 * @author blumbeb
 *A small class that holds "MSR" the data that is retireved from SONAR
 */
public class Msr {
    private String key;
    private double val;


    @JsonProperty("frmt_val")
    private String frmtVal;

    private double branch_coverage;
    private double violation;

    public String getKey() {
        return key;
    }

    public double getVal() {
        return val;
    }

    public String getFrmtVal() {
        return frmtVal;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setVal(double val) {
        this.val = val;
    }

    public void setFrmtVal(String frmtVal) {
        this.frmtVal = frmtVal;
    }

    public double getBranch_coverage() {
        return branch_coverage;
    }

    public void setBranch_coverage(double branch_coverage) {
        this.branch_coverage = branch_coverage;
    }

    public double getViolation() {
        return violation;
    }

    public void setViolation(double violation) {
        this.violation = violation;
    }
}
