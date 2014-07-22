package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import domein.Programma;

public class ProgrammaIO extends DbAbstract {

    public ProgrammaIO() {
	super();
    }

    /*
     * toevoegen van een programma
     * 
     * @author rwijhe
     */

    public void addProgramma(Programma p) {
	String query = "INSERT INTO `programma` (`programmaID`, `vanTijd`, `totTijd`, `programmaTxt`)";
	query += "VALUES (NULL, " + p.getVanTijd() + ", " + p.getTotTijd() + ", " + p.getProgrammatxt() + ")";
	super.addUpdateRecord(query);
    }

    /*
     * editen van een programma
     * 
     * @author rwijhe
     */

    public void updateProgramma(Programma p) {
	int programmaID = p.getProgrammaID();
	String vanTijd = p.getVanTijd();
	String totTijd = p.getTotTijd();
	String programmaTxt = p.getProgrammatxt();
	String query = "UPDATE programma SET vanTijd='" + vanTijd + "', totTijd='" + totTijd + "', programmaTxt='"
		+ programmaTxt + "' where programmaID='" + programmaID + "'";

	try {
	    super.addUpdateRecord(query);
	    super.closeConnectRst();

	} catch (Exception ex) {

	}
    }

    /*
     * het verkrijgen van de starttijd doormiddel van een ID
     * 
     * @author kevinwtenweerde
     */
    public String getStartTijd(int id) {
	String tijd = "";
	try {
	    super.makeConnection();
	    ResultSet rs = super.makeResultSet("SELECT * FROM programma");
	    while (rs.next()) {
		int idCheck = (rs.getInt("programmaID"));
		// System.out.println(mail);
		// System.out.println(mailCheck);
		if (id == idCheck) {
		    tijd = rs.getString("vanTijd");
		}
	    }
	} catch (Exception e) {

	}
	super.closeConnectRst();
	return tijd;
    }

    /*
     * toevoegen van een programma
     * 
     * @author kevinwtenweerde
     */

    public void voegProgrammaToe(String sTijd, String eTijd, String prog) {

	String query = "INSERT INTO programma (vanTijd, totTijd, programmaTxt)";
	query += "VALUES('" + sTijd + "', '" + eTijd + "', '" + prog + "')";

	try {
	    super.addUpdateRecord(query);
	    super.closeConnectRst();

	} catch (Exception ex) {

	}
    }

    /*
     * verkrijgen van de lijst met programma's
     * 
     * @author kevinwtenweerde
     */

    public ArrayList<Programma> listProgramma() {
	ArrayList<Programma> pList = new ArrayList<Programma>();
	try {
	    super.makeConnection();
	    ResultSet rs = super.makeResultSet("SELECT * FROM programma");
	    while (rs.next()) {
		int programmaID = rs.getInt("programmaID");
		String vanTijd = rs.getString("vanTijd");
		String totTijd = rs.getString("totTijd");
		String pText = rs.getString("programmaTxt");
		Programma p = new Programma(programmaID, vanTijd, totTijd, pText);
		pList.add(p);
	    }
	} catch (SQLException ex) {
	    System.out.println("Lijst programma's ophalen gefaald.");
	}
	super.closeConnectRst();
	return pList;
    }

    /*
     * get programma via ID
     * 
     * @author rwijhe
     */

    public Programma getProgramma(int id) {
	Programma p = new Programma(0, "", "", "");
	try {
	    super.makeConnection();
	    ResultSet rs = super.makeResultSet("SELECT * FROM programma WHERE programmaID ="+id+"");
	    while (rs.next()) {
		    p.setProgrammaID(id);
		    p.setTotTijd(rs.getString("totTijd"));
		    p.setVanTijd(rs.getString("vanTijd"));
		    p.setProgrammatxt(rs.getString("programmaTxt"));
	    }
	    super.closeConnectRst();
	    return p;
	} catch (Exception ex) {
	    super.closeConnectRst();
	    return p;
	}
    }

    /*
     * check of programma al bestaat
     * 
     * @author rwijhe
     */

    public boolean programmaBestaat(int id) {
	boolean b = false;
	int counter = 0;
	try {
	    super.makeConnection();
	    ResultSet rs = super.makeResultSet("SELECT * FROM programma");
	    while (rs.next()) {
		if (rs.getInt("programmaID") == id) {
		    counter++;
		}
	    }
	} catch (Exception ex) {
	    ex.printStackTrace();
	}
	if (counter != 0) {
	    b = true;
	}
	return b;
    }
}
