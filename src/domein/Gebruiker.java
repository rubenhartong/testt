package domein;

public class Gebruiker {
	private String id;
	private String email, voornaam, achternaam, bedrijf, telefoonnummer, wachtwoord;
	boolean bool;
	
	public Gebruiker(String i, String em, String vn, String an, String be, String tel){
		id = i;
		email = em;
		voornaam = vn;
		achternaam = an;
		bedrijf = be;
		telefoonnummer = tel;
	}

	public Gebruiker(String email2, String voornaam2, String achternaam2, String bedrijf2, String telefoonnummer2, String wachtwoord2, boolean bool2) {
		email = email2;
		voornaam = voornaam2;
		achternaam = achternaam2;
		bedrijf = bedrijf2;
		telefoonnummer = telefoonnummer2;
		wachtwoord = wachtwoord2;
		bool = bool2;
	}

	public String getId(){
		return id;
	}
	public void setId(String id){
		this.id = id;
	}
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getBedrijf() {
		return bedrijf;
	}

	public void setBedrijf(String bedrijf) {
		this.bedrijf = bedrijf;
	}

	public String getTelefoonnummer() {
		return telefoonnummer;
	}

	public void setTelefoonnummer(String telefoonnummer) {
		this.telefoonnummer = telefoonnummer;
	}

	public String getWachtwoord() {
		return wachtwoord;
	}

	public void setWachtwoord(String wachtwoord) {
		this.wachtwoord = wachtwoord;
	}

}
