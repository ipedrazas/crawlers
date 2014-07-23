package me.pedrazas.fhr;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.http.client.ClientProtocolException;


public class Loader {
	
	public static void main(String[] args){
		String index = "fhr";
		String path = "/home/ivan/workspace/crawlers/FHR/2014-07-21";
		Loader l = new Loader(path, index);
		System.out.println("Processing documents... be patient!");
		System.out.println((new Date()).toString());
		l.execute();
		System.out.println("The Import process is finished, hooray!");
		System.out.println((new Date()).toString());
	}
	
	

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	private String path;
	private String index;
	
	public Loader(String path, String index) {
		super();
		this.path = path;
		this.index = index;
	}
	
	public void execute(){
		File[] files = getFiles();
		for(File f : files){
			processFile(f);
		}
	}

	
	public File[] getFiles(){
		File[] list = null;
		GenericExtFilter filter = new GenericExtFilter(".xml");
		File dir = new File(this.path);
		if(dir.isDirectory()){
			list = dir.listFiles(filter);
		}
		return list;
	}
	
	
	public List<Establishment> getEstablishmentsFromFile(File file){
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(FHRSEstablishment.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			FHRSEstablishment ests = (FHRSEstablishment) jaxbUnmarshaller.unmarshal( file );
			return ests.getEstablishments();
		} catch (JAXBException ej) {
			ej.printStackTrace();
		}	
		return null;
	}
	
	public void processFile(File file){
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(FHRSEstablishment.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			FHRSEstablishment ests = (FHRSEstablishment) jaxbUnmarshaller.unmarshal( file );
			
			for (Establishment e : ests.getEstablishments()){
				 try {
						String esResponse = HttpElasticSearch.addByRest(this.index, Establishment.type, e.toJson().toString());
						System.out.println(esResponse);
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
	

	
	public String readFile(File fileName){
		
		BufferedReader br = null;
        String sCurrentLine = null;
        StringBuilder sb = new StringBuilder(); 
        try{
            br = new BufferedReader(
            new FileReader(fileName));
            while ((sCurrentLine = br.readLine()) != null){
                sb.append(sCurrentLine);
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        finally{
            try{
                if (br != null)
                br.close();
            } catch (IOException ex){
                ex.printStackTrace();
            }
        }
        
        return sb.toString();
	}

}
