<%@page import="model.*"%>
<%@page import="java.util.*"%>
<%@include file="../includes/login_check.jsp"%>

<%
	String eventID = request.getParameter("eventID");
	String genodigdeID = request.getParameter("genodigdeID");
	String mailID 				= request.getParameter("mailid");			
	
	MailSendIO mio = new MailSendIO();
	mio.constructor(eventID, mailID, true, genodigdeID);
	out.println("De mail is verstuurd");
%>
