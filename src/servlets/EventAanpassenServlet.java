package servlets;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.DateHandler;
import model.EvenementenIO;
import domein.Evenement;


public class EventAanpassenServlet extends HttpServlet   {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */

	public EventAanpassenServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse response) 
			throws ServletException, IOException {
		DateHandler dh = new DateHandler();
		EvenementenIO eIo = new EvenementenIO();
		String eventID = req.getParameter("eventID");
		String naam = req.getParameter("naam");
		String subTitel = req.getParameter("subTitel");
		Timestamp datum =  dh.stringToTimestamp(req.getParameter("datum"));
		int locatieID = Integer.parseInt(req.getParameter("locatie"));
		Evenement ev = eIo.getEvent(eventID);
		int programmaID = ev.getProgrammaID();
		String contactPersoon = req.getParameter("contactpersoon");
		String organisator = req.getParameter("organisator");		
		Evenement e = new Evenement(eventID, naam, subTitel, datum, locatieID, programmaID, contactPersoon, organisator, "", false, null);
		try {
			eIo.pasEvenementAan(e);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		response.sendRedirect("eDetails.jsp?eventID="+eventID);
	}
}


