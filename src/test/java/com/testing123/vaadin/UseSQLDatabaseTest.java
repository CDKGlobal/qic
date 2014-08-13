package com.testing123.vaadin;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.testing123.dataObjects.ConvertProject;

public class UseSQLDatabaseTest {

    @Test
    public void TestGetMapToID() {
        Map<String, Integer> map = new UseSQLDatabase().getMapToID(17271);

    }

    @Test
    public void TestGetAvailableProjects() {
        UseSQLDatabase useSQLDb = new UseSQLDatabase();
        List<ConvertProject> listOfProject = useSQLDb.getAvailableProjects();
        assertEquals(58704, listOfProject.get(0).getID());
    }

}
