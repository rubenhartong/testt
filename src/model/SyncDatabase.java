package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import domein.ContactZoho;
import domein.Evenement;

public class SyncDatabase extends DbAbstract {
	private boolean status = true;
	private ArrayList<ContactZoho> contacts;
	private ArrayList<Evenement> event;
	private int aCount = 0;
	private int aCounte = 0;
	private int maxRecords = 199;
	private ZohoAPI api = new ZohoAPI();

	public boolean synchronize() throws ParseException {
		// get contacts form zoho data base after last updated time

		contacts = api.ContactenOphalenNaDatum();
		aCount = contacts.size();

		event = api.EventsOphalen();
		aCounte = event.size();

		// check of het onder de 200 zit
		if (aCount <= maxRecords) {
			java.util.Date dt = new java.util.Date();
			java.text.SimpleDateFormat sdf = 
				     new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				
				String currentTime = sdf.format(dt);
			try {
				super.addUpdateRecord("UPDATE cache SET updated='"
						+ currentTime +"'");
				System.out.println("date set at: " +currentTime);
				super.closeConnection();

			} catch (Exception e) {

			}
			try {
				super.makeConnection();
				for (ContactZoho g : contacts) {
					// System.out.println("ervoor: " + g.getFunctie());
					// String functie = g.getFunctie().replaceAll( "\'",
					// "\\\'");
					// System.out.println("de functie is= " + functie
					// +"einde van functie tonen");
					String query = "REPLACE INTO contacts(id, voornaam, achternaam,email,functie,functie_level, functie_domain, bedrijf,telefoon, mobiel, contactpersoon)"
							+ "VALUES("
							+ g.getId()
							+ ", '"
							+ g.getVoornaam()
							+ "', '"
							+ g.getAchternaam()
							+ "', '"
							+ g.getMail()
							+ "','"
							+ g.getFunctie()
							+ "','"
							+ g.getFunctie_level()
							+ "','"
							+ g.getFunctie_domain()
							+ "','"
							+ g.getBedrijf()
							+ "','"
							+ g.getTelefoon()
							+ "', '"
							+ g.getMobiel()
							+ "', '"
							+ g.getContactpersoon()
							+ "')";
					System.out.println("query = \n" + query);
					try {
						super.makeResultSetUpdate(query);
					} catch (Exception e) {
						System.out.println("Query error name: " + g.getMail()
								+ "and error: " + e);
					}
				}
			} finally {
				super.closeConnection();
			}

		} else {
			System.out.println("meer dan 200 gewijzigde/aangemaakte accounts");
			status = false;
		}

		if (aCounte <= maxRecords) {
			EvenementenIO eio = new EvenementenIO();
			ArrayList<Evenement> events = eio.listEvenementen();
			DateHandler dh = new DateHandler();
			Date date = null; 
			try {
				super.makeConnection();
				ResultSet rs = super.makeResultSet("SELECT updatedEvent FROM cache WHERE 1");
				while (rs.next()) {
				date = dh.dateCompare(rs.getTimestamp("updatedEvent"));
				}
				super.closeConnectRst();
			} catch (SQLException ex) {
				System.out.println(ex);
				System.out.println("geen datum opgehaald");
			}

			System.out.println("******************* " + date + " ***************************");
			try {
				super.makeConnection();

				for (Evenement g : event) {
					String name = g.getNaam().replaceAll("\'", "\\\'");
					
						if(g.getUpdated().after(date)) {
							System.out.println("naam vervangen:" + name + "\n" + g.getUpdated() + " vs " + date);	
						String query = "REPLACE INTO event (eventID, contactpersoon, datum, naam, type)"
								+ "VALUES('"
								+ g.getEventID()
								+ "', '"
								+ g.getContactPersoon()
								+ "',  '"
								+ g.getDatum()
								+ "',  \""
								+ name
								+ "\",  '"
								+ g.getType() + "')";
						try {
							super.makeResultSetUpdate(query);
						} catch (Exception e) {
							System.out.println("Query error name: "
									+ g.getNaam() + "and error: " + e);
						}
					}
					
					for (int i = 0; i < events.size(); i++) {
						if (g.getEventID().equals(events.get(i).getEventID())) {
							events.remove(i);
						}
					}
				}
			} finally {
				java.util.Date dt = new java.util.Date();

				java.text.SimpleDateFormat sdf = 
				     new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

				String currentTime = sdf.format(dt);
				
				try {
					String query = "UPDATE cache SET updatedEvent='"+currentTime+"'";
					super.addUpdateRecord(query);
					System.out.println("date set at: " + query );

				} catch (Exception e) {
					System.out.println("exception update eventdate" + e);
				}
				System.out.println("Evenementen die in Zoho verwijderd zijn:\n");
				for (Evenement ed : events) {
					System.out.println(""+ ed.getNaam() + "\n");
					String query = "UPDATE event SET removed=true WHERE eventID='"+ed.getEventID()+"' ";
					try {
						super.makeResultSetUpdate(query);
					} catch (Exception e) {
						System.out.println("Query error name: "
								+ ed.getNaam() + "and error: " + e);
					}
				}
				super.closeConnection();
			}

		} else {
			System.out.println("meer dan 200 gewijzigde/aangemaakte events");
			status = false;
		}

		return status;
	}

}
