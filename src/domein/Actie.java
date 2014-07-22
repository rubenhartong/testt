package domein;

import java.util.Date;

public class Actie {
	private int id;
	private String eventID, user,  text,  actor, contact, user_vn, user_an;
	private Date date;
	public Actie(int id, String eventID, String user, Date date, String text,
			String actor, String contact, String user_vn, String user_an) {
		super();
		this.id = id;
		this.eventID = eventID;
		this.user = user;
		this.date = date;
		this.text = text;
		this.actor = actor;
		this.contact = contact;
		this.user_vn = user_vn;
		this.user_an = user_an;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEventID() {
		return eventID;
	}
	public void setEventID(String eventID) {
		this.eventID = eventID;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getActor() {
		return actor;
	}
	public void setActor(String actor) {
		this.actor = actor;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getUser_vn() {
		return user_vn;
	}
	public void setUser_vn(String user_vn) {
		this.user_vn = user_vn;
	}
	public String getUser_an() {
		return user_an;
	}
	public void setUser_an(String user_an) {
		this.user_an = user_an;
	}
	
	
}
