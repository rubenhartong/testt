<%@include file="/includes/login_check.jsp"%>
<%@page import="model.*"%>
<%@page import="domein.*"%>
<%@page import=" java.util.ArrayList"%>
<!DOCTYPE html>
<html>

<head>
<title>Incentro Event Inviting System</title>
<%@include file="/includes/head.html"%>
<link rel="stylesheet" type="text/css" href="../assets/css/inviteSystem.css" />
</head>
<body>
<div class="wrapper">
<div class="header">
			<a href="../eOverzicht.jsp" class="logo"><img src="../assets/img/logo.png" alt="" /></a>
		</div>
		<%@include file="../includes/menu_back.html" %>
<!-- multistep form -->
<%
GebruikersIO gio = new GebruikersIO();
ArrayList<Gebruiker> aUsers = gio.listGebruikers();
%>
<table class="table">
			<thead>
				<tr>
					<th>Naam</th>
					<th >Email</th>
					<th>Bedrijf</th>
					<th>telefoon</th>
					<th>Verwijderen</th>
				</tr>
			</thead>
			<tbody>
		<% 
			for(Gebruiker u : aUsers){
				out.println("<tr><td> " + u.getVoornaam() + " "	+ u.getAchternaam() + "</td>");
				out.println("<td> " + u.getEmail() 						+ "</td>");
				out.println("<td> " + u.getBedrijf()						+ "</td>");
				out.println("<td> " + u.getTelefoonnummer() 	+ "</td>");
				out.println("<td> <a href='#' id='gebruikerDelete' value='"+ u.getEmail()+"'>verwijder</a></td></tr>");
			}
		%>
			</tbody>
		</table>

<form id="msform">
	<!-- fieldsets --> 
	<!--  ************************************************************************************** -->
	<fieldset class="show">
		<h2 class="fs-title">gebruiker toevoegen</h2>
		<h3 class="fs-subtitle">Voeg een nieuwe gebruiker toe die toegang krijgt tot dit systeem</h3>
		<input type="text" name="user_email" placeholder="Email" title="Voer hier het incentro email account in van de gebruiker" />
		<input type="text" name="user_voornaam" placeholder="voornaam" title="Voer de voornaam van de gebruiker in" />
		<input type="text" name="user_achternaam" placeholder=achternaam title="voer de achternaam in" />
		<input type="text" name="user_tel" placeholder="telefoonnummer" title="voer een telefoonnummer in" />
		<input type="text" name="user_bedrijf" placeholder="bedrijf" title="geef aan voor welk bedrijf het contactpersoon werkt" />
		<input type="password" name="user_pass" placeholder="Password" title="geef het wachtwoord waarmee de gebruiker kan inloggen" />
		<input type="password" name="user_cpass" placeholder="Confirm Password" title="voer nog een keer het wachtwoord in" />
		<input type="button" name="user_submit" class="submit action-button" id="gebruiker" value="Voeg toe" />
	</fieldset>
	<!--  ************************************************************************************** -->
</form>
</div>
<!-- jQuery -->
<script src="../assets/js/jquery.1.8.0.min.js" type="text/javascript"></script>
<script src="../assets/js/tooltip.js" type="text/javascript"></script>
<!-- jQuery easing plugin -->
<script src="../assets/js/newEvent.js" type="text/javascript"></script>
</body>
</html>