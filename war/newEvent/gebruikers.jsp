<%@page import="java.sql.*"%>
<%@page import="model.*"%>
<%@page import="domein.*"%>
<%@include file="../includes/login_check.jsp" %>
<%
GebruikersIO lio = new GebruikersIO();

 for(Gebruiker l :  lio.listGebruikers()){
	out.println("<option value='"+ l.getVoornaam() + " "+ l.getAchternaam() +"'>"+ l.getVoornaam() + " "+ l.getAchternaam() +"</option>");
} 

%>