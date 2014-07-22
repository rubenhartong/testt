package servlets;
import model.*;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteContactsServlet  extends HttpServlet {


		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

		protected void doPost(HttpServletRequest req, HttpServletResponse response) 	throws ServletException, IOException {
		
			ZohoContacts contact = new ZohoContacts();
			
			contact.deleteAllContacts();

			response.sendRedirect("changeKey.jsp?message=Alle+contacten+zijn+verwijderd");
		}

	}


