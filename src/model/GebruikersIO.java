package model;

import java.sql.ResultSet;
import java.io.UnsupportedEncodingException; 
import java.security.MessageDigest; 
import java.security.NoSuchAlgorithmException; 
import java.sql.SQLException;
import java.util.ArrayList;

import domein.Gebruiker;

public class GebruikersIO extends DbAbstract {

	public GebruikersIO() {
		super();
	}

	/*
	 * AddGebruiker voor normale gebruiker met telefoonnummer en wachtwoord Geen
	 * hashing/salting/security wordt nog gebruikt. Wordt plain text opgeslagen
	 * 
	 * @author rwijhe
	 */

	public void AddGebruikerUsr(Gebruiker g) {

		String email = g.getEmail();
		String voornaam = g.getVoornaam();
		String achternaam = g.getAchternaam();
		String bedrijf = g.getBedrijf();
		String telefoonnummer = g.getTelefoonnummer();
		String wachtwoord = g.getWachtwoord();

		String query = "INSERT INTO gebruiker (email, voornaam, achternaam, bedrijf, telefoonnummer, wachtwoord)";
		query += "VALUES('" + email + "', '" + voornaam + "', '" + achternaam
				+ "', '" + bedrijf + "', '" + telefoonnummer + "', '"
				+ wachtwoord + "')";

		try {
			super.addUpdateRecord(query);
			super.closeConnection();

		} catch (Exception e) {
				System.out.println(e + "Het toevoegen van een user is mislukt");
		}
	}	
	/*
	 * deleteGebruiker voor het verwijderen van een user in de database.
	 * 
	 * @author rwijhe
	 */
	public void deleteGebruiker(String email) {
		String query = "DELETE FROM gebruiker WHERE email='" + email + "'";
		super.addUpdateRecord(query);
	}

	/*
	 * veranderen van data van een gebruiker met wachtwoord
	 * 
	 * @author rwijhe
	 */
	public void changePass(String email, String ww) {

		String query = "UPDATE `gebruiker` SET  `wachtwoord` ='"+ ww + "'  WHERE email='" + email + "'";

		super.addUpdateRecord(query);
		super.closeConnection();
	}


	public boolean Login(String mail, String wachtwoord) {
		System.out.println("begin:" +mail + wachtwoord);
		boolean b = false;
		try {
			super.makeConnection();
			ResultSet rs = super.makeResultSet("SELECT * FROM gebruiker");
			while (rs.next()) {
				String mailCheck = (rs.getString("email"));
				if (mail.equals(mailCheck)) {
					String wwCheck = (rs.getString("wachtwoord"));
					if (wachtwoord.equals(wwCheck)) {
						b = true;
					}
				}
			}

		} catch (Exception e) {
			System.out.println(e + "er is iets mis gegaan");
		}
		super.closeConnectRst();
		return b;
	}
			 
	    private static String convertToHex(byte[] data) { 
	        StringBuffer buf = new StringBuffer();
	        for (int i = 0; i < data.length; i++) { 
	            int halfbyte = (data[i] >>> 4) & 0x0F;
	            int two_halfs = 0;
	            do { 
	                if ((0 <= halfbyte) && (halfbyte <= 9)) 
	                    buf.append((char) ('0' + halfbyte));
	                else 
	                    buf.append((char) ('a' + (halfbyte - 10)));
	                halfbyte = data[i] & 0x0F;
	            } while(two_halfs++ < 1);
	        } 
	        return buf.toString();
	    } 
	 
	    public static String SHA1(String text)   throws NoSuchAlgorithmException, UnsupportedEncodingException  { 
	    MessageDigest md;
	    md = MessageDigest.getInstance("SHA-1");
	    byte[] sha1hash = new byte[40];
	    md.update(text.getBytes("iso-8859-1"), 0, text.length());
	    sha1hash = md.digest();
	    return convertToHex(sha1hash);
	    } 

	
	/*
	 * 
	 * Lijst ophalen van gebruikers in de database
	 * @author kevinwtenweerde
	 * 
	 */
	
	public ArrayList<Gebruiker> listGebruikers() {
		ArrayList<Gebruiker> gList = new ArrayList<Gebruiker>();
		try {
			super.makeConnection();
			ResultSet rs = super.makeResultSet("SELECT * FROM gebruiker");
			while(rs.next()){
				String id = rs.getString("id");
				String email = rs.getString("email");
				String voornaam = rs.getString("voornaam");
				String achternaam  = rs.getString("achternaam");
				String bedrijf = rs.getString("bedrijf");
				String telefoonnummer = rs.getString("telefoonnummer");
				Gebruiker g = new Gebruiker(id, email, voornaam, achternaam, bedrijf, telefoonnummer);
				gList.add(g);
			}
		} catch (SQLException ex) {
			System.out.println("Lijst gebruikers ophalen gefaald.");
		}
		super.closeConnectRst();
		return gList; 
	}

}