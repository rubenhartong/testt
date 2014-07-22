package model;

import java.io.IOException;
import java.io.StringReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;

import sun.jdbc.odbc.JdbcOdbcObject;
import domein.ContactZoho;

public class ZohoContacts extends DbAbstract {
	private int firstContact = 1;
	private String apiKey =  getKey();
	// alle contacten ophalen
	
	public void construct (ArrayList<ContactZoho> aContacts){
		alleContactenOphalen(aContacts);
	}
	
	public void deleteAllContacts() {
		System.out.println("delete all contacts");
		try {
			super.makeConnection();			
				String queryDelete = "TRUNCATE TABLE contacts";
				try {
					
					 super.makeResultSetUpdate(queryDelete);
				} catch (Exception e) {
					System.out.println("Query error name: Truncate contacts and error: "+ e);
				}
			} finally {
				super.closeConnection();
				System.out.println("all contacts are gone");
			}
	}
	public ArrayList<ContactZoho> alleContactenOphalen(ArrayList<ContactZoho> aContacts) {
		String lastContact  = Integer.toString(firstContact +200);
		try {
			String authtoken = apiKey;
			String targetURL = "https://crm.zoho.com/crm/private/xml/Contacts/getRecords";
			PostMethod post = new PostMethod(targetURL);
			post.setParameter("authtoken", authtoken);
			post.setParameter("scope", "crmapi");
			post.setParameter("fromIndex", Integer.toString(firstContact));
			post.setParameter("toIndex", lastContact);
			HttpClient httpclient = new HttpClient();

			/*-------------------------------------- Execute the http request--------------------------------*/
			try {
				long t1 = System.currentTimeMillis();
				int result = httpclient.executeMethod(post);
				System.out.println("HTTP Response status code: " + result);
				System.out.println(">> Time taken "	+ (System.currentTimeMillis() - t1));

				/*-------------------------------------- Execute the http request--------------------------------*/

				/*-----------------------Get response as a string ----------------*/

				String postResp = post.getResponseBodyAsString();

				parseXML(postResp, true, aContacts);

				/*
				 * ---------------------Get response as a string
				 * ----------------------------
				 */
				new JdbcOdbcObject();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				post.releaseConnection();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
return aContacts;
	}
	
	private ArrayList<ContactZoho> parseXML(String xml, boolean isStream, ArrayList<ContactZoho> aContacts) {
		DocumentBuilderFactory dbf;
		DocumentBuilder db;
		Document dom;
		dbf = DocumentBuilderFactory.newInstance();
		dbf.setIgnoringElementContentWhitespace(true);
		dbf.setCoalescing(true);
		dbf.setNamespaceAware(true);
		int aantal = 0;

		try {
			db = dbf.newDocumentBuilder();
			if (isStream) {
				InputSource is = new InputSource(new StringReader(xml));
				dom = db.parse(is);
			} else {
				dom = db.parse(xml);
			}

			NodeList contacts = dom.getElementsByTagName("row");
			for (int r = 0; r < contacts.getLength(); r++) {
				System.out.println("test"+ firstContact);
				firstContact++;
				aantal ++;
				
				ContactZoho zohoContact = new ContactZoho("","", "", "","","", "", "","", "", "", 0, 0, 0 ,0,0, true);
				Element row = (Element) contacts.item(r);
				System.out.println("contact nummer " + row.getAttribute("no"));
				NodeList contact = row.getElementsByTagName("FL");

				for (int n = 0; n < contact.getLength(); n++) {
					Element item = (Element) contact.item(n);
					String fieldName = item.getAttribute("val");
					String fieldValue = item.getFirstChild().getNodeValue();
					fieldValue = fieldValue.replaceAll("\n", "");
					fieldValue = fieldValue.replaceAll("\t", "");
					fieldValue = fieldValue.replaceAll( "'", "\\\\'");
					fieldValue = fieldValue.trim();
					System.out.println(fieldName + " : " + fieldValue);
					switch (fieldName) {
					case "CONTACTID":
						zohoContact.setId(fieldValue);
						break;
					case "First Name":
						zohoContact.setVoornaam(fieldValue);
						break;
					case "Last Name":
						zohoContact.setAchternaam(fieldValue);
						break;
					case "Email":
						zohoContact.setMail(fieldValue);
						break;
					case "Title":
						zohoContact.setFunctie(fieldValue);
						break;
					case "Function Level":
						zohoContact.setFunctie_level(fieldValue);
						break;
					case "Function Domain":
						zohoContact.setFunctie_domain(fieldValue);
						break;
					case "Account Name":
						zohoContact.setBedrijf(fieldValue);
						break;
					case "Phone":
						zohoContact.setTelefoon(fieldValue);
						break;
					case "Mobile":
						zohoContact.setMobiel(fieldValue);
						break;
					case "Contact Owner":
						zohoContact.setContactpersoon(fieldValue);
						break;
					case "Email Opt Out":
						zohoContact.setOptOut(Boolean.parseBoolean(fieldValue));
						break;
					}

				}
				aContacts.add(zohoContact);
			}

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (aantal >199){
			construct(aContacts);
		}
		else {
			database(aContacts);
		}
		
		return aContacts;
	}

	// / ***********Get the current date in timestamp ************
	
	public void database(ArrayList<ContactZoho> aContacts) {
		try {
			super.makeConnection();			
			for(ContactZoho g : aContacts) {
			String	functie  = g.getFunctie().replaceAll( "'" ,"\\\\'");
				String query = "INSERT INTO contacts(id, voornaam, achternaam,email,functie,functie_level, functie_domain, bedrijf,telefoon, mobiel, contactpersoon)"
						+ "VALUES("+g.getId()+", '"+g.getVoornaam().replaceAll( "'" ,"\\\\'")+"', '"+g.getAchternaam().replaceAll( "'" ,"\\\\'")+"', '"+g.getMail()
						+"','"+functie+"','"+g.getFunctie_level()+"','"+g.getFunctie_domain()+"','"+g.getBedrijf().replaceAll( "'" ,"\\\\'")+"','"+g.getTelefoon()+"', '"+g.getMobiel()+"', '"+g.getContactpersoon().replaceAll( "'" ,"\\\\'")+"')";		
							
				try {
					 super.makeResultSetUpdate(query);
				} catch (Exception e) {
					System.out.println("Query error name: "+ g.getMail() +"and error: "+ e);
				}
			}
			} finally {
				super.closeConnection();
			}
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
	}
	
	public String AddContactToEvent(String eventID, String contactID, String status) throws IOException{
	String root = "https://crm.zoho.com/crm/private/xml/Campaigns/updateRelatedRecords?authtoken=090f197a6e0fe85de40b7e2921e7c665&scope=crmapi&relatedModule=Contacts&xmlData=";
	
	String leadxml = "<Contacts><row no=\"1\"><FL val=\"CONTACTID\">"+contactID+"</FL><FL val=\"member_status\">"+status+"</FL></row></Contacts>&id="+eventID+"";
		
	
	String urla = root+leadxml;
	String url = urla.replaceAll(" ", "%20");

	try {
		PostMethod post = new PostMethod(url);
		HttpClient httpclient = new HttpClient();

		/*-------------------------------------- Execute the http request--------------------------------*/
		try {
			long t1 = System.currentTimeMillis();
			int result = httpclient.executeMethod(post);
			System.out.println("HTTP Response status code: " + result);
			System.out.println(">> Time taken "
					+ (System.currentTimeMillis() - t1));

			/*-------------------------------------- Execute the http request--------------------------------*/

			/*-----------------------Get response as a string ----------------*/

			System.out.println("nummer word getoond: " + post.getResponseBodyAsString());
			/*
			 * ---------------------Get response as a string
			 * ----------------------------
			 */
			{
				// generate new auth token and call the API
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			post.releaseConnection();
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	
	//////////////////////////////
		
		return leadxml;
	}
	
	public String getKey(){
		String key = null;
		try {
			super.makeConnection();
			ResultSet rs = super.makeResultSet("SELECT `key` FROM `cache` WHERE 1");
			while (rs.next()) {
				key = rs.getString("key");
			}
			super.closeConnectRst();
		} catch (SQLException ex) {
			System.out.println(ex);
		}
		
		return key;
	}
}
