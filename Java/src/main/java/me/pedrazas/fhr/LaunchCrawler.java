package me.pedrazas.fhr;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBException;

import me.pedrazas.fhr.es.HttpElasticSearch;
import me.pedrazas.fhr.om.Establishment;

public class LaunchCrawler {

	public static void main(String[] args) {
		
		
		Crawler fhr = new Crawler();
		try {
			fhr.fetchLinks(); 										// parse the url and extract links
			List<File> files = fhr.downloadFilesFromLinks(); 		// download files,
			for(File file : files){ 								// parse the xml file to extract list of Establishments
				List<Establishment> establishments = 
						EstablishmentService.getEstablishmentsFromFile(file);
				for(Establishment e : establishments){ 				// Index establishment
					HttpElasticSearch.addByRest(fhr.getIndex(), 
							Establishment.type, e.toJson().toString(), e.getId());
				}
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JAXBException e) {
			e.printStackTrace();
		}

	}

}
