<%@page import="model.*" %>
<%@page import="domein.*"%>
<%@include file="/includes/login_check.jsp"%>
			
		<%
		String owner_id				= request.getParameter("owner_id");
		String owner_email 		= request.getParameter("owner_email");
		String company 			= request.getParameter("company");
		String firstName 			= request.getParameter("firstName");
		String lastName 			= request.getParameter("lastName");
		String level 					= request.getParameter("level");
		String email 					= request.getParameter("email");
		String phone 					= request.getParameter("phone");
		String mobile 				= request.getParameter("mobile");
		String description 		= request.getParameter("description");
		
		
		ZohoAPI za = new ZohoAPI(); 
		za.insertLead(owner_id, owner_email, company, firstName, lastName, level, email, phone, mobile, description);
	  	%>
	  	