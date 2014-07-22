package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import domein.Opmerking;

public class OpmerkingIO extends DbAbstract {

	public OpmerkingIO() {
		super();
	}

	/*
	 * 
	 * Lijst opmerkingen ophalen
	 * 
	 * @author rubenhartong
	 */

	public ArrayList<Opmerking> listOpmerkingen(String id, String eventID) {
		ArrayList<Opmerking> oList = new ArrayList<Opmerking>();
		try {
			super.makeConnection();
			ResultSet rs = super.makeResultSet("SELECT DISTINCT * FROM opmerkingen  WHERE opmerkingen.eventID ='"+eventID+"' AND contact_id='"+id+"' ORDER BY opmerkingen.datum DESC");
			while (rs.next()) {
				
				Opmerking g = new Opmerking(null, "", "", "", "", ""); 
				g.setEmail(rs.getString("contact_id"));
				g.setGebruikerID(rs.getString("gebruikerID"));
				g.setEventID(rs.getString("eventID"));
				g.setDatum(rs.getDate("datum"));
				g.setOpmerking(rs.getString("opmerking"));
				g.setStatus(null);
				
				oList.add(g);
			}
		} catch (SQLException ex) {
			System.out.println("Lijst opmerkingen ophalen mislukt." + ex);
			
		}
		super.closeConnectRst();
		return oList;
	}
	
	public void setStatus(String eventID, String contact, String name, String valueS){
		int value = Integer.parseInt(valueS);
		String query = "UPDATE `status` SET  `"+name+"`="+value+" WHERE contact='"+contact+"' AND eventID='"+eventID+"' ";

		try {
			 makeConnection();
			 makeResultSetUpdate(query);
			 closeConnection();

		} catch (Exception ex) {
				System.out.println("het toevoegen van een status is mislukt");
		}
	}
	/*
	 * Invoegen van een opmerking
	 * 
	 *  @author rubenhartong
	 */
	
	public void insertOpmerking(String contact, String opmerking, String gebruikerID, String eventID){
		String query = "INSERT INTO opmerkingen (contact_id, opmerking, gebruikerID, eventID) ";
		query += "VALUES('" + contact + "', '" + opmerking + "', '" + gebruikerID+ "', '" + eventID + "')";
 
		try {
			super.addUpdateRecord(query);
			super.closeConnection();
			
		} catch (Exception e) {
			System.out.println(e + "toevoegen van een opmerking is mislukt");
		}
	}
	
	
}
