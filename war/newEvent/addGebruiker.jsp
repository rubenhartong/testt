<%@page import="java.sql.*"%>
<%@page import="model.*"%>
<%@page import="domein.*"%>
<%@include file="../includes/login_check.jsp" %>

<%
NewEventIO event = new NewEventIO();

String mail 	= request.getParameter("v1");
String vn 		= request.getParameter("v2");
String an 		= request.getParameter("v3");
String tel 		= request.getParameter("v4");
String pass 	= request.getParameter("v5");
String bedrijf 	= request.getParameter("v6");

Gebruiker u = new Gebruiker(mail, vn, an, bedrijf, tel, pass, true);

String succes = event.addUser(u);

out.println(succes);
%>