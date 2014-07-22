package domein;

public class Entry {
	int id;
	private String first_name, last_name, company, function, telephone, email, contact_link, eventID; 
	boolean checked;
	public Entry(int id, String first_name, String last_name, String company,
			String function, String telephone, String email,
			String contact_link, String eventID, boolean checked) {
		super();
		this.id = id;
		this.first_name = first_name;
		this.last_name = last_name;
		this.company = company;
		this.function = function;
		this.telephone = telephone;
		this.email = email;
		this.contact_link = contact_link;
		this.eventID = eventID;
		this.checked = checked;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getFunction() {
		return function;
	}
	public void setFunction(String function) {
		this.function = function;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getContact_link() {
		return contact_link;
	}
	public void setContact_link(String contact_link) {
		this.contact_link = contact_link;
	}
	public String getEventID() {
		return eventID;
	}
	public void setEventID(String eventID) {
		this.eventID = eventID;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	
	
}
