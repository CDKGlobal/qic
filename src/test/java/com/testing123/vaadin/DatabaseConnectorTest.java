package com.testing123.vaadin;

import org.junit.BeforeClass;
import org.junit.Test;

import com.testing123.downloader.DatabaseConnector;

public class DatabaseConnectorTest {
    private DatabaseConnector dbConnector;

    @BeforeClass
    public void setUpBeforeClass() throws Exception {
        dbConnector = new DatabaseConnector();
    }

    @Test
    public void testGetConnection() {

    }

    @Test
    public void testCreateDbAndLoadTable() {

    }

}
