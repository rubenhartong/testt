<%@page import="model.*"%>
<%@page import="java.util.*"%>
<%@page import="domein.*"%>
<%

String eventID  = request.getParameter("eventID");

ZohoAPI za= new ZohoAPI();
UitnodigingIO UIo   = new UitnodigingIO();

ArrayList<ContactZoho> aContacts = new ArrayList<ContactZoho>() ;

aContacts = za.koppelContacten(eventID);


for(ContactZoho l :  aContacts){
	UIo.genodigdenToevoegen(l.getId(), eventID);
}
%> 