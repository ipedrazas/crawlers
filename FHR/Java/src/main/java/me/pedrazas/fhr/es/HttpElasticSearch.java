package me.pedrazas.fhr.es;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import com.google.gson.Gson;

public class HttpElasticSearch {

	public static EsResponse addByRest(String index, String type, String establishment) throws ClientProtocolException, IOException{
		StringBuilder sb = new StringBuilder();
		 
        HttpClient c = HttpClientBuilder.create().build();        
        HttpPost p = new HttpPost("http://localhost:9200/" + index + "/" + type);        
 
		p.setEntity(new StringEntity(establishment, ContentType.APPLICATION_JSON));
		HttpResponse r = c.execute(p);
		BufferedReader br = new BufferedReader(new InputStreamReader(r.getEntity().getContent()));
		String output;
		while ((output = br.readLine()) != null) {
			sb.append(output);
		}
		Gson gson = new Gson();
		return gson.fromJson(sb.toString(), EsResponse.class);
		
	}
	
	
	public static EsResponse addByRest(String index, String type, String json, int id) throws ClientProtocolException, IOException{
		StringBuilder sb = new StringBuilder();
		 
        HttpClient c = HttpClientBuilder.create().build();        
        HttpPut p = new HttpPut("http://localhost:9200/" + index + "/" + type + "/" + id);        
 
		p.setEntity(new StringEntity(json, ContentType.APPLICATION_JSON));
		HttpResponse r = c.execute(p);
		BufferedReader br = new BufferedReader(new InputStreamReader(r.getEntity().getContent()));
		String output;
		while ((output = br.readLine()) != null) {
			sb.append(output);
		}
		Gson gson = new Gson();
		return gson.fromJson(sb.toString(), EsResponse.class);
	}
}
