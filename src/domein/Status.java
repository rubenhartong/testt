package domein;

public class Status {
	int bellen, mailen, hardcopy, status, prioriteit;
	String eventID, contact;
	public Status(String contact, int bellen, int mailen, int hardcopy,
			int status, int prioriteit, String eventID) {
		super();
		this.contact = contact;
		this.bellen = bellen;
		this.mailen = mailen;
		this.hardcopy = hardcopy;
		this.status = status;
		this.prioriteit = prioriteit;
		this.eventID = eventID;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public int getBellen() {
		return bellen;
	}
	public void setBellen(int bellen) {
		this.bellen = bellen;
	}
	public int getMailen() {
		return mailen;
	}
	public void setMailen(int mailen) {
		this.mailen = mailen;
	}
	public int getHardcopy() {
		return hardcopy;
	}
	public void setHardcopy(int hardcopy) {
		this.hardcopy = hardcopy;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getPrioriteit() {
		return prioriteit;
	}
	public void setPrioriteit(int prioriteit) {
		this.prioriteit = prioriteit;
	}
	public String getEventID() {
		return eventID;
	}
	public void setEventID(String eventID) {
		this.eventID = eventID;
	}
	
	
}
