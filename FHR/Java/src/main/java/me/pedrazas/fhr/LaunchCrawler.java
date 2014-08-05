package me.pedrazas.fhr;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBException;

import me.pedrazas.fhr.es.EsResponse;
import me.pedrazas.fhr.es.HttpElasticSearch;
import me.pedrazas.fhr.om.Establishment;

import org.apache.commons.io.FileUtils;

public class LaunchCrawler {

	private static void Log(StringBuilder sb, String message){
		sb.append(message).append("\n");
		System.out.println(message);
	}
	public static void main(String[] args) {
		
		StringBuilder sb = new StringBuilder();
		
		Crawler fhr = new Crawler();
		try {
			Log(sb, "Fetching links ...");
			fhr.fetchLinks(); 										// parse the url and extract links
			Log(sb, "Downloading links ...");
			List<File> files = fhr.downloadFilesFromLinks(); 		// download files,
			Log(sb, files.size() + " files downloaded");
			for(File file : files){ 								// parse the xml file to extract list of Establishments
				Log(sb, "Processing " + file.getCanonicalPath());
				List<Establishment> establishments = 
						EstablishmentService.getEstablishmentsFromFile(file);
				for(Establishment e : establishments){ 				// Index establishment
					Log(sb, "Indexing establishment: " + e.getId());
					EsResponse resp = HttpElasticSearch.addByRest(fhr.getIndex(), 
							 Establishment.type, e.toJson().toString(), e.getId());
					if(!resp.isCreated()){
						Log(sb, "[ERROR] -- " + resp.toString());
					}
				}
				
			}
			FileUtils.writeStringToFile(new File("crawler.fhr.log."), sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JAXBException e) {
			e.printStackTrace();
		}

	}

}
