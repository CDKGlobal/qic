package com.testing123.dataObjects;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

public class ChangedDataTest {
    private static ChangedData cdData;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        cdData = new ChangedData(0, "2014-08-09", -1, "authors", true);
    }

    @Test
    public void testSetFile_IdAndGetFile_Id() {
        ChangedData cdData1 = new ChangedData(0, "2014-08-09", -1, null, true);
        cdData1.setFile_id(1);
        assertEquals(1, cdData1.getFile_id());
    }

    @Test
    public void testSetChurnAndGetChurn() {
        ChangedData cdData1 = new ChangedData(0, "2014-08-09", -1, null, true);
        cdData1.setChurn(1);
        assertEquals(1, cdData1.getChurn());
    }

    @Test
    public void testSetAuthorsAndGetAuthors() {
        ChangedData cdData1 = new ChangedData(0, "2014-08-09", -1, null, true);
        cdData1.setAuthors("weiyoud, authors");
        assertEquals("weiyoud, authors", cdData1.getAuthors());
    }

    @Test
    public void testSetDateAndGetDate() {
        ChangedData cdData1 = new ChangedData(0, "2014-08-09", -1, null, true);
        cdData1.setDate("2014-08-08");
        assertEquals("2014-08-08", cdData1.getDate());
    }

    @Test
    public void testToString() {
        assertEquals("ChangedData [file_id=0, date=2014-08-09, churn=-1, authors=authors]", cdData.toString());
    }

}
