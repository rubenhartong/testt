<%@page import="model.*"%>
<%@page import="java.util.*"%>
<%@page import="domein.*"%>
<%

String eventID  = request.getParameter("eventID");
String id    = request.getParameter("id");

UitnodigingIO uIo   = new UitnodigingIO();
uIo.genodigdenVerwijderen(id, eventID);

ZohoContacts zc = new ZohoContacts();
String status = "Refused";
zc.AddContactToEvent(eventID, id, status);
%>