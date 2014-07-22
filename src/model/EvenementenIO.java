package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import domein.Evenement;

public class EvenementenIO extends DbAbstract {

	public EvenementenIO() {
		super();
	}

	public Evenement getEvent(String id) {
		Evenement e = new Evenement("", "", "", null, 0, 0, "", "", "", false, null);
		try {
			super.makeConnection();
			ResultSet rs = super.makeResultSet("SELECT * FROM event WHERE eventID= '"+id+"'");
			while (rs.next()) {
					e.setEventID( id);
					e.setNaam(rs.getString("naam"));
					e.setSubtitel(rs.getString("subtitel"));
					e.setDatum(rs.getDate("datum"));
					e.setLocatieID(rs.getInt("locatieID"));
					e.setProgrammaID(rs.getInt("programmaID"));
					e.setContactPersoon( rs.getString("contactpersoon"));
					e.setOrganisator(rs.getString("organisator"));
					e.setType(rs.getString("type"));
					e.setRemoved(rs.getBoolean("removed"));
					
			}
			super.closeConnectRst();
			return e;
		} catch (SQLException ex) {
			super.closeConnectRst();
			System.out.println(e + "het ophalen van alle evenementen is mislukt");
			return e;
		}
	}
	
	
	public ArrayList<Evenement> listEvenementen() {
		ArrayList<Evenement> eList = new ArrayList<Evenement>();
		try {
			super.makeConnection();
			ResultSet rs = super.makeResultSet("SELECT * FROM event ORDER BY datum DESC; ");
			while (rs.next()) {
				String EventID = rs.getString("eventID");
				String naam = rs.getString("naam");
				String subtitel = rs.getString("subtitel");
				Date datum  = rs.getDate("datum");
				int locatieID = rs.getInt("locatieID");
				int programmaID = rs.getInt("programmaID");
				String contactPersoon = rs.getString("contactpersoon");
				String organisator = rs.getString("organisator");
				String type = rs.getString("type");
				boolean removed = rs.getBoolean("removed");
				Evenement e = new Evenement(EventID, naam, subtitel, datum, locatieID, programmaID, contactPersoon, organisator, type, removed, null);
				eList.add(e);
			}
		} catch (SQLException ex) {
			System.out.println(ex +"Lijst evenementen ophalen mislukt.");
		}
		super.closeConnectRst();
		return eList; 
	}
	
	public ArrayList<Evenement> listUpcomingEvents() throws ParseException {
		ArrayList<Evenement> eList = new ArrayList<Evenement>();
		DateHandler dh = new DateHandler();
		try {
			super.makeConnection();
			ResultSet rs = super.makeResultSet("SELECT * FROM `event`  WHERE `datum` >= '"+dh.getDate()+"' ORDER BY `datum` DESC");
			while (rs.next()) {
				
				String EventID = rs.getString("eventID");
				String naam = rs.getString("naam");
				String subtitel = rs.getString("subtitel");
				Date datum  = rs.getDate("datum");
				int locatieID = rs.getInt("locatieID");
				int programmaID = rs.getInt("programmaID");
				String contactPersoon = rs.getString("contactpersoon");
				String organisator = rs.getString("organisator");
				String type = rs.getString("type");
				boolean removed = rs.getBoolean("removed");
				Evenement e = new Evenement(EventID, naam, subtitel, datum, locatieID, programmaID, contactPersoon, organisator, type, removed, null);
				eList.add(e);
			}
		} catch (SQLException ex) {
			System.out.println(ex +"Lijst evenementen ophalen mislukt.");
		}
		super.closeConnectRst();
		return eList; 
	}
	
	public ArrayList<Evenement> listPastEvents() throws ParseException {
		ArrayList<Evenement> eList = new ArrayList<Evenement>();
		DateHandler dh = new DateHandler();
		try {
			super.makeConnection();
			ResultSet rs = super.makeResultSet("SELECT * FROM `event`  WHERE `datum` < '"+dh.getDate()+"' ORDER BY `datum` DESC");
			while (rs.next()) {
				
				String EventID = rs.getString("eventID");
				String naam = rs.getString("naam");
				String subtitel = rs.getString("subtitel");
				Date datum  = rs.getDate("datum");
				int locatieID = rs.getInt("locatieID");
				int programmaID = rs.getInt("programmaID");
				String contactPersoon = rs.getString("contactpersoon");
				String organisator = rs.getString("organisator");
				String type = rs.getString("type");
				boolean removed = rs.getBoolean("removed");
				Evenement e = new Evenement(EventID, naam, subtitel, datum, locatieID, programmaID, contactPersoon, organisator, type, removed, null);
				eList.add(e);
			}
		} catch (SQLException ex) {
			System.out.println(ex +"Lijst evenementen ophalen mislukt.");
		}
		super.closeConnectRst();
		return eList; 
	}
	
	public void pasEvenementAan(Evenement e) throws ParseException{
		String naam = e.getNaam();
		String subTitel = e.getSubtitel();
		int locatieID = e.getLocatieID();
		String contactPersoon = e.getContactPersoon();
		String organisator = e.getOrganisator();
		

		String query = "UPDATE event SET naam='"+naam+"', subtitel='"+subTitel+"',datum='"+e.getDatum()+"',  locatieid="+locatieID+" , contactpersoon='"+contactPersoon+"', organisator='"+organisator+"'  where eventID='"+e.getEventID()+"'";
		System.out.println("Query is ***********************" +query);
		try {
			super.addUpdateRecord(query);
			super.closeConnectRst();

		} catch (Exception ex) {
			System.out.println(ex + "het aanpassen van een evenement is mislukt");
		}
	}
	
	public void linkProgramma(String eId, int pId){
		String query = "UPDATE event SET programmaID='"+pId+"' where eventID='"+eId+"'";

		try {
			super.addUpdateRecord(query);
			super.closeConnection();

		} catch (Exception ex) {
			System.out.println(ex + "het linken van programma aan een event is mislukt");
		}
	}
	
	public void removeEvent(String eventID){
		String query = "DELETE FROM event where eventID='"+eventID+"'";
		System.out.println(query);
		try {
			super.addUpdateRecord(query);
			super.closeConnection();
		} catch (Exception ex) {
			System.out.println(ex + "het verwijderen van een evenement is niet gelukt.");
		}  
	}
}
