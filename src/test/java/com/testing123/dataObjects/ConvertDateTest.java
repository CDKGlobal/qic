package com.testing123.dataObjects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class ConvertDateTest {
    private ConvertDate date;

    @Before
    public void setUp() throws Exception {
        date = new ConvertDate("2014-07-07");
    }

    @Test
    public void TestGetters(){
        assertEquals(2014, date.getYear());
        assertEquals(7, date.getMonth());
        assertEquals(7, date.getDay());
    }

    @Test
    public void TestAddsZeros(){
        ConvertDate date1 = new ConvertDate("2014-7-7");
        assertEquals(date,date1);
    }

    @Test
    public void TestToStringReturnsReadableFormat() {
        assertEquals("07/07/2014", date.toString());
    }

    @Test
    public void TestCompareDatesSameMonth() {
        ConvertDate date1 = new ConvertDate("2014-07-07");
        ConvertDate date2 = new ConvertDate("2014-07-08");
        assertTrue(date1.compareTo(date2) == -1);
    }

    @Test
    public void TestCompareDatesDifferentMonth() {
        ConvertDate date1 = new ConvertDate("2014-06-07");
        ConvertDate date2 = new ConvertDate("2014-07-03");
        assertTrue(date1.compareTo(date2) == -1);
    }

    @Test
    public void TestCompareDatesDifferentYear() {
        ConvertDate date1 = new ConvertDate("2013-12-12");
        ConvertDate date2 = new ConvertDate("2014-07-03");
        assertTrue(date1.compareTo(date2) == -1);
    }

    @Test
    public void TestEqualityMethodsWithSameDateRepresentation() {
        ConvertDate date1 = new ConvertDate("2014-07-07");
        ConvertDate date2 = new ConvertDate("2014-07-07");
        assertEquals(date1.hashCode(), date2.hashCode());
        assertTrue(date1.equals(date2));
    }

    @Test
    public void TestEqualsWithSameDateRepresentation() {
        ConvertDate date1 = new ConvertDate("2014-07-07");
        ConvertDate date2 = new ConvertDate("2014-07-07");
        assertEquals(date1.toString(), date2.toString());
        assertEquals(date1.toString(), date2.toString());
    }

    @Test
    public void TestGetDBFormatSingleDigitsMonthAndDay() {
        ConvertDate date = new ConvertDate("2014-7-7");
        assertEquals("2014-07-07", date.getDBFormat());
    }

    @Test
    public void TestGetDBFormatDoubleDigitsMonthAndDay() {
        ConvertDate date = new ConvertDate("2014-12-10");
        assertEquals("2014-12-10", date.getDBFormat());
    }
    
    @Test
    public void TestGetDBFormatPlusOneExample(){
    	 ConvertDate date = new ConvertDate("2014-12-10");
         assertEquals("2014-12-11", date.getDBFormatPlusOne());
    }
    
    @Test
    public void TestGetDBFormatPlusOneEndOfMonth(){
    	 ConvertDate date = new ConvertDate("2014-08-31");
         assertEquals("2014-08-32", date.getDBFormatPlusOne());
    }

}