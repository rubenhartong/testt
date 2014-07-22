package servlets;

import model.*;
import domein.Evenement;
import domein.Programma;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ProgrammaToevoegenServlet extends HttpServlet   {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */

	public ProgrammaToevoegenServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse response) 
			throws ServletException, IOException {
		
		String eventID = (String) req.getSession().getAttribute("eventID");
		req.getSession().removeAttribute("eventID");
		ProgrammaIO pIo = new ProgrammaIO();
		EvenementenIO eIo = new EvenementenIO();
		Evenement e = eIo.getEvent(eventID);
		
		int pId = e.getProgrammaID();
		String startTijd = req.getParameter("startTijd");
		String eindTijd = req.getParameter("eindTijd");
		String programma = req.getParameter("programma");
		Programma prog = new Programma(pId, "", "", "");
		
		for(Programma p : pIo.listProgramma()){
			if(p.getProgrammaID()==pId){
				prog = p;
			}
		}
		if(pIo.programmaBestaat(pId) == true){		
			prog.setVanTijd(startTijd);
			prog.setTotTijd(eindTijd);
			prog.setProgrammatxt(programma);
			pIo.updateProgramma(prog);
		}else{
			pIo.voegProgrammaToe(startTijd, eindTijd, programma);			
			int programmaID = 0;
			for(Programma p : pIo.listProgramma()){
				if(p.getProgrammatxt().equals(programma)){
					programmaID = p.getProgrammaID();
				}
			}
			eIo.linkProgramma(eventID, programmaID);
		}
		
		response.sendRedirect("eDetails.jsp?eventID="+eventID+"&message=Programma+toegevoegd.");
	}
}


