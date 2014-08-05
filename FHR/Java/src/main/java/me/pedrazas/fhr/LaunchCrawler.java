package me.pedrazas.fhr;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.xml.bind.JAXBException;

import me.pedrazas.fhr.es.EsResponse;
import me.pedrazas.fhr.es.HttpElasticSearch;
import me.pedrazas.fhr.om.Establishment;

public class LaunchCrawler {

	 private final static Logger LOGGER = Logger.getLogger(LaunchCrawler.class
		      .getName());


	public static void main(String[] args) {
		
		Crawler fhr = new Crawler();
		try {
			LOGGER.info("Fetching links ...");
			fhr.fetchLinks(); 										// parse the url and extract links
			LOGGER.info("Downloading links ...");
			List<File> files = fhr.downloadFilesFromLinks(); 		// download files,
			LOGGER.info(files.size() + " files downloaded");
			for(File file : files){ 								// parse the xml file to extract list of Establishments
				LOGGER.info("Processing " + file.getCanonicalPath());
				List<Establishment> establishments = 
						EstablishmentService.getEstablishmentsFromFile(file);
				for(Establishment e : establishments){ 				// Index establishment
					LOGGER.info("Indexing establishment: " + e.getId());
					EsResponse resp = HttpElasticSearch.addByRest(fhr.getIndex(), 
							 Establishment.type, e.toJson().toString(), e.getId());
					if(!resp.isCreated()){
						LOGGER.info("[ERROR] -- " + resp.toString());
					}
				}
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JAXBException e) {
			e.printStackTrace();
		}

	}

}
