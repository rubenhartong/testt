package servlets;
import model.*;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domein.ContactZoho;

public class syncContacts  extends HttpServlet {


		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

		protected void doPost(HttpServletRequest req, HttpServletResponse response) 	throws ServletException, IOException {
		
			ZohoContacts contact = new ZohoContacts();
			ArrayList<ContactZoho> aContacts = new ArrayList<ContactZoho>();
			System.out.println("lets begin");
			//contact.deleteAllContacts();
			contact.construct(aContacts);
			

			response.sendRedirect("changeKey.jsp?message=Alle+contacten+zijn+vervangen");
		}

	}


