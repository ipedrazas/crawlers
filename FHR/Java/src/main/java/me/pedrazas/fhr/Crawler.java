package me.pedrazas.fhr;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Crawler {
	
	private String path;
	private String index = "fhr";
	
	private List<String> extractedLinks;
	private List<File> filesToProcess;
	
	
	public Crawler(String path, String index) {
		super();
		this.path = path;
		this.index = index;
		this.extractedLinks = new ArrayList<String>();
		this.filesToProcess = new ArrayList<File>();
		File f = new File(path);
		if(!f.exists()){
			f.mkdirs();
		}	
	}
	
	public Crawler() {
		super();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");		
		String dataFolder = formatter.format(new Date());
		this.extractedLinks = new ArrayList<String>();
		this.filesToProcess = new ArrayList<File>();
		this.path = "/data/crawlers/FHR/" + dataFolder;
		File f = new File(path);
		if(!f.exists()){
			f.mkdirs();
		}	
	}
	
	public void fetchLinks() throws IOException{
		Document doc = Jsoup.connect("http://ratings.food.gov.uk/open-data/en-GB").get();
		Elements links = doc.select("a[href]");
		for (Element link : links) {
			// we only want the XMLs. It turns out data is in Welsh and English...
			// we just want the english version
			if(link.attr("abs:href").endsWith("en-GB.xml")){				
				this.extractedLinks.add(link.attr("abs:href"));	            
			}
        }
	}
	
	/**
	 * this method is used when you execute the process in different steps.
	 */
	public void readFilesFromPath(){
	
		File[] list = null;
		GenericExtFilter filter = new GenericExtFilter(".xml");
		File dir = new File(this.path);
		if(dir.isDirectory()){
			list = dir.listFiles(filter);
		}
		this.filesToProcess = Arrays.asList(list);
	}
	
	public List<File> downloadFilesFromLinks(){
		for(String link : this.extractedLinks){
			try {
				this.filesToProcess.add(downloadFile(link, this.path));				
			} catch (Exception e) {
				// if something goes wrong downloading a file, we just carry on
				System.out.println("Error downloading " + link);
			}
		}
		return this.filesToProcess;
	}
	
	public void indexEstablishmentFromFiles(){
		for(File file : this.filesToProcess){
			EstablishmentService.indexFromFile(file, this.index);
		}
	}
	
	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public List<String> getExtractedLinks() {
		return extractedLinks;
	}

	public void setExtractedLinks(List<String> extractedLinks) {
		this.extractedLinks = extractedLinks;
	}

	public List<File> getFilesToProcess() {
		return filesToProcess;
	}

	public void setFilesToProcess(List<File> filesToProcess) {
		this.filesToProcess = filesToProcess;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	public File  downloadFile(String url, String path) throws Exception{
		
		String filename = url.substring(url.lastIndexOf("/"));
		File file = new File(path + filename);
		FileUtils.copyURLToFile(new URL(url), file);
		return file;
	}
	
}
