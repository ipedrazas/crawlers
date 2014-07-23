package me.pedrazas.fhr;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;


@XmlRootElement(name = "FHRSEstablishment")
@XmlAccessorType (XmlAccessType.FIELD)
public class FHRSEstablishment {
	
	
	private List<Establishment> establishmentCollection = null;

	@XmlElementWrapper(name="EstablishmentCollection")
	@XmlElementRef()
	public List<Establishment> getEstablishments() {
		return establishmentCollection;
	}

	public void setEstablishments(List<Establishment> establishments) {
		this.establishmentCollection = establishments;
	}
	
	public String toString() {
	   return ToStringBuilder.reflectionToString(this);
	 }
	

}
