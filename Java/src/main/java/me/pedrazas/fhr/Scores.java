package me.pedrazas.fhr;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;

@XmlRootElement(name = "Scores")
@XmlAccessorType (XmlAccessType.FIELD)
public class Scores {

	private int Hygiene;
	private int Structural;
	private int ConfidenceInManagement;
	
	public int getHygiene() {
		return Hygiene;
	}
	public void setHygiene(int hygiene) {
		Hygiene = hygiene;
	}
	public int getStructural() {
		return Structural;
	}
	public void setStructural(int structural) {
		Structural = structural;
	}
	public int getConfidenceInManagement() {
		return ConfidenceInManagement;
	}
	public void setConfidenceInManagement(int confidenceInManagement) {
		ConfidenceInManagement = confidenceInManagement;
	}


	public String toString() {
	   return ToStringBuilder.reflectionToString(this);
	 }
	
}
