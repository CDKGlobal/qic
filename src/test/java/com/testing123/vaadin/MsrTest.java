package com.testing123.vaadin;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

public class MsrTest {
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {

    }

    @Test
    public void testSetKeyAndGetKey() {
        Msr msr = new Msr();
        msr.setKey("");
        assertEquals("", msr.getKey());
    }

    @Test
    public void testSetFrmt_valAndGetFrmt_val() {
        Msr msr = new Msr();
        msr.setFrmtVal("0.0");
        assertEquals("0.0", msr.getFrmtVal());
    }

    @Test
    public void testSetValAndGetVal() {
        Msr msr = new Msr();
        msr.setVal(0.0);
        assertEquals(0, (int) msr.getVal());
    }
}
