<!DOCTYPE html>
<%@page import="model.*" %>
<%@page import="domein.*" %>
<%@page import="java.text.SimpleDateFormat" %>
<%@page import="java.text.DateFormat" %>
<%@page import="java.util.ArrayList" %>
<%@page import="java.util.Date" %>
<%@include file="../includes/login_check.jsp"%>
<% String eventID = request.getParameter("eventID"); 
	EvenementenIO eIo = new EvenementenIO();
	Evenement e = eIo.getEvent(eventID);
	ProgrammaIO pIo = new ProgrammaIO();
	Programma p = pIo.getProgramma(e.getProgrammaID());
	LocatieIO lIo = new LocatieIO(); 
	String locatie = "";
	int id = e.getLocatieID();
	for(Locatie l : lIo.listLocaties()){
		if(l.getLocatieID() == id)
			locatie = l.getAdres()+" "+l.getPlaats();
	}
%>
<html>

<head>
<title>Incentro Event Inviting System</title> 
<%@include file="../includes/head.html"%>
<link rel="stylesheet" type="text/css" href="../assets/css/inviteSystem.css" />

</head>

<body>

	<div class="wrapper">
		<div class="header">
			<a href="../eOverzicht.jsp" id="logo"><img src="../assets/img/logo.png" /></a>
		</div>
		
		<h3>Details aanpassen - <%out.print(e.getNaam()); %></h3>
		
		<%@include file="../includes/menu_back.html"%>
			
		<form class="opstelForm" action="../EventAanpassen.do" method="post" >
			<div class="child bottom noheight">				
				
				<label for="contactpersoon">Contactpersoon</label> 
					<select name="contactpersoon">
					<% 
					GebruikersIO gIo = new GebruikersIO();
						DateHandler dh = new DateHandler();
						String datum = dh.uniDate(e.getDatum());
						out.println("<option value='"+e.getContactPersoon()+"' >"+e.getContactPersoon()+"</option>");
						out.println("<option disabled='disabled' >events contacten</option>");
						for(Gebruiker g : gIo.listGebruikers()){
					%>
						<option value="<%out.print(g.getVoornaam() +" "+g.getAchternaam()); %>"><%out.print(g.getVoornaam() +" "+g.getAchternaam()); %></option>
						<%} 
						
				  			ZohoUsers zc = new ZohoUsers();
				  			ArrayList<Gebruiker> aUsers = zc.alleGebruikersOphalen();
				  			out.println("<option disabled='disabled' >Zoho contacten</option>");
				  			for(Gebruiker l :  aUsers){
				  				out.println("<option value='"+l.getVoornaam()+" "+ l.getAchternaam()+"' >"+l.getVoornaam()+" "+ l.getAchternaam()+"</option>");
				  			}
						%>
						
					</select> 
				    <br /><br />
					<label for="naam">Titel event</label> <input type="text" id="naam" name="naam" value="<%out.print(e.getNaam());%>"/><br />				 
					<label for="naam">Subtitel</label> <input type="text" id="subTitel" name="subTitel" value="<%out.print(e.getSubtitel());%>"  /><br />
					<label for="datum">Datum</label> <input type="date" name="datum" id="datum" value="<%out.print(datum); %>" /><br />				
					<label for="organisator">Organisator</label> <input type="text" name="organisator" id="organisator" value="<%out.print(e.getOrganisator());%>" /><br />
					<label for="locatie">Locatie</label> 
					<select name="locatie"><option value="<%out.print(id); %>" ><%out.print(locatie); %></option>
<%
						for(Locatie l : lIo.listLocaties()){
					%>
						<option value="<%out.print(l.getLocatieID());%>"><%out.print(l.getAdres()+", "+l.getPlaats()); %></option>
						<%} %>
					</select><a href="../newEvent/newLocation.jsp">Locatie toevoegen</a><br /><br />
				<input type="submit" value="Pas aan" id="form_submit1" />
            <input type="hidden" name="eventID" id="eventID" value="<%out.print(eventID); %>" />
            </div>
        </form> 
			</div>	
	
</body>

</html>