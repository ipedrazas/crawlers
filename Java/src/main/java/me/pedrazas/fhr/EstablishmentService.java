package me.pedrazas.fhr;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import me.pedrazas.fhr.es.EsResponse;
import me.pedrazas.fhr.es.HttpElasticSearch;
import me.pedrazas.fhr.om.Establishment;
import me.pedrazas.fhr.om.FHRSEstablishment;

import org.apache.http.client.ClientProtocolException;

public class EstablishmentService {
	
	public static List<Establishment> getEstablishmentsFromFile(File file) throws JAXBException{
		JAXBContext jaxbContext = JAXBContext.newInstance(FHRSEstablishment.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		FHRSEstablishment ests = (FHRSEstablishment) jaxbUnmarshaller.unmarshal( file );
		return ests.getEstablishments();
	}

	public static void indexFromFile(File file, String ESIndex){
		try {			
			List<Establishment> ests = getEstablishmentsFromFile(file);
			for (Establishment e : ests){
				 try {
					 EsResponse esResponse = HttpElasticSearch.addByRest(ESIndex, Establishment.type, e.toJson().toString(), e.getId());
					 if(!esResponse.isCreated()){
						 System.out.println("Error indexing establishment ID: " + e.getId());
					 }
					} catch (ClientProtocolException ex) {
						ex.printStackTrace();
					} catch (IOException ex) {
						ex.printStackTrace();
					}
			}
		} catch (JAXBException ej) {
			ej.printStackTrace();
		}
		
	}
}
