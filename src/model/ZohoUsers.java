package model;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import domein.Gebruiker;

public class ZohoUsers extends DbAbstract {
	// alle gebruikers ophalen
	public ArrayList<Gebruiker> alleGebruikersOphalen() {
		String apiKey =  getKey();
		ArrayList<Gebruiker> aContacts = new ArrayList<>();
		
		try {
			String authtoken = apiKey;
			String targetURL = "https://crm.zoho.com/crm/private/xml/Users/getUsers";
			PostMethod post = new PostMethod(targetURL);
			post.setParameter("authtoken", authtoken);
			post.setParameter("scope", "crmapi");
			post.setParameter("type", "ActiveUsers");
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
				//new JdbcOdbcObject();
				//{
					// generate new auth token and call the API
				//}
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
	public ArrayList<Gebruiker> gebruikersOphalenNaDatum() {
		String apiKey =  getKey();
		ArrayList<Gebruiker> aContacts = new ArrayList<>();
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

				java.sql.Timestamp timeStamp =  new java.sql.Timestamp(date.getTime());
				try {
					super.addUpdateRecord("UPDATE cache SET updated='"
							+ timeStamp + "'");
					System.out.println("date set at: " + timeStamp);
					super.closeConnection();

				} catch (Exception e) {
 
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

	/**
	 * Parse the supplied xml file or stream
	 * 
	 * @param xml
	 *            File location or string
	 * @param isStream
	 *            If true the parser will treat the xml as an inputstream (one
	 *            lage xml string). False means a file location
	 */
	private ArrayList<Gebruiker> parseXML(String xml, boolean isStream) {
		ArrayList<Gebruiker> array = new ArrayList<Gebruiker>();
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

			NodeList contacts = dom.getElementsByTagName("users");
			for (int r = 0; r < contacts.getLength(); r++) {
				Element row = (Element) contacts.item(r);
				System.out.println("contact nummer " + row.getAttribute("no"));
				NodeList contact = row.getElementsByTagName("user");

				for (int n = 0; n < contact.getLength(); n++) {
					Element item = (Element) contact.item(n);
					
					String id = item.getAttribute("id");
					String email = item.getAttribute("email");	
					String name = item.getFirstChild().getNodeValue();
					System.out.println("id: " + id +" \nEmail: " + email + " \n naam: " + name);
					
					Gebruiker zc = new Gebruiker(id,email, name, "", "", "");
					array.add(zc);
				}
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
	

}
