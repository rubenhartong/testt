<%@include file="/includes/login_check.jsp"%>
<%@page import="model.*"%>
<%@page import="domein.*"%>
<%
	String eventID 		=request.getParameter("eventID");
String mailID 			= request.getParameter("mailID");

MailSendIO mio= new MailSendIO();
mio.constructor(eventID, mailID, false,"");
%>
