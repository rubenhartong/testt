package model;

import java.sql.ResultSet;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import domein.Actie;

public class ActieIO extends DbAbstract {

	public ActieIO() {
		super();
	}

	public ArrayList<Actie> getActies(String eventID){
		ArrayList<Actie> aActie = new ArrayList<Actie>();
		try {
			super.makeConnection();
			ResultSet rs = super.makeResultSet("SELECT actie.*, contacts.voornaam, contacts.achternaam FROM actie INNER JOIN contacts ON actie.contact=contacts.id WHERE eventID='"+eventID+"' ORDER BY date");
			
			while (rs.next()) {
				Actie a = new Actie(rs.getInt("id"), rs.getString("eventID"), rs.getString("user"), rs.getDate("date") ,rs.getString("text"),rs.getString("actor"),rs.getString("contact"),  rs.getString("voornaam"), rs.getString("achternaam"));
				aActie.add(a);
			}
		} catch (Exception ex) {
			System.out.println(ex+ "het ophalen van alle acties is mislukt");
		} finally {
			super.closeConnectRst();
		}
		
		return aActie;
	}
	
	public boolean checkDate(Date date) throws ParseException {
		//DateHandler dh = new DateHandler();
		Date current = new Date();
		boolean b = false;
		if(current.after(date) || current.equals(date)){
			b = true;
		}
		return b;
	}
	
	public ArrayList<Actie> getActiesContact(String eventID, String contact){
		ArrayList<Actie> aActie = new ArrayList<Actie>();
		try {
			super.makeConnection();
			ResultSet rs = super.makeResultSet("SELECT actie.*, contacts.voornaam, contacts.achternaam FROM actie INNER JOIN contacts ON actie.contact=contacts.id WHERE eventID='"+eventID+"' AND contact='"+contact+"'");
			
			while (rs.next()) {
				Actie a = new Actie(rs.getInt("id"), rs.getString("eventID"), rs.getString("user"), rs.getDate("date"),rs.getString("text"),rs.getString("actor"),rs.getString("contact"),  rs.getString("voornaam"), rs.getString("achternaam"));
				aActie.add(a);
			}
		super.closeConnectRst();
		} catch (Exception ex) {			
			System.out.println(ex+ "het ophalen van acties van contact "+contact+" is mislukt");
		}
		
		return aActie;
	}
	
	public ArrayList<Actie> getActiesDatum(String eventID, String datum){
		ArrayList<Actie> aActie = new ArrayList<Actie>();
		ResultSet rs = null;
		try {
			super.makeConnection();
			if(datum.equals("")){
				rs = super.makeResultSet("SELECT actie.*, contacts.voornaam, contacts.achternaam FROM actie INNER JOIN contacts ON actie.contact=contacts.id WHERE  date='"+datum+"'");
			}
			else {
			rs = super.makeResultSet("SELECT * FROM actie WHERE eventID='"+eventID+"' AND date='"+datum+"'");
			}
			while (rs.next()) {
				Actie a = new Actie(rs.getInt("id"), rs.getString("eventID"), rs.getString("user"), rs.getDate("date"),rs.getString("text"),rs.getString("actor"),rs.getString("contact"),  rs.getString("voornaam"), rs.getString("achternaam"));
				aActie.add(a);
			}
		super.closeConnectRst();
		} catch (Exception ex) {			
			System.out.println(ex+ "het ophalen van acties met datum  "+datum+" is mislukt");
		}
		
		return aActie;
	}
	
	public void setActie(String eventID, String user, String sDate, String text, String actor, String contact) throws ParseException{
		String query = "INSERT INTO actie (eventID, user, date, text, actor, contact)";
		query += "VALUES('" + eventID + "', '" + user + "',  '" + sDate + "', '" + text
				+ "', '" + actor + "', '"+ contact + "')";
System.out.println(query);
		try {
			 makeConnection();
			 makeResultSetUpdate(query);
			 closeConnection();

		} catch (Exception ex) {
				System.out.println("het toevoegen van een actie is mislukt");
		}
	}
	
	public boolean checkActies(String eventID) throws ParseException{
		DateHandler dh = new DateHandler();
		boolean aantal = false;
		String query = "SELECT * FROM actie WHERE eventID='"+eventID+"' AND date <='"+dh.getUniDate()+"' ";
		try {
			 ResultSet rs = null;
			makeConnection();
			 rs = super.makeResultSet(query);
			 while (rs.next()) {
					aantal =true;
			}
			 closeConnectRst();
			 
		} catch (Exception ex) {
				System.out.println("het toevoegen van een actie is mislukt");
				
		}
		return aantal;
	}
	
	
	
	public void deleteActie(String id1){
		int id= Integer.parseInt(id1);
		
		String query = "DELETE FROM `actie` WHERE id='"+id+"'";
		try {
			 makeConnection();
			 makeResultSetUpdate(query);

		} catch (Exception ex) {
				System.out.println("het verwijderen van een actie is mislukt");
		}
	}
	
}

