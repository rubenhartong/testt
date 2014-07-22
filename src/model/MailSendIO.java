package model;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import domein.ContactZoho;
import domein.Evenement;
import domein.Locatie;
import domein.Mail;
import domein.MailFormat;
import domein.MailLogin;

public class MailSendIO extends DbAbstract {

    public MailSendIO() {
	super();
    }


///////////////////***************************verstuur de mail*************************/////////////////////
    public void constructor(String eventIDs, String mailIDs, boolean single,String id) {
    	BezoekerIO bio = new BezoekerIO();
    	ArrayList<ContactZoho> aContacts = null;
    	if(single){
    		aContacts = bio.naamBezoekers(eventIDs);
    	} else {
    		aContacts = bio.mailingBezoekers(eventIDs);
    	}
    	int mailID 			= Integer.parseInt(mailIDs);
    	
    	MailIO mIo			= new MailIO();
    	EvenementenIO eIo 	= new EvenementenIO();
    	LocatieIO lIo		= new LocatieIO();
    	OpmerkingIO oIo = new OpmerkingIO();
    	Evenement event	 	= eIo.getEvent(eventIDs); 
    	Locatie		 locatie 	= lIo.getLocatie(event.getLocatieID());
    	Mail 			mail			= mIo.getGegevens(eventIDs, mailID);
    	MailFormat format = getMailFormat(mailID); 
    	MailLogin ml				=	mIo.getMailLogin();
    	
    	String opmerking_contact 	= "Mailing";
    	String opmerking					= "Mailing met onderwerp  "+mail.getOnderwerp()+" is verstuurd";
    	
    	
    	 String message = format.getFormat();
    	 
    	 message = message.replaceAll( "\\{background-image\\}" , mail.getImage());
    	message = message.replaceAll( "\\{background-image\\}" , mail.getImage());
    	message = message.replaceAll( "\\{image-header\\}" , mail.getImage_header());
    	message = message.replaceAll( "\\{image-subheader\\}" , mail.getImage_subheader());
    	message = message.replaceAll( "\\{header1\\}" , mail.getHeader1());
    	message = message.replaceAll( "\\{content1\\}" , mail.getContent1());
    	message = message.replaceAll( "\\{header2\\}" , mail.getHeader2());
    	message = message.replaceAll( "\\{content2\\}" , mail.getContent2());
    	message = message.replaceAll( "\\{aanhef\\}" , mail.getAanhef());
    	
    	message = message.replaceAll( "\\{element1\\}" , format.getElement1());
    	message = message.replaceAll( "\\{element2\\}" , format.getElement2());
    	message = message.replaceAll( "\\{element3\\}" , format.getElement3());
    	
    	message = message.replaceAll( "\\{adres\\}" , locatie.getAdres());
    	message = message.replaceAll( "\\{postcode\\}" , locatie.getPostcode());
    	message = message.replaceAll( "\\{plaats\\}" , locatie.getPlaats());
    	message = message.replaceAll( "\\{telefoon\\}" , locatie.getTelefoonnummer());
    	message = message.replaceAll( "\\{website\\}" , locatie.getWebsite() + "?id={id}");
    	message = message.replaceAll( "\\{link\\}" , mail.getLink());
    	
    	 for (ContactZoho c : aContacts) {
    		 String m = message;
    		 m = m.replaceAll( "\\{id\\}" ,c.getId());
    		 m = m.replaceAll( "\\{voornaam\\}" ,c.getVoornaam());
    		 m = m.replaceAll( "\\{achternaam\\}" ,c.getAchternaam());
    		 m = m.replaceAll( "\\{email\\}" ,c.getMail());
    		 m = m.replaceAll( "\\{bedrijf\\}" ,c.getBedrijf());
    		 m = m.replaceAll( "\\{contactpersoon\\}" ,c.getContactpersoon());
    		 m = m.replaceAll( "\\{functie\\}" ,c.getFunctie());
    		 String onderwerp = mail.getOnderwerp().replaceAll( "\\{voornaam\\}" ,c.getVoornaam());
    		 onderwerp = onderwerp.replaceAll( "\\{achternaam\\}" , c.getAchternaam());
		 
    		 if(single){
    			 if(c.getId().equals(id)) {
    			 SendMail(ml.getEmail(),ml.getEmail(), ml.getPass(), onderwerp, c.getMail(), m);
    			 oIo. insertOpmerking( id ,  opmerking, opmerking_contact  ,  eventIDs);
    			 System.out.println(c.getVoornaam() + " " + c.getAchternaam());
    		} 
    		 } else {
	    		 SendMail(ml.getEmail(),ml.getEmail(), ml.getPass(),  onderwerp, c.getMail(), m);
	    		 oIo. insertOpmerking( c.getId() ,  opmerking, opmerking_contact  ,  eventIDs);
	    		 System.out.println(c.getVoornaam() + " " + c.getAchternaam());
    		 }
    		 // voeg status update toe.
    	 }

    }
    
    
    
    
    public String SendMail(String sender, String useru, String passw, String subject, String receiver, String msg) {
    		String s = "Uw bericht is verstuurd naar:" ;
    	   
    	   try {
    	/*Retrieve value from the text field using getParameter() method on Request object. Otherwise you can set it directly also if you are not using any interface */ 
  	
	         final String user	= useru;
    	     final String pass	= passw;
    	     //create an instance of Properties Class     
    	     Properties props = new Properties();
    	     
    	/* Specifies the IP address of your default mail server
    	  for e.g if you are using gmail server as an email sever
    	  you will pass smtp.gmail.com as value of mail.smtp host. As shown here in the coding. Change accordingly, if your email id is not an gmail id*/
    	    
    	     props.put("mail.smtp.auth", "true");
    			props.put("mail.smtp.starttls.enable", "true");
    			props.put("mail.smtp.host", "smtp.gmail.com");
    			props.put("mail.smtp.port", "587");

    	     
    	 /*Pass Properties object(props) and Authenticator object   for authentication to Session instance */     

    	    Session session = Session.getInstance(props,
    	                        new javax.mail.Authenticator() {
    	  protected PasswordAuthentication getPasswordAuthentication() {
    	   return new PasswordAuthentication(user,pass);
    	   }
    	});
    	 /* Create an instance of MimeMessage, it accept MIME types and headers */    
    	 MimeMessage message = new MimeMessage(session);
    	 message.setFrom(new InternetAddress(sender, sender));
   
    	 message.setSubject(subject);
 
    	message.addRecipient(Message.RecipientType.TO,new InternetAddress(receiver));
    	 message.setContent(msg, "text/html; charset=utf-8");
    	 /* Transport class is used to deliver the message to the recipients */
    	 Transport.send(message);
    	        }catch(Exception e){
    	        	System.out.println(e + "Mailen mislukt");
    	        	s = "verkeerd email adres ingevoerd: ";
    	   e.printStackTrace(); 
    	  }
    	   
    	   return s;
    	
    }
    
    
    	///******************************* Get gegevens van de mail ******************************************//
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
    	
    	public MailFormat getMailFormat(int mailID){
    		MailFormat m = new MailFormat(mailID, "", "", "", "", "");
    		mailID = 1;
    		try {
    		    super.makeConnection();
    		    // Deze query gaat wat fout mee, eventID word er dubbel ingezet
    		    ResultSet rs = super.makeResultSet("SELECT * FROM mailFormat WHERE mailID="+mailID+"");
    		    while (rs.next()) {
    		    	m.setNaam( rs.getString("naam"));
    		    	m.setFormat( rs.getString("format"));
    		    	m.setElement1( rs.getString("element1"));
    		    	m.setElement2( rs.getString("element2"));
    		    	m.setElement3( rs.getString("element3"));
    			}
    		    super.closeConnectRst();
    		}
    		catch(Exception e){
    			System.out.print(e + "ophalen van mailformat door middel van mailID= "+mailID+" mislukt");
    		}
    		return m;
    	}
 
}