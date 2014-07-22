package domein;

public class Locatie {
	private int locatieID;
	private String adres, plaats, postcode, telefoonnummer, website;
	
	public Locatie(int lID, String ad, String pl, String pc, String tel, String web){
		locatieID = lID;
		adres = ad;
		plaats = pl;
		postcode = pc;
		telefoonnummer = tel;
		website = web;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public int getLocatieID() {
		return locatieID;
	}

	public void setLocatieID(int locatieID) {
		this.locatieID = locatieID;
	}

	public String getAdres() {
		return adres;
	}

	public void setAdres(String adres) {
		this.adres = adres;
	}

	public String getPlaats() {
		return plaats;
	}

	public void setPlaats(String plaats) {
		this.plaats = plaats;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getTelefoonnummer() {
		return telefoonnummer;
	}

	public void setTelefoonnummer(String telefoonnummer) {
		this.telefoonnummer = telefoonnummer;
	}

}
