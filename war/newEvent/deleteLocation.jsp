<%@page import="java.sql.*"%>
<%@page import="model.*"%>
<%@page import="domein.*"%>
<%@include file="../includes/login_check.jsp" %>

<%
LocatieIO lio = new LocatieIO();
out.println(lio.deleteLocatie(request.getParameter("locatieID")));
%>