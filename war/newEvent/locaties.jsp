<%@page import="java.sql.*"%>
<%@page import="model.*"%>
<%@page import="domein.*"%>
<%@include file="../includes/login_check.jsp" %>
<%
LocatieIO lio = new LocatieIO();


for(Locatie l :  lio.listLocaties()){
	out.println("<option name='event_adres' value='"+ l.getLocatieID() +"'>"+ l.getAdres() + " "+ l.getPlaats() +"</option>");
}

%>