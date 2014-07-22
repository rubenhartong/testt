package domein;

import java.util.Date;

public class Evenement {
	private int locatieID, programmaID;
	private String eventID, naam, subtitel, contactPersoon, organisator, type;
	private Date datum, updated;
	private boolean removed;
	
	public Evenement(String eID, String nm, String subT, Date dat, int lID, int pID, String cp, String org, String ty, boolean rem, Date up){
		eventID = eID;
		naam = nm;
		subtitel = subT;
		datum = dat;
		locatieID = lID;
		programmaID = pID;
		contactPersoon = cp;
		organisator = org;
		type = ty;
		updated = up;
		removed = rem;
	}

	public String getSubtitel() {
		return subtitel;
	}

	public void setSubtitel(String subtitel) {
		this.subtitel = subtitel;
	}

	public String getEventID() {
		return eventID;
	}

	public void setEventID(String eventID) {
		this.eventID = eventID;
	}

	public int getLocatieID() {
		return locatieID;
	}

	public void setLocatieID(int locatieID) {
		this.locatieID = locatieID;
	}

	public int getProgrammaID() {
		return programmaID;
	}

	public void setProgrammaID(int programmaID) {
		this.programmaID = programmaID;
	}

	public String getNaam() {
		return naam;
	}

	public void setNaam(String naam) {
		this.naam = naam;
	}
	public Date getDatum() {
		return datum;
	} 

	public void setDatum(Date datum) {
		this.datum = datum;
	}

	public String getContactPersoon() {
		return contactPersoon;
	}

	public void setContactPersoon(String contactPersoon) {
		this.contactPersoon = contactPersoon;
	}

	public String getOrganisator() {
		return organisator;
	}

	public void setOrganisator(String organisator) {
		this.organisator = organisator;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public boolean getRemoved() {
		return removed;
	}
	
	public void setRemoved(boolean removed) {
		this.removed = removed;
	}
	
	public Date getUpdated() {
		return updated;
	} 

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

}
