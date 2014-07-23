package me.pedrazas.fhr;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

public class HttpElasticSearch {

	public static String addByRest(String index, String type, String json) throws ClientProtocolException, IOException{
		StringBuilder sb = new StringBuilder();
		 
        HttpClient c = HttpClientBuilder.create().build();        
        HttpPost p = new HttpPost("http://localhost:9200/" + index + "/" + type);        
 
		p.setEntity(new StringEntity(json, ContentType.APPLICATION_JSON));
		HttpResponse r = c.execute(p);
		BufferedReader br = new BufferedReader(new InputStreamReader(r.getEntity().getContent()));
		String output;
		while ((output = br.readLine()) != null) {
			sb.append(output);
		}
		return sb.toString();
	}
}
