<%@page import="model.*" %>
<%@page import="domein.*"%>
<%@include file="/includes/login_check.jsp"%>
			
		<%
		OpmerkingIO oIo = new OpmerkingIO();
	
	  	String name = request.getParameter("name");
	  	String eventID = request.getParameter("eventID");
	  	String value =request.getParameter("value");
	  	String contact	= request.getParameter("gebruikerID");

	  	oIo.setStatus(eventID, contact, name, value);
	  	%>

	  	