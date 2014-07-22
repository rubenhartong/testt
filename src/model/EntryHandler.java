package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import domein.Entry;

public class EntryHandler extends DbAbstract  {
	public EntryHandler() {
		super();
	}
	//get all entries
	public Entry getEntrieByID (String ids) {
		Entry entry = new Entry(0, "", "", "", "", "", "", "", "", false);
	 int id		= Integer.parseInt(ids);	
				try {
					super.makeConnection();
					ResultSet rs = super.makeResultSet("SELECT * FROM entries WHERE id="+id+" ");
					while (rs.next()) {					
						entry.setId(rs.getInt("id"));
						entry.setEventID(rs.getString("eventID"));
						entry.setFirst_name(rs.getString("first_name"));
						entry.setLast_name(rs.getString("last_name"));
						entry.setCompany(rs.getString("company"));
						entry.setFunction(rs.getString("function"));
						entry.setTelephone(rs.getString("telephone"));
						entry.setEmail(rs.getString("email"));
						entry.setContact_link(rs.getString("contact_link"));
						entry.setChecked(rs.getBoolean("checked"));
					}
					super.closeConnectRst();
					return entry;
				} catch (Exception ex) {
					 super.closeConnectRst();
					System.out.println("gegevens van alle aanmeldingen ophalen mislukt.");
					return entry;	
				}
			}
	
	//get entries not checked
	
	//get not checked entries with link
	public ArrayList<Entry> getFoundedEntries (String eventID) {
		ArrayList<Entry> entries = new ArrayList<Entry>();
				
				try {
					super.makeConnection();
					ResultSet rs = super.makeResultSet("SELECT * FROM entries INNER JOIN contacts ON entries.email=contacts.email AND checked = 0");
					while (rs.next()) {
						Entry entry = new Entry(0, "", "", "", "", "", "", "", "", false);
						entry.setId(rs.getInt("id"));
						entry.setEventID(rs.getString("eventID"));
						entry.setFirst_name(rs.getString("first_name"));
						entry.setLast_name(rs.getString("last_name"));
						entry.setCompany(rs.getString("company"));
						entry.setFunction(rs.getString("function"));
						entry.setTelephone(rs.getString("telephone"));
						entry.setEmail(rs.getString("email"));
						entry.setContact_link(rs.getString("contacts.id"));
						entry.setChecked(rs.getBoolean("checked"));
						entries.add(entry);
					}
				} catch (SQLException ex) {
					System.out.println("gegevens van alle aanmeldingen ophalen mislukt.");
				}
				super.closeConnectRst();
				
				return entries;	
			}
	
	//get not checked entries without link
	public ArrayList<Entry> getUnfoundEntries (String eventID) {
		ArrayList<Entry> entries = new ArrayList<Entry>();
				
				try {
					super.makeConnection();
					ResultSet rs = super.makeResultSet("SELECT * FROM   entries WHERE  NOT EXISTS (SELECT * FROM  contacts WHERE  entries.email = contacts.email) AND checked = 0 ");
					while (rs.next()) {
						Entry entry = new Entry(0, "", "", "", "", "", "", "", "", false);
						entry.setId(rs.getInt("id"));
						entry.setEventID(rs.getString("eventID"));
						entry.setFirst_name(rs.getString("first_name"));
						entry.setLast_name(rs.getString("last_name"));
						entry.setCompany(rs.getString("company"));
						entry.setFunction(rs.getString("function"));
						entry.setTelephone(rs.getString("telephone"));
						entry.setEmail(rs.getString("email"));
						entry.setContact_link(rs.getString("contact_link"));
						entry.setChecked(rs.getBoolean("checked"));
						entries.add(entry);
					}
				} catch (SQLException ex) {
					System.out.println("gegevens van alle aanmeldingen ophalen mislukt.");
				}
				super.closeConnectRst();
				
				return entries;	
			}
	
	//get entries only checked
	public ArrayList<Entry> getCompletedEntries (String eventID) {
		ArrayList<Entry> entries = new ArrayList<Entry>();
				
				try {
					super.makeConnection();
					ResultSet rs = super.makeResultSet("SELECT * FROM entries WHERE `checked` =1");
					while (rs.next()) {
						Entry entry = new Entry(0, "", "", "", "", "", "", "", "", false);
						entry.setId(rs.getInt("id"));
						entry.setEventID(rs.getString("eventID"));
						entry.setFirst_name(rs.getString("first_name"));
						entry.setLast_name(rs.getString("last_name"));
						entry.setCompany(rs.getString("company"));
						entry.setFunction(rs.getString("function"));
						entry.setTelephone(rs.getString("telephone"));
						entry.setEmail(rs.getString("email"));
						entry.setContact_link(rs.getString("contact_link"));
						entry.setChecked(rs.getBoolean("checked"));
						entries.add(entry);
					}
				} catch (SQLException ex) {
					System.out.println("gegevens van alle aanmeldingen ophalen mislukt.");
				}
				super.closeConnectRst();
				
				return entries;	
			}
	
	//check for link
	public void checkLink() {
		
	}
	//set entry checked
	
}
