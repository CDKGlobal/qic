package com.testing123.dataObjects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ConvertProjectTest {


    @Test
    public void testGetName() {
        ConvertProject cp = new ConvertProject("name", "", -1, "");
        assertEquals("name", cp.getName());
    }

    @Test
    public void testToString() {
        ConvertProject cp = new ConvertProject("name", "", -1, "");
        assertEquals("name", cp.toString());
    }

    @Test
    public void testGetKey() {
        ConvertProject cp = new ConvertProject("", "project_key", -1, "");
        assertEquals("project_key", cp.getKey());
    }

    @Test
    public void testGetId() {
        ConvertProject cp = new ConvertProject("", "", 1, "");
        assertEquals(1, cp.getID());
    }

    @Test
    public void testGetPath() {
        ConvertProject cp = new ConvertProject("", "", -1, "path");
        assertEquals("path", cp.getPath());
    }

    @Test
    public void TestEqualityMethodsWithSameCPRepresentation() {
        ConvertProject cp1 = new ConvertProject("", "", -1, "");
        ConvertProject cp2 = new ConvertProject("", "", -1, "");
        assertEquals(cp1.hashCode(), cp2.hashCode());
        assertTrue(cp1.equals(cp2));
    }

    @Test
    public void TestNullCaseInEqualsMethods() {
        ConvertProject cp1 = new ConvertProject("", "", -1, "");
        assertFalse(cp1.equals(null));
    }

    @Test
    public void TestNotSameObjectCaseInEqualsMethods() {
        ConvertProject cp1 = new ConvertProject("", "", -1, "");
        String cp2 = "";
        assertFalse(cp1.equals(cp2));
    }

    @Test
    public void TestNotSameIdCaseInEqualsMethods() {
        ConvertProject cp1 = new ConvertProject("", "", -1, "");
        ConvertProject cp2 = new ConvertProject("", "", 0, "");
        assertFalse(cp1.equals(cp2));
    }

    @Test
    public void TestNotAllAreNullCaseInEqualsMethods() {
        ConvertProject cp1 = new ConvertProject("", null, -1, "");
        ConvertProject cp2 = new ConvertProject("", "key", -1, "");
        assertFalse(cp1.equals(cp2));
    }
    /*
     * @Test
     * public void TestNotSameKeyCaseInEqualsMethods() {
     * ConvertProject cp1 = new ConvertProject("", "the key", -1, "");
     * ConvertProject cp2 = new ConvertProject("", "key", -1, "");
     * assertFalse(cp1.equals(cp2));
     * }
     */


}
