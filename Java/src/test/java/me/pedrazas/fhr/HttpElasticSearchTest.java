package me.pedrazas.fhr;

import java.io.IOException;
import java.util.Random;

import junit.framework.TestCase;
import me.pedrazas.fhr.es.EsResponse;
import me.pedrazas.fhr.es.HttpElasticSearch;
import me.pedrazas.fhr.om.Establishment;

public class HttpElasticSearchTest extends TestCase {

	public void testAddByRestStringStringStringInt() {
		
		String json = "{\"id\":516473,\"LocalAuthorityBusinessID\":0,\"BusinessName\":\"Woodlake Park Golf Club\",\"BusinessType\":\"Pub/bar/nightclub\",\"BusinessTypeID\":7843,\"AddressLine2\":\"Glascoed\",\"AddressLine3\":\"Nr Usk\",\"AddressLine4\":\"Monmouthshire\",\"PostCode\":\"NP4 0TE\",\"RatingValue\":3,\"RatingKey\":\"fhrs_3_en-GB\",\"RatingDate\":\"2013-01-28\",\"LocalAuthorityCode\":562,\"LocalAuthorityName\":\"Monmouthshire\",\"LocalAuthorityWebSite\":\"http://www.monmouthshire.gov.uk\",\"LocalAuthorityEmailAddress\":\"environmentalhealth@monmouthshire.gov.uk\",\"SchemeType\":\"FHRS\",\"outward\":\"NP4\",\"areacode\":\"NP\",\"Scores\":{\"Hygiene\":10,\"Structural\":10,\"ConfidenceInManagement\":5},\"Geocode\":{\"lon\":-2.955944,\"lat\":51.70104}}";
		Random rand = new Random();
		int  n = rand.nextInt(500) + 1;
		String index = "test_" + n;
		try {
			EsResponse esResponse = HttpElasticSearch.addByRest(index, Establishment.type, json, 5164723);
			System.out.println(esResponse);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
