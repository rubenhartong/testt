<%@page import="model.*"%>
<%@page import="java.util.*"%>
<%@page import="domein.*"%>
<%

String eventID  = request.getParameter("eventID");
String id    = request.getParameter("id");

UitnodigingIO UIo   = new UitnodigingIO();
ZohoContacts zc = new ZohoContacts();
String status = "Planned";
UIo.genodigdenToevoegen(id, eventID);
zc.AddContactToEvent(eventID, id, status);

%> 