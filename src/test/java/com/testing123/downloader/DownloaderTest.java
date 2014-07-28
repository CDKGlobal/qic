package com.testing123.downloader;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class DownloaderTest {
	private static Downloader dl;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		dl = new Downloader();
		File folder = new File("Tester");
		folder.mkdir();
	}
	
	@AfterClass
	public static void cleanUpAfterClass() throws Exception {
		File f = new File("Tester");
		delete(f);
	}
	
	public static void delete(File f) throws Exception {
		if (f.isDirectory()) {
			for (File c : f.listFiles())
				delete(c);
		}
		if (!f.delete()) {
		    throw new FileNotFoundException("Failed to delete file: " + f);
		}
	}
}
