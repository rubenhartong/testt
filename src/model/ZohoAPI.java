package model;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
import domein.Evenement;

public class ZohoAPI extends DbAbstract {
	// alle contacten ophalen
	public ArrayList<ContactZoho> alleContactenOphalen() {
		String apiKey =  getKey();
		ArrayList<ContactZoho> aContacts = new ArrayList<>();
		
		try {
			String authtoken = apiKey;
			String targetURL = "https://crm.zoho.com/crm/private/xml/Contacts/getRecords";
			PostMethod post = new PostMethod(targetURL);
			post.setParameter("authtoken", authtoken);
			post.setParameter("scope", "crmapi");
			post.setParameter("fromIndex", "1");
			post.setParameter("toIndex", "200");
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

				InputStream i = post.getResponseBodyAsStream();
				System.out.println("nummer word getoond: " + i.toString());
				String postResp = post.getResponseBodyAsString();

				aContacts = parseXML(postResp, true);

				/*
				 * ---------------------Get response as a string
				 * ----------------------------
				 */
				new JdbcOdbcObject();
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
		return aContacts;
	}

	// nieuwe contacten ophalen na datum van laatst geupdate.
	public ArrayList<ContactZoho> ContactenOphalenNaDatum() {
		String apiKey =  getKey();
		ArrayList<ContactZoho> aContacts = new ArrayList<>();
		String updated = "2008-05-27 00:00:00";
		Date date = null;
		try {
			super.makeConnection();

			ResultSet rs = super.makeResultSet("SELECT updated FROM cache WHERE 1");
			while (rs.next()) {
				date = rs.getTimestamp("updated");
			}
			super.closeConnectRst();
		} catch (SQLException ex) {
			System.out.println(ex);
			System.out.println("geen datum opgehaald");
		}
		updated = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
		System.out.println("******************* " + updated+ " ***************************");
		try {
			String authtoken = apiKey;
			String targetURL = "https://crm.zoho.com/crm/private/xml/Contacts/getRecords";
			PostMethod post = new PostMethod(targetURL);
			post.setParameter("authtoken", authtoken);
			post.setParameter("scope", "crmapi");
			post.setParameter("fromIndex", "1");
			post.setParameter("toIndex", "200");
			post.setParameter("lastModifiedTime", updated);// date format:
															// 2008-05-27
															// 00:00:00
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

				String postResp = post.getResponseBodyAsString();

				aContacts = parseXML(postResp, true);

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
	
	public ArrayList<ContactZoho> koppelContacten(String eventID) {
		String apiKey =  getKey();
		ArrayList<ContactZoho> aContacts = new ArrayList<>();
		
		try {
			String authtoken = apiKey;
			String targetURL = "https://crm.zoho.com/crm/private/xml/Contacts/getRelatedRecords";

			PostMethod post = new PostMethod(targetURL);
			post.setParameter("authtoken", authtoken);
			post.setParameter("scope", "crmapi");
			post.setParameter("newFormat", "1");
			post.setParameter("fromIndex", "1");
			post.setParameter("toIndex", "200");
			post.setParameter("parentModule", "Campaigns");
			post.setParameter("id", eventID);
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

				InputStream i = post.getResponseBodyAsStream();
				System.out.println("nummer word getoond: " + i.toString());
				String postResp = post.getResponseBodyAsString();

				aContacts = parseXML(postResp, true);

				/*
				 * ---------------------Get response as a string
				 * ----------------------------
				 */
				new JdbcOdbcObject();
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
		return aContacts;
	}
	
	public ArrayList<Evenement> EventsOphalen() {
		String apiKey =  getKey();
		ArrayList<Evenement> aEvents = new ArrayList<Evenement>();
	
		try {
			String authtoken = apiKey;
			String targetURL = "https://crm.zoho.com/crm/private/xml/Campaigns/getRecords";
			PostMethod post = new PostMethod(targetURL);
			post.setParameter("authtoken", authtoken);
			post.setParameter("scope", "crmapi");
			post.setParameter("fromIndex", "1");
			post.setParameter("toIndex", "200");
			//post.setParameter("lastModifiedTime", updated);
															// 2008-05-27
															// 00:00:00
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

				String postResp = post.getResponseBodyAsString();

				aEvents = parseXMLevent(postResp, true);

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				post.releaseConnection();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return aEvents;
	}

	/**
	 * Parse the supplied xml file or stream
	 * 
	 * @param xml
	 *            File location or string
	 * @param isStream
	 *            If true the parser will treat the xml as an inputstream (one
	 *            lage xml string). False means a file location
	 */
	private ArrayList<ContactZoho> parseXML(String xml, boolean isStream) {
		ArrayList<ContactZoho> array = new ArrayList<ContactZoho>();
		DocumentBuilderFactory dbf;
		DocumentBuilder db;
		Document dom;
		dbf = DocumentBuilderFactory.newInstance();
		dbf.setIgnoringElementContentWhitespace(true);
		dbf.setCoalescing(true);
		dbf.setNamespaceAware(true);

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
					case "Department":
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
				array.add(zohoContact);
			}

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return array;
	}
	
	
	
	
	private ArrayList<Evenement> parseXMLevent(String xml, boolean isStream) throws ParseException {
		//below the url for contacts related to event
		//https://crm.zoho.com/crm/private/xml/Contacts/getRelatedRecords?newFormat=1&authtoken=090f197a6e0fe85de40b7e2921e7c665&scope=crmapi&parentModule=Campaigns&id=970434000000438001
		
		ArrayList<Evenement> array = new ArrayList<Evenement>();
		DocumentBuilderFactory dbf;
		DocumentBuilder db;
		Document dom;
		dbf = DocumentBuilderFactory.newInstance();
		dbf.setIgnoringElementContentWhitespace(true);
		dbf.setCoalescing(true);
		dbf.setNamespaceAware(true);

		try {
			db = dbf.newDocumentBuilder();
			if (isStream) {
				InputSource is = new InputSource(new StringReader(xml));
				dom = db.parse(is);
			} else {
				dom = db.parse(xml);
			}

			NodeList contacts = dom.getElementsByTagName("row");
			DateHandler dh = new DateHandler();
			for (int r = 0; r < contacts.getLength(); r++) {
				Evenement event = new Evenement("","","", dh.stringToTimestamp("2000-01-01"),0, 0, "","", "", false, null);
				Element row = (Element) contacts.item(r);
				System.out.println("contact nummer " + row.getAttribute("no"));
				NodeList contact = row.getElementsByTagName("FL");

				for (int n = 0; n < contact.getLength(); n++) {
					Element item = (Element) contact.item(n);
					String fieldName = item.getAttribute("val");
					String fieldValue = item.getFirstChild().getNodeValue();
					fieldValue = fieldValue.replaceAll("\n", "");
					fieldValue = fieldValue.replaceAll("\t", "");
					fieldValue = fieldValue.trim();
					System.out.println(fieldName + " : " + fieldValue);
					
					switch (fieldName) {
					case "CAMPAIGNID":
						event.setEventID(fieldValue);
						break;
					case "Start Date":
						if(fieldValue == "0000-00-00"){
							fieldValue  = "2000-01-01";
						}
						event.setDatum(dh.stringToTimestamp(fieldValue));
						break;
					case "Campaign Name":
						event.setNaam(fieldValue);
						break;
					case "Type":
						event.setType(fieldValue);
						break;
					case "Description":
						event.setSubtitel(fieldValue);
						break;
					case "Campaign Owner":
						event.setContactPersoon(fieldValue);
						break;
					case "Modified Time":
						event.setUpdated(dh.dateTimeToDate(fieldValue));
						break;
					}

				}
				array.add(event);
			}
			

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return array;
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
	
	public String setKey(String key){
		try {
			super.makeConnection();
			super.addUpdateRecord("UPDATE `cache` SET `key`='"+ key + "' WHERE 1");
			System.out.println("Key aangepast naar: " + key);
			super.closeConnection();
		} catch (Exception ex) {
			System.out.println(ex);
		}
		
		return key;
	}
	
	
	
	public String insertOpmerking(String user, String titlea, String contenta) throws IOException{
		String contentb = contenta.replaceAll( "\n" ,"%0a%0d");
		String content = contentb.replaceAll(" ", "%20");
		String title = titlea.replaceAll(" ", "%20");
		
	String root = "https://crm.zoho.com/crm/private/xml/Notes/insertRecords?newFormat=1&authtoken="+getKey()+"&scope=crmapi&xmlData=";
	String xml = "<Notes>" +"<row%20no=\"1\">" +"<FL%20val=\"entityId\">"+user+"</FL>" +"<FL%20val=\"Note%20Title\">"+title+"</FL>" +"<FL%20val=\"Note%20Content\">"+content+"</FL>" +"</row>" +	"</Notes>";

	String urla = root+xml;
	String url = urla.replaceAll(" ", "%20");
System.out.println(url);
	//////////////////////////////////
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
		
		return xml;
	}
	
	public String insertLead(String owner_id, String owner_email, String company, String firstName, String lastName, String level,
			String email, String phone, String mobile, String description) throws IOException{
	String root = "https://crm.zoho.com/crm/private/xml/Leads/insertRecords?newFormat=1&authtoken="+getKey()+"&scope=crmapi&xmlData=";
	//String xml = "<Notes>" +"<row%20no=\"1\">" +"<FL%20val=\"entityId\">"+user+"</FL>" +"<FL%20val=\"Note%20Title\">"+title+"</FL>" +"<FL%20val=\"Note%20Content\">"+content+"</FL>" +"</row>" +	"</Notes>";
	String leadxml = "<Leads>" +
	"<row no='1'>" +
	"<FL val='SMOWNERID'>"+owner_id+"</FL>" +
	"<FL val='Lead Owner'>"+owner_email+"</FL>" +
	"<FL val='Company'>"+company+"</FL>" +
	"<FL val='First Name'>"+firstName+"</FL>" +
	"<FL val='Last Name'>"+lastName+"</FL>" +
	"<FL val='Designation'>"+level+"</FL>" +
	"<FL val='Email'>"+email+"</FL>" +
	"<FL val='Phone'>"+phone+"</FL>" +
	"<FL val='Mobile'>"+mobile+"</FL>" +
//	"<FL val='Website'>"+website+"</FL>" +
//	"<FL val='Lead Source'>External Referral</FL>" +
//	"<FL val='Lead Status'>Contacted</FL>" +
//	"<FL val='Industry'>Financial Services</FL>" +
//	"<FL val='No of Employees'>100</FL>" +
//	"<FL val='Annual Revenue'>100.0</FL>" +
//	"<FL val='Email Opt Out'>true</FL>" +
//	"<FL val='Skype ID'>peter</FL>" +
//	"<FL val='Salutation'>Mr.</FL>" +
//	"<FL val='Street'>Street One</FL>" +
//	"<FL val='City'>Chennai</FL>" +
//	"<FL val='State'>Tamil Nadu</FL>" +
//	"<FL val='Zip Code'>6000001</FL>" +
//	"<FL val='Country'>India</FL>" +
	"<FL val='Description'><![CDATA["+description+"]]></FL>" +
	"</row>" +
	"</Leads>" ;
	
	String urla = root+leadxml;
	String urlb = urla.replaceAll( "\n" ,"%0a%0d");
	String url = urlb.replaceAll(" ", "%20");
	
System.out.println(url);
	//////////////////////////////////
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

}
