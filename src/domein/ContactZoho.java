package domein;

public class ContactZoho {
	String voornaam, achternaam, mail, functie, functie_domain, functie_level, bedrijf, telefoon, mobiel, contactpersoon;
	String id;
	int bellen, mailen, hardcopy, prioriteit, status;
	boolean optOut;
	public ContactZoho(String voornaam, String achternaam, String mail,
			String functie, String functie_domain, String functie_level,
			String bedrijf, String telefoon, String mobiel,
			String contactpersoon, String id, int bellen, int mailen,
			int hardcopy, int prioriteit, int status, boolean optOut) {
		super();
		this.voornaam = voornaam;
		this.achternaam = achternaam;
		this.mail = mail;
		this.functie = functie;
		this.functie_domain = functie_domain;
		this.functie_level = functie_level;
		this.bedrijf = bedrijf;
		this.telefoon = telefoon;
		this.mobiel = mobiel;
		this.contactpersoon = contactpersoon;
		this.id = id;
		this.bellen = bellen;
		this.mailen = mailen;
		this.hardcopy = hardcopy;
		this.prioriteit = prioriteit;
		this.status = status;
		this.optOut = optOut;
	}
	public String getVoornaam() {
		return voornaam;
	}
	public void setVoornaam(String voornaam) {
		this.voornaam = voornaam;
	}
	public String getAchternaam() {
		return achternaam;
	}
	public void setAchternaam(String achternaam) {
		this.achternaam = achternaam;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getFunctie() {
		return functie;
	}
	public void setFunctie(String functie) {
		this.functie = functie;
	}
	public String getFunctie_domain() {
		return functie_domain;
	}
	public void setFunctie_domain(String functie_domain) {
		this.functie_domain = functie_domain;
	}
	public String getFunctie_level() {
		return functie_level;
	}
	public void setFunctie_level(String functie_level) {
		this.functie_level = functie_level;
	}
	public String getBedrijf() {
		return bedrijf;
	}
	public void setBedrijf(String bedrijf) {
		this.bedrijf = bedrijf;
	}
	public String getTelefoon() {
		return telefoon;
	}
	public void setTelefoon(String telefoon) {
		this.telefoon = telefoon;
	}
	public String getMobiel() {
		return mobiel;
	}
	public void setMobiel(String mobiel) {
		this.mobiel = mobiel;
	}
	public String getContactpersoon() {
		return contactpersoon;
	}
	public void setContactpersoon(String contactpersoon) {
		this.contactpersoon = contactpersoon;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public int getPrioriteit() {
		return prioriteit;
	}
	public void setPrioriteit(int prioriteit) {
		this.prioriteit = prioriteit;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public boolean isOptOut() {
		return optOut;
	}
	public void setOptOut(boolean optOut) {
		this.optOut = optOut;
	}
	
	
	
	
}
