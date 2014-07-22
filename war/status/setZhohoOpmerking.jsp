<%@page import="model.*" %>
<%@page import="domein.*"%>
<%@include file="/includes/login_check.jsp"%>
			
		<%
		String contact		= request.getParameter("contact");
		String title 				= request.getParameter("title");
		String description = request.getParameter("description");
		
		ZohoAPI za = new ZohoAPI(); 
		za.insertOpmerking(contact, title, description);
	  	%>
