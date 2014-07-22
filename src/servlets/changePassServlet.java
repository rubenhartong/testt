package servlets;

import model.*;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class changePassServlet extends HttpServlet   {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */

	public changePassServlet() {
		super();
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse response) 
			throws ServletException, IOException {
		GebruikersIO gIo 	= new GebruikersIO();
		String user 				= req.getParameter("username");
		String current 		= req.getParameter("current");
		String nieuw 			= req.getParameter("new");
		String newcheck 	= req.getParameter("newcheck");
		System.out.println("user= " + user);
		try {
			current	 = GebruikersIO.SHA1(current);
			nieuw 		= GebruikersIO.SHA1(nieuw);
			newcheck	 = GebruikersIO.SHA1(newcheck);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	if (nieuw.equals(newcheck)){
			if(gIo.Login(user, current)){
				gIo.changePass(user, nieuw);
				response.sendRedirect("changePass.jsp?message=Het wachtwoord is gewijzigd.");
			}
			else {
				response.sendRedirect("changePass.jsp?message=Het huidige wachtwoord is niet goed.");
			}
		}
		else {
			response.sendRedirect("changePass.jsp?message=De+wachtwoorden+matchen+niet.");
		}
		
		// check if current password is true
		
		// check if nieuw and check is the same
		
		//change pass
		
	
			//response.sendRedirect("index.jsp?message=Gebruikersnaam+en+of+wachtwoord+incorrect");

	}

}


