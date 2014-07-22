package model;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import domein.Gebruiker;
import domein.Locatie;

public class NewEventIO extends DbAbstract {

	public NewEventIO() {
		super();
	}
	
	public String addUser(Gebruiker u) throws NoSuchAlgorithmException, UnsupportedEncodingException{
		String m = "Gebruiker toegevoegd!";
		String wachtwoord =GebruikersIO.SHA1(u.getWachtwoord());
		
		String query = "INSERT INTO gebruiker (email, voornaam, achternaam, bedrijf, telefoonnummer, wachtwoord)";
		query += "VALUES('" + u.getEmail() + "', '" + u.getVoornaam() + "', '" + u.getAchternaam()
				+ "', '" + u.getBedrijf() + "', '" + u.getTelefoonnummer() + "', '"
				+ wachtwoord + "')";

		try {
			super.addUpdateRecord(query);
			super.closeConnection();

		} catch (Exception e) {
				m = ("Het toevoegen van een user is mislukt");
		}

		return m;	
	}
	
	public String addLocation(Locatie l){
		String m = "De locatie is toegevoegd";
		String query = "INSERT INTO locatie (adres, plaats, postcode, telefoonnummer, locatiecol, website) ";
		query += "VALUES('" + l.getAdres() + "',  '" + l.getPlaats() + "', '" + l.getPostcode() + "', '" + l.getTelefoonnummer() + "', NULL, '" + l.getWebsite() + "')";
		
		try {
			super.addUpdateRecord(query);
			super.closeConnection();

		} catch (Exception ex) {
			System.out.println(ex + "toevoegen van locatie is mislukt");
			m = "locatie is niet toegevoegd door een onbekende reden";
		}		
		return m;
	}
	
}
