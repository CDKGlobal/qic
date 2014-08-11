package com.testing123.dataObjects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;


public class CacheTagTest {
    private static CacheTag ctag;

    private static Set<ConvertProject> emptyProjects;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        emptyProjects = new HashSet<ConvertProject>();
        emptyProjects.add(new ConvertProject("", "", -1, ""));

        ctag = new CacheTag(new ConvertDate("0000-01-01"), new ConvertDate("0000-01-01"), emptyProjects, false);
    }

    @Test
    public void testGetStartDate() {
        assertEquals("01/01/0000", ctag.getStartDate().toString());
    }

    @Test
    public void testGetEndDate() {
        assertEquals("01/01/0000", ctag.getEndDate().toString());
    }

    @Test
    public void testGetProject() {
        assertEquals("[]", ctag.getProjects().toString());
    }

    @Test
    public void testTrueCaseOfEquals() {
        CacheTag o = new CacheTag(new ConvertDate("0000-01-01"), new ConvertDate("0000-01-01"), emptyProjects, false);
        assertTrue(ctag.equals(o));
    }

    @Test
    public void testFalseCaseOfEquals() {
        CacheTag o = new CacheTag(new ConvertDate("0000-01-02"), new ConvertDate("0000-01-01"), emptyProjects, false);
        assertFalse(ctag.equals(o));
    }

}
