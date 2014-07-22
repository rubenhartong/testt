package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import domein.ContactZoho;

public class BezoekerIO extends DbAbstract {

	public BezoekerIO() {
		super();
	}

	/*
	 * 
	 * Lijst bezoekers ophalen
	 */
	
	public ArrayList<ContactZoho> naamBezoekers(String eventID) {

		ArrayList<ContactZoho> gList = new ArrayList<ContactZoho>();
		try {
			super.makeConnection();
			ResultSet rs = super.makeResultSet("select * from contacts INNER JOIN status ON contacts.id=status.contact AND status.eventID='"+eventID+"'  ORDER BY status ASC");
			while (rs.next()) {
				ContactZoho g = new ContactZoho("","", "", "","","", "", "","", "", "", 0, 0, 0 ,0,0, true);
				g.setId(rs.getString("id"));
				g.setVoornaam(rs.getString("voornaam"));
				g.setAchternaam(rs.getString("achternaam"));
				g.setMail(rs.getString("email"));
				g.setFunctie(rs.getString("functie"));
				g.setFunctie_level(rs.getString("functie_level"));
				g.setFunctie_domain(rs.getString("functie_domain"));
				g.setBedrijf(rs.getString("bedrijf"));
				g.setTelefoon(rs.getString("telefoon"));
				g.setMobiel(rs.getString("mobiel"));
				g.setContactpersoon(rs.getString("contactpersoon"));
				g.setStatus(rs.getInt("status"));
				g.setBellen(rs.getInt("bellen"));
				g.setMailen(rs.getInt("mailen"));
				g.setHardcopy(rs.getInt("hardcopy"));
				g.setPrioriteit(rs.getInt("prioriteit"));
				gList.add(g);
			}
		} catch (SQLException ex) {
			System.out.println("Lijst bezoekers ophalen gefaald.");
		}
		super.closeConnectRst();
		return gList;
	}
	
	public ArrayList<ContactZoho> naamBezoekersSorted(String eventID, String sortBy) {

		ArrayList<ContactZoho> gList = new ArrayList<ContactZoho>();
		try {
			super.makeConnection();
			ResultSet rs = super.makeResultSet("select * from contacts INNER JOIN status ON contacts.id=status.contact AND status.eventID='"+eventID+"'  ORDER BY "+sortBy+" =3 ASC");
			while (rs.next()) {
				ContactZoho g = new ContactZoho("","", "", "","","", "", "","", "", "", 0, 0, 0 ,0,0, true);
				g.setId(rs.getString("id"));
				g.setVoornaam(rs.getString("voornaam"));
				g.setAchternaam(rs.getString("achternaam"));
				g.setMail(rs.getString("email"));
				g.setFunctie(rs.getString("functie"));
				g.setFunctie_level(rs.getString("functie_level"));
				g.setFunctie_domain(rs.getString("functie_domain"));
				g.setBedrijf(rs.getString("bedrijf"));
				g.setTelefoon(rs.getString("telefoon"));
				g.setMobiel(rs.getString("mobiel"));
				g.setContactpersoon(rs.getString("contactpersoon"));
				g.setStatus(rs.getInt("status"));
				g.setBellen(rs.getInt("bellen"));
				g.setMailen(rs.getInt("mailen"));
				g.setHardcopy(rs.getInt("hardcopy"));
				g.setPrioriteit(rs.getInt("prioriteit"));
				gList.add(g);
			}
		} catch (SQLException ex) {
			System.out.println("Lijst bezoekers ophalen gefaald.");
		}
		super.closeConnectRst();
		return gList;
	}
	
	public ArrayList<ContactZoho> sortStatus(String sort, String eventID) {

		ArrayList<ContactZoho> gList = new ArrayList<ContactZoho>();
		try {
			super.makeConnection();
			ResultSet rs = super.makeResultSet("select * from contacts INNER JOIN status ON contacts.id=status.contact AND status.eventID='"+eventID+"'  ORDER BY "+sort+" DESC");
			while (rs.next()) {
				ContactZoho g = new ContactZoho("","", "", "","","", "", "","", "", "", 0, 0, 0 ,0,0, true);
				g.setId(rs.getString("id"));
				g.setVoornaam(rs.getString("voornaam"));
				g.setAchternaam(rs.getString("achternaam"));
				g.setMail(rs.getString("email"));
				g.setFunctie(rs.getString("functie"));
				g.setFunctie_level(rs.getString("functie_level"));
				g.setFunctie_domain(rs.getString("functie_domain"));
				g.setBedrijf(rs.getString("bedrijf"));
				g.setTelefoon(rs.getString("telefoon"));
				g.setMobiel(rs.getString("mobiel"));
				g.setContactpersoon(rs.getString("contactpersoon"));
				g.setStatus(rs.getInt("status"));
				g.setBellen(rs.getInt("bellen"));
				g.setMailen(rs.getInt("mailen"));
				g.setHardcopy(rs.getInt("hardcopy"));
				g.setPrioriteit(rs.getInt("prioriteit"));
				gList.add(g);
			}
		} catch (SQLException ex) {
			System.out.println("Lijst bezoekers ophalen gefaald.");
		}
		super.closeConnectRst();
		return gList;
	}
	
	public ArrayList<ContactZoho> mailingBezoekers(String eventID) {
		ArrayList<ContactZoho> gList = new ArrayList<ContactZoho>();
		try {
			super.makeConnection();
			ResultSet rs = super.makeResultSet("select * from contacts INNER JOIN status ON contacts.id=status.contact AND status.eventID='"+eventID+"'  AND mailing=0 ORDER BY status ASC");
			while (rs.next()) {
				ContactZoho g = new ContactZoho("","", "", "","","", "", "","", "", "", 0, 0, 0 ,0,0, true);
				g.setId(rs.getString("id"));
			g.setVoornaam(rs.getString("voornaam"));
				g.setAchternaam(rs.getString("achternaam"));
				g.setMail(rs.getString("email"));
				g.setFunctie(rs.getString("functie"));
				g.setFunctie_level(rs.getString("functie_level"));
				g.setFunctie_domain(rs.getString("functie_domain"));
				g.setBedrijf(rs.getString("bedrijf"));
				g.setTelefoon(rs.getString("telefoon"));
				g.setMobiel(rs.getString("mobiel"));
				g.setContactpersoon(rs.getString("contactpersoon"));
				g.setStatus(rs.getInt("status"));
				g.setBellen(rs.getInt("bellen"));
				g.setMailen(rs.getInt("mailen"));
				g.setHardcopy(rs.getInt("hardcopy"));
				g.setPrioriteit(rs.getInt("prioriteit"));
				gList.add(g);
			}
		} catch (SQLException ex) {
			System.out.println("Lijst bezoekers ophalen gefaald.");
		}
		super.closeConnectRst();
		return gList;
	}
	
}