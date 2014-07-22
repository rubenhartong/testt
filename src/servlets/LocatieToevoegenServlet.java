package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.LocatieIO;
import domein.Locatie;

public class LocatieToevoegenServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */

    public LocatieToevoegenServlet() {
	super();
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
	LocatieIO lIo = new LocatieIO();
	String adres = req.getParameter("adres") + " "+  req.getParameter("nr");
	String plaats = req.getParameter("plaats");
	String postcode = req.getParameter("postcode") + "" + req.getParameter("letters");
	String telefoonnummer = req.getParameter("telefoonnummer");
	String website = req.getParameter("website");

	if (website.length() > 7 && website.substring(0, 7) != "http://") {
	    website = "http://" + website;
	}


	 Locatie l = new Locatie(0, adres, plaats, postcode, telefoonnummer,website);
	 lIo.voegLocatieToe(l);
	response.sendRedirect("./eOverzicht.jsp?locatie+toegevoegd");	

    }
}
