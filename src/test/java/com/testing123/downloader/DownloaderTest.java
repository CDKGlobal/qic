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
    public void TestDownloadAndStoreInList() {
        List<WebData> projectList = new Downloader().downloadAndStoreInList("", 0, "?");
        for (WebData project : projectList) {
            System.out.println(project.getKey());
        }
        assertEquals("com.onstation:uploader", projectList.get(0).getKey());
    }

    /*
     * @Test
     * public void TestParseData() {
     * try {
     * List<WebData> fileList =
     * Downloader.downloadFilesAndStoreInList(Downloader.downloadProjectsAndStoreInList().get(0), 2);
     * assertEquals(6369, fileList.get(0).getId());
     * } catch (Exception e) {
     * e.printStackTrace();
     * }
     * }
     */

}
