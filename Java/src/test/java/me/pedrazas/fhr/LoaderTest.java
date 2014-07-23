package me.pedrazas.fhr;

import java.io.File;
import java.util.List;

import junit.framework.TestCase;

public class LoaderTest extends TestCase {
	
	public void testReadFiles(){
		String path = "/home/ivan/workspace/crawlers/FHR/2014-07-21";
		String  index = "dummy";
		Loader l = new Loader(path, index);
		File[] fs = l.getFiles();
		assertNotNull(fs);
		String fPath = fs[0].getAbsolutePath();
		assertTrue(fPath.contains(path));
	}

	public void testGetEstablishmentsFromFile(){
		String path = "/home/ivan/workspace/crawlers/FHR/2014-07-21";
		String  index = "dummy";
		Loader l = new Loader(path, index);
		File f = new File("/home/ivan/workspace/crawlers/FHR/2014-07-21/OpenDataFiles\\FHRS561cy-GB.xml");
		List<Establishment> ests = l.getEstablishmentsFromFile(f);
		assertNotNull(ests);
		Establishment e = ests.get(0);
		// this will test the post process to calculate the 
		// outward of the postcode
		assertNotNull(e.getOutward());
	}
}
