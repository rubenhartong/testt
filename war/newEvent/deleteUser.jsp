<%@page import="java.sql.*"%>
<%@page import="model.*"%>
<%@page import="domein.*"%>
<%@include file="../includes/login_check.jsp" %>

<%
GebruikersIO gio = new GebruikersIO();

String mail 	= request.getParameter("email");

gio.deleteGebruiker(mail);

%>