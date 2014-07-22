package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.ZohoAPI;


public class ChangeKey extends HttpServlet   {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */

	public ChangeKey() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse response) 
			throws ServletException, IOException {
		ZohoAPI za = new ZohoAPI();
		String key = req.getParameter("key");		
		za.setKey(key);
		
			response.sendRedirect("eOverzicht.jsp?message=API+key+aangepast.");		
	}

}


