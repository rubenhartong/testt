package model;

import java.sql.ResultSet;
import java.util.ArrayList;

import domein.MailFormat;

public class TemplateIO extends DbAbstract {

    public TemplateIO() {
	super();
    }


    	public MailFormat getGegevens(int mailID){
    		MailFormat mf = new MailFormat(mailID, "", "", "", "", "");
    		try {
    		    super.makeConnection();
    		    // Deze query gaat wat fout mee, eventID word er dubbel ingezet
    		    ResultSet rs = super.makeResultSet("SELECT * FROM mailFormat WHERE mailID="+mailID+"");
    		    while (rs.next()) {
    		    	mf.setId(rs.getInt("mailID"));
    		    	mf.setNaam(rs.getString("naam"));
    		    	mf.setFormat(rs.getString("format"));
    		    	mf.setElement1(rs.getString("element1"));
    		    	mf.setElement2(rs.getString("element2"));
    		    	mf.setElement3(rs.getString("element3"));

    			    return mf;
    			}
    		    super.closeConnectRst();
    		}
    		catch(Exception e){
    			System.out.print(e + "ophalen van een enkele maildata door middel van mailID mislukt");
    		}
			return mf;	
    	}
    	
    	public ArrayList<MailFormat> getEventGegevens(){
    		ArrayList<MailFormat> aFormat= new ArrayList<MailFormat>();
    		try {
    		    super.makeConnection();

    		    ResultSet rs = super.makeResultSet("SELECT * FROM `mailFormat` WHERE 1");
    		   
    		    while (rs.next()) {
    		    	System.out.println("format toevoegen:" + rs.getInt("mailID"));
    		    	MailFormat mf = new MailFormat(1, "", "", "", "", "");
    		    	mf.setId(rs.getInt("mailID"));
    		    	mf.setNaam(rs.getString("naam"));
    		    	mf.setFormat(rs.getString("format"));
    		    	mf.setElement1(rs.getString("element1"));
    		    	mf.setElement2(rs.getString("element2"));
    		    	mf.setElement3(rs.getString("element3"));
    			     			   
    				aFormat.add(mf);
    			}
    		    super.closeConnectRst();
    		}
    		catch(Exception e){
    			System.out.print(e + "ophalen van maildata door middel van MailID is mislukt");
    		}
			return aFormat;
    		
    	}
    	
    	public void addMailData(String naam, String format, String element1, String element2, String element3) {
    		super.makeConnection();
    		String query = "INSERT INTO mailFormat (naam, format, element1, element2, element3)"
    				+ "VALUES ('"+naam+"','"+format+"', '"+element1+"', '"+element2+"', '"+element3+"')";
    		System.out.println("\n" + query + "\n");
    		try {
    			super.addUpdateRecord(query);
    			super.closeConnection();
    		} catch (Exception ex) {
    			System.out.println(ex + "aanmaken van nieuwe maildata is mislukt");
    		}
    	}
    	public void updateMailData(int mailID, String naam, String format, String element1, String element2, String element3) {
    		super.makeConnection();
    		String query = "UPDATE mailFormat SET naam='"+naam+"', format='"+format+"' , element1='"+element1+"', element2='"+element2+"', element3='"+element3+"'  WHERE mailID = "+mailID+"";
    		System.out.println("query :\n" + query + "\n");
    		try {
    			super.addUpdateRecord(query);
    			super.closeConnection();
    		} catch (Exception ex) {
    			System.out.println(ex+ "updaten van de maildata is mislukt");
    		}
    	}
}