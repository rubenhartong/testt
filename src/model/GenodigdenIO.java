package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import domein.ContactZoho;

public class GenodigdenIO extends DbAbstract {

	public GenodigdenIO() {
		super();
	}


	
	public ContactZoho getContactData(String id, String eventID) {
		ContactZoho contact = new ContactZoho(id,"", "", "","","", "", "","", "", "", 0, 0, 0 ,0,0, true);
		
		try {
			super.makeConnection();
			ResultSet rs = super.makeResultSet("select * from contacts INNER JOIN status ON contacts.id=status.contact WHERE id ='"+id+"' AND eventID='"+eventID+"'");
			while (rs.next()) {
				contact.setId(rs.getString("id"));
				contact.setVoornaam(rs.getString("voornaam"));
				contact.setAchternaam(rs.getString("achternaam"));
				contact.setMail(rs.getString("email"));
				contact.setFunctie(rs.getString("functie"));
				contact.setFunctie_level(rs.getString("functie_level"));
				contact.setFunctie_domain(rs.getString("functie_domain"));
				contact.setBedrijf(rs.getString("bedrijf"));
				contact.setTelefoon(rs.getString("telefoon"));
				contact.setMobiel(rs.getString("mobiel"));
				contact.setStatus(rs.getInt("status"));
				contact.setBellen(rs.getInt("bellen"));
				contact.setMailen(rs.getInt("mailen"));
				contact.setHardcopy(rs.getInt("hardcopy"));
				contact.setPrioriteit(rs.getInt("prioriteit"));
				contact.setContactpersoon(rs.getString("contactpersoon"));
			}
		} catch (SQLException ex) {
			System.out.println("gegevens van contactpersoon ophalen mislukt.");
		}
		super.closeConnectRst();
		
		return contact;	
	}
	
	public ContactZoho getContactDataI(String id) {
		ContactZoho contact = new ContactZoho(id,"", "", "","","", "", "","", "", "", 0, 0, 0 ,0,0, true);
		
		try {
			super.makeConnection();
			ResultSet rs = super.makeResultSet("select * from contacts WHERE id ='"+id+"'");
			while (rs.next()) {
				contact.setId(rs.getString("id"));
				contact.setVoornaam(rs.getString("voornaam"));
				contact.setAchternaam(rs.getString("achternaam"));
				contact.setMail(rs.getString("email"));
				contact.setFunctie(rs.getString("functie"));
				contact.setFunctie_level(rs.getString("functie_level"));
				contact.setFunctie_domain(rs.getString("functie_domain"));
				contact.setBedrijf(rs.getString("bedrijf"));
				contact.setTelefoon(rs.getString("telefoon"));
				contact.setMobiel(rs.getString("mobiel"));
				contact.setContactpersoon(rs.getString("contactpersoon"));
			}
		} catch (SQLException ex) {
			System.out.println("gegevens van contactpersoon ophalen mislukt.");
		}
		super.closeConnectRst();
		
		return contact;	
	}
	
	public ArrayList<ContactZoho> checkboxSearch(String eventIDs, String level, String domain){
		ArrayList<ContactZoho> contacten = new ArrayList<ContactZoho>();
		String query = "";
		if(level  != "" && domain  != "" ){
			query = "SELECT * FROM contacts WHERE contacts.id NOT IN(SELECT status.contact FROM contacts INNER JOIN status ON contacts.id=status.contact AND status.eventID='"+eventIDs+"') "
					+ "AND  functie_level IN ("+level+") AND functie_domain IN ("+domain+")";
		} else  if (domain  != ""){
			query = "SELECT * FROM contacts  WHERE contacts.id NOT IN(SELECT status.contact FROM contacts INNER JOIN status ON contacts.id=status.contact AND status.eventID='"+eventIDs+"') AND  functie_domain IN ("+domain+")";
		} else if (level  != ""){
			query = "SELECT * FROM contacts  WHERE contacts.id NOT IN(SELECT status.contact FROM contacts INNER JOIN status ON contacts.id=status.contact AND status.eventID='"+eventIDs+"') AND functie_level IN ("+level+")";
		} else {
			return contacten;
		}
		try {
			
			super.makeConnection();
			ResultSet rs = super.makeResultSet(query);
			while (rs.next()) {
				ContactZoho contact = new ContactZoho("","", "", "","","", "", "","", "", "", 0, 0, 0 ,0,0, true); 
				contact.setId(rs.getString("id"));
				contact.setVoornaam(rs.getString("voornaam"));
				contact.setAchternaam(rs.getString("achternaam"));
				contact.setMail(rs.getString("email"));
				contact.setFunctie(rs.getString("functie"));
				contact.setFunctie_level(rs.getString("functie_level"));
				contact.setFunctie_domain(rs.getString("functie_domain"));
				contact.setBedrijf(rs.getString("bedrijf"));
				contact.setTelefoon(rs.getString("telefoon"));
				contact.setMobiel(rs.getString("mobiel"));
				contact.setContactpersoon(rs.getString("contactpersoon"));
				contacten.add(contact);
				System.out.println("contact:"+contact);
			}
			super.closeConnectRst();

		} catch (Exception e) {
			System.out.println(e);
		}
		
		return contacten;
	}
	
	/*
	 * 
	 * Lijst met alle genodigden ophalen die nog niet uitgenodigd zijn
	 * @author kevinwtenweerde
	 * 
	 */
	
	public ArrayList<ContactZoho> listNotGenodigden(String eventIDs) {
		ArrayList<ContactZoho> gList = new ArrayList<ContactZoho>();
		try {
			super.makeConnection();
			
			ResultSet rs = super.makeResultSet("SELECT * FROM contacts WHERE contacts.id NOT IN(SELECT status.contact FROM contacts INNER JOIN status ON contacts.id=status.contact AND status.eventID='"+eventIDs+"') ");
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
				gList.add(g);
			}
		} catch (SQLException ex) {
			System.out.println("Lijst genodigden ophalen gefaald.");
		}
		super.closeConnectRst();
		return gList;
	}
	
	public ArrayList<ContactZoho> search(String eventIDs, String search) {
		ArrayList<ContactZoho> gList = new ArrayList<ContactZoho>();
		try {
			super.makeConnection();
			
			ResultSet rs = super.makeResultSet("SELECT * FROM contacts WHERE contacts.id NOT IN(SELECT status.contact FROM contacts INNER JOIN status ON contacts.id=status.contact AND status.eventID='"+eventIDs+"') "
					+ "AND( 	email 				LIKE  '%"+ search +"%' "
					+ "OR 		bedrijf 			LIKE  '%"+ search +"%' "
					+ "OR 		achternaam 	LIKE  '%"+ search +"%' "
					+ "OR 		contactpersoon 	LIKE  '%"+ search +"%' "
					+ " OR 	voornaam 		LIKE  '%"+ search +"%') ");
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
				gList.add(g);
			}
		} catch (SQLException ex) {
			System.out.println("Lijst genodigden ophalen gefaald.");
		}
		super.closeConnectRst();
		return gList;
	}
}
