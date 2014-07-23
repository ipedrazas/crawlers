package me.pedrazas.fhr;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;


@XmlRootElement(name = "Geocode")
@XmlAccessorType (XmlAccessType.FIELD)
public class Geocode {

	@XmlElement(name="Longitude")
	private float lon;
	@XmlElement(name="Latitude")
	private float lat;
	
	public float getLon() {
		return lon;
	}
	public void setLon(float longitude) {
		lon = longitude;
	}
	public float getLat() {
		return lat;
	}
	public void setLat(float latitude) {
		lat = latitude;
	}


	public String toString() {
	   return ToStringBuilder.reflectionToString(this);
	 }
	
}
