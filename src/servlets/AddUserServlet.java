package servlets;

import model.*;
import domein.Gebruiker;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class AddUserServlet extends HttpServlet   {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */

	public AddUserServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse response) 
			throws ServletException, IOException {
		GebruikersIO g = new GebruikersIO();
		String email = req.getParameter("email");
		String voornaam = req.getParameter("voornaam"); 
		String achternaam = req.getParameter("achternaam");
		String bedrijf = req.getParameter("bedrijf");
		String telefoonnummer = req.getParameter("telefoonnummer");
		String wachtwoord = req.getParameter("wachtwoord");
		String wachtwoordCheck = req.getParameter("wachtwoordCheck");
		
		try {
			wachtwoord = GebruikersIO.SHA1(wachtwoord);
			wachtwoordCheck = GebruikersIO.SHA1(wachtwoordCheck);
		}catch (NoSuchAlgorithmException e) {
			e.printStackTrace();  
			System.out.println("fout in SHA" + e);
			} 
		catch (UnsupportedEncodingException e) {
			e.printStackTrace();   
			System.out.println("fout in SHA" + e);
			} 
		
		if(wachtwoord.equals(wachtwoordCheck)){
			Gebruiker geb = new Gebruiker(email, voornaam, achternaam, bedrijf, telefoonnummer, wachtwoord); 
					
			g.AddGebruikerUsr(geb);
			response.sendRedirect("eOverzicht.jsp?message=Gebruiker+toegevoegd.");
		}
		else{
			response.sendRedirect("voegGebruikerToe.jsp?message=Wachtwoorden+komen+niet+overeen.");
		}
		
		
	}

}


