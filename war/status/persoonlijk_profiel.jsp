<%@include file="/includes/login_check.jsp"%>
<%@page import="model.*" %>
<%@page import="domein.*" %>
<%@page import="java.util.ArrayList" %>
<%
	String genodigdeID = request.getParameter("genodigdeID");
	String eventID =request.getParameter("eventID");
	GenodigdenIO gio = new GenodigdenIO();
	OpmerkingIO oio = new OpmerkingIO();
	ContactZoho c = gio.getContactData(genodigdeID, eventID);
	MailIO mio = new MailIO();

	ArrayList<Mail> mail = mio.getEventsGegevens(eventID);
%>
<!DOCTYPE html>
<html>

<head>
<title>Incentro Event Inviting System</title>
<%@include file="/includes/head.html" %>

<link rel="stylesheet" type="text/css" href="../assets/css/inviteSystem.css" />
</head>

<body>

	<div class="wrapper">
	
		<div class="header">
			<a href="../eOverzicht.jsp" class="logo"><img src="../assets/img/logo.png" /></a>
		</div>	
		<h3><% out.print(c.getVoornaam() + " " + c.getAchternaam()); %></h3>
            <%@include file="/includes/menu_back.html" %>
	  	
		<!-- *****************Formulier van de status wijziging**************************** -->
		
		<form>
		<!-- *****************CONTACTGEGEVENS***************** -->
		<fieldset class='contactgegevens'>
		<legend>contact gegevens</legend>
			<div class="block">	
				<label>Voornaam</label><%=c.getVoornaam() %><br />
				<label>Achternaam</label><%=c.getAchternaam() %><br />
				<label>Contactpersoon</label><%=c.getContactpersoon() %><br />
			</div>
			<div class="block">
 
				<label>Tel nummer</label><%=c.getTelefoon()%><br />
				<label>Mobiel</label><%=c.getMobiel() %><br />
				<label>email</label><%=c.getMail() %><br />
			</div>
			<div class="block">
				<label>bedrijf</label><%=c.getBedrijf() %><br />
				<label>functie</label><%=c.getFunctie() %>
			</div>
		</fieldset>
		<!-- *************EINDE ********************** -->
		<!-- *****************MAILING***************** -->
		<fieldset class='contactgegevens'>
		<legend>Mailen</legend>
		Verstuur template mail:
			<select name="mailid" id='template'>
							<%
							for (Mail m : mail){
								out.println("<option name='mailid' value='"+m.getId()+"'>"+m.getOnderwerp()+"</option>");
							}
							%>
			</select>
						<input type='submit' value='verstuur' id="sendtemplate"><br /><br />
						Stuur mail zonder opmaak:<br />
						<select name="singleMail_user" id="singleMail_user">
							<% 
							GebruikersIO gIo = new GebruikersIO();
							for(Gebruiker g : gIo.listGebruikers()){
						%>
							<option value="<%=g.getEmail() %>"><%=g.getEmail() %></option>
							<%} %>
						</select>
						<input type="text" placeholder="Onderwerp" id="singleMail_onderwerp"><br />
						<input type="hidden" id="singleMail_receiver" value="<%= c.getMail() %>" ><br />
						<textarea placeholder='Plaats hier het bericht' cols="70" rows="10" id="singleMail_message"></textarea>
						<input type='submit' value='stuur' id="sendSingleMail">
		</fieldset>
		<!-- *************EINDE ********************** -->
		<!-- *****************Statuswijziging***************** -->
		<fieldset class='status'>
			<legend>bellen</legend>
			<input type='radio' name='bellen' value='2' <%= (c.getBellen()== 2) ?  "checked" : "" %>><label>niet gebeld</label><br />
				<input type='radio' name='bellen' value='1' <%= (c.getBellen()== 1) ?  "checked" : "" %>><label>gebeld</label><br />
				<input type='radio' name='bellen' value='3' <%= (c.getBellen()== 3) ?  "checked" : "" %>><label>niet opgenomen</label><br />
		</fieldset>
		<fieldset class='status'>
			<legend>mailen</legend>
				
				<input type='radio' name='mailen' value='2'  <%= (c.getMailen()== 2) ?  "checked" : "" %>><label>niet gemaild</label>	<br />
				<input type='radio' name='mailen' value='1' <%= (c.getMailen()== 1) ?  "checked" : "" %>><label>mail verstuurd</label>	<br />
				<input type='radio' name='mailen' value='3' <%= (c.getMailen()== 3) ?  "checked" : "" %>><label>reminder verstuurd</label>	<br />
		</fieldset>
		<fieldset class='status'>	
			<legend>hard copy</legend>
				
				<input type='radio' name='hardcopy' value='2' <%= (c.getHardcopy()== 2) ?  "checked" : "" %>>  <label>niet gestuurd</label>	<br />
				<input type='radio' name='hardcopy' value='1' <%= (c.getHardcopy()== 1) ?  "checked" : "" %>><label>gestuurd</label>	<br />
		</fieldset>
		<fieldset class='status'>	
				<legend>status</legend>
				
					<input type='radio' name='status' value='2'  <%= (c.getStatus()== 2) ?  "checked" : "" %>><label>afwachtend</label><br />
					<input type='radio' name='status' value='1' <%= (c.getStatus()==1) ?  "checked" : "" %>><label>geaccepteerd</label><br />
					<input type='radio' name='status' value='3' <%= (c.getStatus()== 3) ?  "checked" : "" %>><label>geweigerd</label><br />
		</fieldset>
		<fieldset class='status'>	
				<legend>prioriteit</legend>
				
		<span class="currentprio"><%=c.getPrioriteit() %></span><br />
					1<input type="range" name="prioriteit" class="range" min="1" max="5" step='1' value="<%=c.getPrioriteit() %>">5
				
		</fieldset>
		</form>		
	  	<!-- *****************EINDE**************************** -->
	  	
		<!-- *****************Formulier van de status toevoegen**************************** -->
		
		<form>
		<fieldset class='status_groot'>
			<legend>Opmerking toevoegen</legend>
			<textarea rows="4" cols="61" name="opmerking" id="opmerking" placeholder="Opmerking toevoegen."></textarea>
							
					
						<input type="hidden" name="genodigdeID" id="genodigdeID" value="<% out.print(genodigdeID); %>"/>
						<input type="hidden" name="eventID" id="eventID" value="<% out.print(eventID); %>"/>
					<br /><input type="submit" value="Toevoegen" id="addOpmerking" />
		</fieldset>
			<!-- *****************EINDE**************************** -->
				  	
		<!-- *****************Formulier van de status toevoegen**************************** -->
		<fieldset class='status_groot'>
			<legend>todo toevoegen</legend>
			<select name="locatie" id="actor">
	  			<% 
						for(Gebruiker g : gIo.listGebruikers()){
					%>
						<option  value="<%=g.getVoornaam() +" "+g.getAchternaam() %>"><%=g.getVoornaam() +" "+g.getAchternaam() %></option>
						<%} %>
					</select>
					<input type="date" " id="date" name="date" />
				<textarea rows="4" cols="61" name="opmerking" id="todo" placeholder="Opmerking toevoegen."></textarea>
							
					<br /><input type="submit" value="Toevoegen" id="addTodo" />
		</fieldset>
		<fieldset class='status_groot'>
			<legend>Lead in Zoho toevoegen</legend>
	  			<% 
	  			ZohoUsers zc = new ZohoUsers();
	  			ArrayList<Gebruiker> aUsers = zc.alleGebruikersOphalen();
	  			out.println("<select name='lead_id' id='leading'>");
	  			for(Gebruiker l :  aUsers){
	  				out.println("<option id='user' value='"+l.getId() +"' email='"+ l.getEmail()+"'>"+ l.getVoornaam()+"</option>");
	  			}
	  			out.println("</select>");
					%>
				<textarea rows="4" cols="61" name="opmerking" id="lead" placeholder="Opmerking toevoegen."></textarea>
					<input id="leadCompany" type="hidden" value="<%=c.getBedrijf() %>" />
					<input id="leadFirst" 			type="hidden" value="<%=c.getVoornaam() %>" />
					<input id="leadLast" 			type="hidden" value="<%=c.getAchternaam() %>" />
					<input id="leadLevel" 			type="hidden" value="<%=c.getFunctie_level() %>" />
					<input id="leadEmail" 		type="hidden" value="<%=c.getMail() %>" />
					<input id="leadPhone" 		type="hidden" value="<%=c.getTelefoon() %>" />
					<input id="leadMobile" 		type="hidden" value="<%=c.getMobiel()%>" />
					
					<br /><input type="submit" value="Toevoegen" id="addLead" />
		</fieldset>
		
		<fieldset class='status_groot'>
			<legend>Opmerking in Zoho toevoegen</legend>
	  			<input type="text" id="opmerking-title" placeholder="Titel">
				<textarea rows="4" cols="61" name="opmerking" id="zohoOpmerking" placeholder="Opmerking toevoegen."></textarea>
						
					<br /><input type="submit" value="Toevoegen" id="addZohoOpmerking" />
		</fieldset>
		</form>		
	  	<!-- *****************EINDE**************************** -->
	  	<table class="table" summary="Evenementen overzicht">
	  	<caption><h1>Opmerkingen</h1> </caption>  
			<thead>
				<tr>
					<th scope="col">Contactpersoon</th>
					<th scope="col">datum</th>
					<th scope="col">opmerking</th>
				</tr>
			</thead>
			<tbody class="opmerkingen">
			<!-- include ajax -->
			</tbody>
		</table>	  
		<table class="table" summary="Evenementen overzicht">
		<caption><h1>Todo</h1> </caption>  
			<thead>
				<tr>
					<th class="datumcol">Datum</th>
		                 <th>Actienemer</th>
		                <th class="large">Actie</th>
		                <th class="small">verwijder</th>
				</tr>
			</thead>
			<tbody class="todolist">
			<!-- include ajax -->
			</tbody>
		</table>	  	
		
	</div>
 <script src="../assets/js/jquery.1.8.0.min.js"></script>    
    <script src="../assets/js/opmerkingen.js"></script>
</body>

</html>