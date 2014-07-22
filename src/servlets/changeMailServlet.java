package servlets;

import model.*;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class changeMailServlet extends HttpServlet   {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */

	public changeMailServlet() {
		super();
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse response) 
			throws ServletException, IOException {
		MailIO mio = new MailIO();
		String user 				= req.getParameter("email");
		String nieuw 		= req.getParameter("pass");
		String newcheck 			= req.getParameter("passcheck");
		
	if (nieuw.equals(newcheck)){
			try {
				mio.setLogin(user, nieuw);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			response.sendRedirect("changeMailing.jsp?message=Het+emailadres+is+gewijzigd.");
		}
		else {
			response.sendRedirect("changeMailing.jsp?message=De+wachtwoorden+matchen+niet.");
		}

	}

}


