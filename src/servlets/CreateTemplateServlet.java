package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.TemplateIO;

public class CreateTemplateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public CreateTemplateServlet() {
	super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	TemplateIO mIo = new TemplateIO();

	int mailID 				= Integer.parseInt(request.getParameter("mailID"));

	String naam				= request.getParameter("naam");
	Boolean active 			= Boolean.valueOf(request.getParameter("exist"));  
	boolean exist 			= active.booleanValue();  
	String format				= request.getParameter("format");
	String element1			= request.getParameter("element1");
	String element2			= request.getParameter("element2");
	String element3			= request.getParameter("element3");
	
	naam = naam.replaceAll( "'" ,"\\\\'");
	format = format.replaceAll( "'" ,"\\\\'");
	element1 = element1.replaceAll( "'" ,"\\\\'");
	element2 = element2.replaceAll( "'" ,"\\\\'");
	element3 = element3.replaceAll( "'" ,"\\\\'");
	
	naam = naam.replaceAll( "\"" ,"\\\\\"");
	format = format.replaceAll( "\"" ,"\\\\\"");
	element1 = element1.replaceAll( "\"" ,"\\\\\"");
	element2 = element2.replaceAll( "\"" ,"\\\\\"");
	element3 = element3.replaceAll( "\"" ,"\\\\\"");
		//update query
	if(!exist){
	mIo.addMailData(naam, format, element1, element2, element3);
	}else {
		mIo.updateMailData(mailID, naam,  format, element1, element2, element3);
	}
	response.sendRedirect("Aanmaken/mail_template.jsp");
    }
}
