package me.pedrazas.fhr.om;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import me.pedrazas.fhr.PostCodeUtils;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

@XmlRootElement(name = "EstablishmentDetail")
@XmlAccessorType (XmlAccessType.FIELD)
public class Establishment {

	@XmlElement(name="FHRSID")
	private int id;
	private int LocalAuthorityBusinessID;
	private String BusinessName;
	private String BusinessType;
	private int BusinessTypeID;
	private String AddressLine1;
	private String AddressLine2;
	private String AddressLine3;
	private String AddressLine4;
	private String PostCode;
	private int RatingValue;
	private String RatingKey;
	private String RatingDate;
	private int LocalAuthorityCode;
	private String LocalAuthorityName;
	private String LocalAuthorityWebSite;
	private String LocalAuthorityEmailAddress;
	private String SchemeType;
	private String outward;
	private String areacode;
	
	
	public String getAreacode() {
		return areacode;
	}

	public void setAreacode(String areacode) {
		this.areacode = areacode;
	}

	public static final String type = "establishment"; 
	
	public String getOutward() {
		if(this.outward == null){
			String[] pcs = this.PostCode.split(" ");
			if(pcs != null && pcs.length > 0){
				this.outward = pcs[0];
			}
		}
		return this.outward;
	}

	public void setOutward(String outward) {
		this.outward = outward;
	}

	private Scores Scores;
	private Geocode Geocode;
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getLocalAuthorityBusinessID() {
		return LocalAuthorityBusinessID;
	}
	
	public void setLocalAuthorityBusinessID(int localAuthorityBusinessID) {
		LocalAuthorityBusinessID = localAuthorityBusinessID;
	}
	public String getBusinessName() {
		return BusinessName;
	}
	public void setBusinessName(String businessName) {
		BusinessName = businessName;
	}
	public String getBusinessType() {
		return BusinessType;
	}

	public void setBusinessType(String businessType) {
		BusinessType = businessType;
	}
	public int getBusinessTypeID() {
		return BusinessTypeID;
	}

	public void setBusinessTypeID(int businessTypeID) {
		BusinessTypeID = businessTypeID;
	}
	public String getAddressLine1() {
		return AddressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		AddressLine1 = addressLine1;
	}
	public String getAddressLine2() {
		return AddressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		AddressLine2 = addressLine2;
	}
	public String getAddressLine3() {
		return AddressLine3;
	}

	public void setAddressLine3(String addressLine3) {
		AddressLine3 = addressLine3;
	}
	public String getAddressLine4() {
		return AddressLine4;
	}
	
	public void setAddressLine4(String addressLine4) {
		AddressLine4 = addressLine4;
	}
	public String getPostCode() {
		return PostCode;
	}
	
	public void setPostCode(String postCode) {
		PostCode = postCode;
		String[] pcs = postCode.split(" ");
		if(pcs != null && pcs.length > 0){
			this.outward = pcs[0];
		}
		
	}
	public int getRatingValue() {
		return RatingValue;
	}
	
	public void setRatingValue(int ratingValue) {
		RatingValue = ratingValue;
	}
	public String getRatingKey() {
		return RatingKey;
	}
	
	public void setRatingKey(String ratingKey) {
		RatingKey = ratingKey;
	}
	public String getRatingDate() {
		return RatingDate;
	}
	
	public void setRatingDate(String ratingDate) {
		RatingDate = ratingDate;
	}
	public int getLocalAuthorityCode() {
		return LocalAuthorityCode;
	}
	
	public void setLocalAuthorityCode(int localAuthorityCode) {
		LocalAuthorityCode = localAuthorityCode;
	}
	public String getLocalAuthorityName() {
		return LocalAuthorityName;
	}
	
	public void setLocalAuthorityName(String localAuthorityName) {
		LocalAuthorityName = localAuthorityName;
	}
	public String getLocalAuthorityWebSite() {
		return LocalAuthorityWebSite;
	}
	
	public void setLocalAuthorityWebSite(String localAuthorityWebSite) {
		LocalAuthorityWebSite = localAuthorityWebSite;
	}
	public String getLocalAuthorityEmailAddress() {
		return LocalAuthorityEmailAddress;
	}
	
	public void setLocalAuthorityEmailAddress(String localAuthorityEmailAddress) {
		LocalAuthorityEmailAddress = localAuthorityEmailAddress;
	}
	
	public Scores getScores() {
		return Scores;
	}

	public void setScores(Scores scores) {
		Scores = scores;
	}

	public String getSchemeType() {
		return SchemeType;
	}
	
	public void setSchemeType(String schemeType) {
		SchemeType = schemeType;
	}

	
	public String toString() {
	   return ToStringBuilder.reflectionToString(this);
	 }
	
	public JsonElement toJson(){
		if(this.PostCode != null){
			this.outward = PostCodeUtils.getOutward(this.PostCode);
			this.areacode = PostCodeUtils.getAreaCode(this.PostCode);
		}
		if(this.RatingDate.isEmpty()){
			this.RatingDate = null;
		}
		Gson gson = new Gson();
		JsonElement element = gson.toJsonTree(this, new TypeToken<Establishment>() {}.getType());
		return element;
	}

	public Geocode getGeocode() {
		return Geocode;
	}

	public void setGeocode(Geocode geocode) {
		Geocode = geocode;
	}
	
	
/*	
  <EstablishmentDetail>
	<FHRSID>52236</FHRSID>
	<LocalAuthorityBusinessID>11776</LocalAuthorityBusinessID>
	<BusinessName>Zinc</BusinessName>
	<BusinessType>Restaurant/Cafe/Canteen</BusinessType>
	<BusinessTypeID>1</BusinessTypeID>
	<AddressLine1>Abersoch Cafe</AddressLine1>
	<AddressLine2>Lôn Pen Cei</AddressLine2>
	<AddressLine3>Abersoch</AddressLine3>
	<AddressLine4>Pwllheli, Gwynedd</AddressLine4>
	<PostCode>LL53 7AW</PostCode>
	<RatingValue>5</RatingValue>
	<RatingKey>fhrs_5_en-GB</RatingKey>
	<RatingDate>2013-11-05</RatingDate>
	<LocalAuthorityCode>554</LocalAuthorityCode>
	<LocalAuthorityName>Gwynedd</LocalAuthorityName>
	<LocalAuthorityWebSite>http://www.gwynedd.gov.uk</LocalAuthorityWebSite>
	<LocalAuthorityEmailAddress>BwydIechydaDiogelwch@gwynedd.gov.uk</LocalAuthorityEmailAddress>
	<Scores>
	<Hygiene>5</Hygiene>
	<Structural>5</Structural>
	<ConfidenceInManagement>5</ConfidenceInManagement>
	</Scores>
	<SchemeType>FHRS</SchemeType>
	<Geocode>
	<Longitude>-4.50444900000000</Longitude>
	<Latitude>52.82492300000000</Latitude>
	</Geocode>
	</EstablishmentDetail>
	
	*/
}
