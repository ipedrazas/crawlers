package me.pedrazas.fhr;

import java.io.File;
import java.util.List;

import javax.xml.bind.JAXBException;

import me.pedrazas.fhr.om.Establishment;
import junit.framework.TestCase;

public class EstablishmentServiceTest extends TestCase {

	public void testGetEstablishmentsFromFile() {		
		String path = "/data/crawlers/FHR/test/OpenDataFiles\\FHRS562en-GB.xml";		
		File f = new File(path);
		List<Establishment> ests = null;
		try {
			ests = EstablishmentService.getEstablishmentsFromFile(f);
			assertNotNull(ests);
			Establishment e = ests.get(0);
			assertNotNull(e);
		} catch (JAXBException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for(Establishment e: ests){
			System.out.println(e.toJson());
		}
	}

}
