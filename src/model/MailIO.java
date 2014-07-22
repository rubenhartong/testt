package model;

import java.sql.ResultSet;
import java.util.ArrayList;

import domein.Mail;
import domein.MailLogin;

public class MailIO extends DbAbstract {

    public MailIO() {
	super();
    }


    	public Mail getGegevens(String eventID, int mailID){
    		Mail u = new Mail(mailID, "", "", "", "", "", "", "", "", "", "", "", "", "", "");
    		try {
    		    super.makeConnection();
    		    // Deze query gaat wat fout mee, eventID word er dubbel ingezet
    		    ResultSet rs = super.makeResultSet("SELECT * FROM mail WHERE id="+mailID+"");
    		    while (rs.next()) {
    		    	u.setId(rs.getInt("id"));
    		    	u.setEventID(rs.getString("eventID"));
    		    	u.setAanhef(rs.getString("aanhef"));
    			    u.setOnderwerp(rs.getString("onderwerp"));
    			    u.setLink(rs.getString("link"));
    			    u.setImage_header(rs.getString("image_header"));
    			    u.setImage_subheader(rs.getString("image_subheader"));
    			    u.setImage(rs.getString("image"));
    			    u.setContent1(rs.getString("content1"));
    			    u.setContent2(rs.getString("content2"));
    			    u.setHeader1(rs.getString("header1"));
    			    u.setHeader2(rs.getString("header2"));
    			    
    			    return u;
    			}
    		    super.closeConnectRst();
    		}
    		catch(Exception e){
    			System.out.print(e + "ophalen van een enkele maildata door middel van mailID mislukt");
    		}
			return u;	
    	}
    	
    	public ArrayList<Mail> getEventGegevens(int mailID, String eventID){
    		ArrayList<Mail> aMail = new ArrayList<Mail>();
    		try {
    		    super.makeConnection();
    		    // Deze query gaat wat fout mee, eventID word er dubbel ingezet
    		    ResultSet rs = super.makeResultSet("SELECT * FROM mail WHERE id="+mailID+"");
    		   
    		    while (rs.next()) {
    		    	int 	id				= rs.getInt("id");
    		    	String naam				= rs.getString("naam");
    		    	String aanhef			= rs.getString("aanhef");
    		    	String onderwerp		= rs.getString("onderwerp");
    		    	String link				= rs.getString("link");
    		    	String image_header 	= rs.getString("image_header");
    		    	String image_subheader	= rs.getString("image_subheader");
    		    	String image			= rs.getString("image");
    		    	String content1      	= rs.getString("content1");
    		    	String content2			= rs.getString("content2");
    		    	String header1			= rs.getString("header1");
    		    	String header2 			= rs.getString("header2");
    			    
    			    Mail m = new Mail(id, eventID, naam, link, image_header, image_subheader, image, content1, content2, header1, header2, "", "", onderwerp, aanhef);
    				aMail.add(m);
    			    
    			    return aMail;
    			}
    		    super.closeConnectRst();
    		}
    		catch(Exception e){
    			System.out.print(e + "ophalen van maildata door middel van MailID is mislukt");
    		}
			return aMail;
    		
    	}
    	
    	public ArrayList<Mail> getEventsGegevens(String eventID){
    		ArrayList<Mail> aMail = new ArrayList<Mail>();
    		try {
    		    super.makeConnection();
    		    
    		    ResultSet rs = super.makeResultSet("SELECT * FROM mail WHERE eventID='"+eventID+"'");
    		   
    		    while (rs.next()) {
    		    	int 	id				= rs.getInt("id");
    		    	String naam				= rs.getString("naam");
    		    	String aanhef			= rs.getString("aanhef");
    		    	String onderwerp		= rs.getString("onderwerp");
    		    	String link				= rs.getString("link");
    		    	String image_header 	= rs.getString("image_header");
    		    	String image_subheader	= rs.getString("image_subheader");
    		    	String image			= rs.getString("image");
    		    	String content1      	= rs.getString("content1");
    		    	String content2			= rs.getString("content2");
    		    	String header1			= rs.getString("header1");
    		    	String header2 			= rs.getString("header2");
    			    
    			    Mail m = new Mail(id, eventID, naam, link, image_header, image_subheader, image, content1, content2, header1, header2, "", "", onderwerp, aanhef);
    				aMail.add(m);
    			}
    		    super.closeConnectRst();
    		}
    		catch(Exception e){
    			System.out.print(e + "ophalen van maildata door middel van eventID is mislukt");
    		}
			return aMail;
    		
    	}
    	
    	public MailLogin getMailLogin(){
    	
    		MailLogin ml = new MailLogin("", "");
    		try {
    		    super.makeConnection();
    		    
    		    ResultSet rs = super.makeResultSet("SELECT email, pass FROM cache WHERE id=1");
    		   
    		    while (rs.next()) {	    
    		    	 decription myEncryptor= new decription();
    		    	 String decrypted=myEncryptor.decrypt(rs.getString("pass"));
    			    ml.setEmail(rs.getString("email"));
    			    ml.setPass(decrypted);
    			}
    		    super.closeConnectRst();
    		}
    		catch(Exception e){
    			System.out.print(e + "ophalen van maildata is mislukt");
    		}
			return ml;	
    	}
    	
    	public void setLogin(String mail, String pass) throws Exception{
    		super.makeConnection();
	    	 decription myEncryptor= new decription();
	    	 String encrypted=myEncryptor.encrypt(pass);

    		String query = "UPDATE `cache` SET `email`='"+mail+"',`pass`='"+encrypted+"' WHERE id=1";

    		try {
    			super.addUpdateRecord(query);
    			super.closeConnection();
    		} catch (Exception ex) {
    			System.out.println(ex + "updaten van nieuwe maildata is mislukt");
    		}

    	}
    	
    	public void addMailData(String eventID, String naam, String aanhef, String onderwerp, String link, String image_header, String image_subheader, String image, String content1, String header1, String header2, String content2) {
    		super.makeConnection();
    		String query = "INSERT INTO mail (eventID, naam, aanhef, onderwerp, link, image_header, image_subheader, image, content1, header1, header2, content2)"
    				+ "VALUES ('"+eventID+"','"+naam+"','"+aanhef+"','"+onderwerp+"','"+link+"','"+image_header+"','"+image_subheader+"','"+image+"','"+content1+"','"+header1+"','"+header2+"','"+content2+"')";

    		try {
    			super.addUpdateRecord(query);
    			super.closeConnection();
    		} catch (Exception ex) {
    			System.out.println(ex + "aanmaken van nieuwe maildata is mislukt");
    		}
    	}
    	public void updateMailData(int mailID, String eventID, String naam, String aanhef, String onderwerp, String link, String image_header, String image_subheader, String image, String content1, String header1, String header2, String content2) {
    		super.makeConnection();
    		String query = "UPDATE mail SET naam='"+ naam + "', aanhef='"+aanhef+"', onderwerp='"+onderwerp+"', link='"+link+"',"
    				+ "image_header='"+image_header+"', image_subheader='"+image_subheader+"', image='"+image+"', content1='"+content1+"', "
    						+ "header1='"+header1+"', header2='"+header2+"', content2='"+content2+"' WHERE id = "+mailID+"";
    		try {
    			super.addUpdateRecord(query);
    			super.closeConnection();
    		} catch (Exception ex) {
    			System.out.println(ex+ "updaten van de maildata is mislukt");
    		}
    	}
}