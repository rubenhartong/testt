<%@page import="model.*"%>
<%@page import="domein.*"%>
<%@page import="java.util.*"%>
<%@include file="../includes/login_check.jsp"%>

<%
	String from 			= request.getParameter("from");
	String to 				= request.getParameter("to");
	String subject 		= request.getParameter("subject");			
	String message 		= request.getParameter("message").replaceAll( "\n" ,"<br />");
	
	
	
	
	MailSendIO mio = new MailSendIO();
	MailIO mo = new MailIO();
	MailLogin ml = mo.getMailLogin();
	String outcome = mio.SendMail(from,ml.getEmail(), ml.getPass(), subject, to, message);
	out.println(outcome + "\n" + to);
%>
