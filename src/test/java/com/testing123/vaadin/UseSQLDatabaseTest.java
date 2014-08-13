package com.testing123.vaadin;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.testing123.dataObjects.ConvertDate;
import com.testing123.dataObjects.ConvertProject;
import com.testing123.dataObjects.RepoAndDirData;
import com.testing123.ui.Preferences;

public class UseSQLDatabaseTest {

    private UseSQLDatabase UseSQLDatabaseFactory() {
        return new UseSQLDatabase(Preferences.DB_NAME_TEST);
    }

    @Test
    public void TestGetMapToID() {
        Map<String, Integer> map = new UseSQLDatabase().getMapToID(17271);

    }

    @Test
    public void TestGetAvailableProjects() {
        UseSQLDatabase useSQLDb = UseSQLDatabaseFactory();
        List<ConvertProject> listOfProject = useSQLDb.getAvailableProjects();
        assertEquals(58704, listOfProject.get(0).getID());
    }

    @Test
    public void TestGetAvailableAuthors() {
        UseSQLDatabase useSQLDb = UseSQLDatabaseFactory();
        List<String> listOfAuthors = useSQLDb.getAvailableAuthors();
        assertEquals("gatesb", listOfAuthors.get(0));
    }

    @Test
    public void TestGetAvailableDates() {
        UseSQLDatabase useSQLDb = UseSQLDatabaseFactory();
        List<ConvertDate> listOfDates = useSQLDb.getAvailableDates();
        assertEquals("08/07/2014", listOfDates.get(0).toString());
    }

    @Test
    public void TestGetProjectID() {
        UseSQLDatabase useSQLDb = UseSQLDatabaseFactory();
        int projectID = useSQLDb.getProjectID("com.vaadin:QIC2:src/main/java/com/testing123/ui/QicUI.java");
        assertEquals(73547, projectID);
    }

    @Test
    public void TestGetProjectPath() {
        UseSQLDatabase useSQLDb = UseSQLDatabaseFactory();
        String projectPath = useSQLDb.getProjectPath(73547);
        assertEquals("/Playpen/QIC2/", projectPath);
    }

    @Test
    public void TestGetRepoAndDirFromFileKey() {
        UseSQLDatabase useSQLDb = UseSQLDatabaseFactory();
        RepoAndDirData repoAndDir = useSQLDb.getRepoAndDirFromFileKey("com.vaadin:QIC2:src/main/java/com/testing123/ui/QicUI.java");
        assertEquals("Playpen.Perforce", repoAndDir.getRepositoryName());
        assertEquals("QIC2/", repoAndDir.getDirectoryName());

    }

}