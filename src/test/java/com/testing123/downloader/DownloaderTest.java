package com.testing123.downloader;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.testing123.vaadin.WebData;

public class DownloaderTest {
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        /*
         * dl = new Downloader();
         * File folder = new File("Tester");
         * folder.mkdir();
         */
    }

    @Test
    public void TestDownloadProjectsAndStoreInList() {
        new Downloader();
        List<WebData> projectList = Downloader.downloadProjectsAndStoreInList();
        assertEquals("com.onstation:uploader", projectList.get(0).getKey());
    }

}
