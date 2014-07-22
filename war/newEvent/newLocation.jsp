<%@page import="java.sql.*"%>
<%@page import="model.*"%>
<%@page import="domein.*"%>
<%@include file="../includes/login_check.jsp" %>
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
		
		<%
LocatieIO lio = new LocatieIO();

%>
<table class="table">
			<thead>
				<tr>
					<th>adres</th>
					<th >plaats</th>
					<th>postcode</th>
					<th>telefoon</th>
					<th>website</th>
					<th>verwijder</th>
				</tr>
			</thead>
			<tbody>
		<% 
			for(Locatie u : lio.listLocaties()){
				out.println("<tr><td> " 	+ u.getAdres()						+ "</td>");
				out.println("<td> " 			+ u.getPlaats()						+ "</td>");
				out.println("<td> "		 	+ u.getPostcode()					+ "</td>");
				out.println("<td> " 			+  u.getTelefoonnummer()	+ "</td>");
				out.println("<td> " 			+  u.getWebsite()					+ "</td>");
				out.println("<td> <a href='#' id='locatieDelete' value='"+ u.getLocatieID()+"'>verwijder</a></td></tr>");
			}
		%>
			</tbody>
		</table>
<!-- multistep form -->
<form id="msform">
	<!-- fieldset --> 

	<fieldset class="show">
		<h2 class="fs-title">Locatie toevoegen</h2>
		<h3 class="fs-subtitle">Voeg hier een nieuwe locatie toe</h3>
		<input type="text" name="locatie_adres" placeholder="straat" style="width:75%;" title="voer de straatnaam in"  /><input type="text" name="locatie_nr" placeholder="nr" style="width:25%;" title="voer het huisnummer in" />
		<input type="text" name="locatie_postcode" placeholder="1234" maxlength="4" style="width:35%;" title="voer de postcode in" /><input type="text" name="locatie_postcode_nr" placeholder="XX" maxlength="2" style="width:20%;" />
		<input type="text" name="locatie_plaats" placeholder="plaats" title="Voer de plaats in" />
		<input type="text" name="locatie_tel" placeholder="telefoonnr" title="Voer het telefoon nummer in van de locatie" />
		<input type="text" name="locatie_website" placeholder="website" title="voer de website van de locatie in" /> 
		
		<input type="button" name="submit" class="submit action-button" id="locatie" value="Voeg toe" />
	</fieldset>
	<!--  ************************************************************************************** -->
</form>
</div>
<!-- jQuery -->
<script src="../assets/js/jquery.1.8.0.min.js" type="text/javascript"></script>
<script src="../assets/js/tooltip.js" type="text/javascript"></script>
<script src="../assets/js/newEvent.js" type="text/javascript"></script>
</body>
</html>