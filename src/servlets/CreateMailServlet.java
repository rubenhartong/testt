package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.MailIO;

public class CreateMailServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public CreateMailServlet() {
	super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	MailIO mIo = new MailIO();

	String eventID 			= request.getParameter("eventID");
	int mailID 				= Integer.parseInt(request.getParameter("mailID"));

	String naam				= request.getParameter("naam");
	String onderwerp 		= request.getParameter("onderwerp");
	Boolean active 			= Boolean.valueOf(request.getParameter("exist"));  
	boolean exist 			= active.booleanValue();  
	String aanhef			= request.getParameter("aanhef");
	String link				= request.getParameter("link");
	String image_header		= request.getParameter("image_header");
	String image_subheader	= request.getParameter("image_subheader");
	String image			= request.getParameter("image");
	String header1			= request.getParameter("header1");
	String header2			= request.getParameter("header2");
	String content1			= request.getParameter("content1");
	String content2			= request.getParameter("content2");
	
	content1 = content1.replaceAll( "\n" ,"<br />");
	content2 = content2.replaceAll( "\n" ,"<br />");
	
	naam = naam.replaceAll( "'" ,"\\\\'");
	onderwerp = onderwerp.replaceAll( "'" ,"\\\\'");
	aanhef = aanhef.replaceAll( "'" ,"\\\\'");
	header1 = header1.replaceAll( "'" ,"\\\\'");
	header2 = header2.replaceAll( "'" ,"\\\\'");
	content1 = content1.replaceAll( "'" ,"\\\\'");
	content2 = content2.replaceAll( "'" ,"\\\\'");

		//update query
	if(!exist){
	mIo.addMailData(eventID, naam, aanhef, onderwerp, link, image_header, image_subheader, image, content1, header1, header2, content2);
	}else {
		mIo.updateMailData(mailID, eventID, naam, aanhef, onderwerp, link, image_header, image_subheader, image, content1, header1, header2, content2);
	}
	response.sendRedirect("mailen.jsp?eventID="+eventID);
    }
}
