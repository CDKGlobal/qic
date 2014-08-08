package com.testing123.dataObjects;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

public class ChangedDataTest {
    private static ChangedData cdData;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        cdData = new ChangedData(0, "2014-08-09", 0, "authors");
    }

    @Test
    public void testSetFile_IdAndGetFile_Id() {
        cdData.setFile_id(1);
        assertEquals(1, cdData.getFile_id());
    }

    @Test
    public void testSetChurnAndGetChurn() {
        cdData.setChurn(1);
        assertEquals(1, cdData.getChurn());
    }

    @Test
    public void testSetAuthorsAndGetAuthors() {
        cdData.setAuthors("weiyoud, authors");
        assertEquals("weiyoud, authors", cdData.getAuthors());
    }

    @Test
    public void testSetDateAndGetDate() {
        cdData.setDate("2014-08-08");
        assertEquals("2014-08-08", cdData.getDate());
    }

    @Test
    public void testToString() {
        assertEquals("ChangedData [file_id=1, date=2014-08-08, churn=1, authors=weiyoud, authors]", cdData.toString());
    }

}
