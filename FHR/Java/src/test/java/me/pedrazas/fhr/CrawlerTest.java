package me.pedrazas.fhr;

import java.io.File;

import junit.framework.TestCase;

public class CrawlerTest extends TestCase {

	
	public void testDownloadFile() {
		Crawler c = new Crawler();
		try {
			c.fetchLinks();
			String url = "http://ratings.food.gov.uk/OpenDataFiles\\FHRS562en-GB.xml";
			String path = "/data/crawlers/FHR/test";
			c.downloadFile(url, path);
			String filename = url.substring(url.lastIndexOf("/"));
			File f = new File(path + filename);
			assertTrue(f.exists());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
