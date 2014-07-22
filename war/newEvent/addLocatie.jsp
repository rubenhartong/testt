<%@page import="java.sql.*"%>
<%@page import="model.*"%>
<%@page import="domein.*"%>
<%@include file="../includes/login_check.jsp" %>
 
<%
NewEventIO event = new NewEventIO();

String adres 			= request.getParameter("v1");
String plaats 			= request.getParameter("v2");
String postcode 	= request.getParameter("v3");
String website 		= request.getParameter("v4");
String tel 				= request.getParameter("v5");

Locatie l = new Locatie(1, adres, plaats, postcode, tel, website); 

String succes = event.addLocation(l);

out.println(succes);
%>