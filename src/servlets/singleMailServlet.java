package servlets;

import model.*;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class singleMailServlet extends HttpServlet   {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */

	public singleMailServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse response) 	throws ServletException, IOException {
		String mailID 				= req.getParameter("mailid");		
		String genodigdeID	= req.getParameter("genodigdeID");		
		String eventID	= req.getParameter("eventID");	
		System.out.println(mailID + genodigdeID + eventID);
		
		MailSendIO mio= new MailSendIO();
		mio.constructor(eventID, mailID, true, genodigdeID);
		
			response.sendRedirect("status/status.jsp?eventID="+eventID);		
	}

}


