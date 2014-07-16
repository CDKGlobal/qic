package com.testing123.vaadin;

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
}
