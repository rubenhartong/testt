<%@page import="model.*"%>
<%@page import="java.util.*"%>
<%@page import="domein.*"%>
<%@include file="../includes/login_check.jsp" %>
<%		
		String id 	= request.getParameter("id");
		GenodigdenIO gIo = new GenodigdenIO();
			
		ContactZoho contact = gIo.getContactDataI(id); 
%>

			<div class="select_header">Naam</div>
			<%=contact.getVoornaam() + " " + contact.getAchternaam() %>
			<div class="select_header">Email</div>
			<%=contact.getMail() %>
			<div class="select_header">Functie</div>
			<%=contact.getFunctie() %>
			<div class="select_header">Functie level</div>
			<%=contact.getFunctie_level() %>
			<div class="select_header">Functie domain</div>
			<%=contact.getFunctie_domain() %>
			<div class="select_header">Bedrijf</div>
			<%=contact.getBedrijf() %>
			<div class="select_header">Telefoon</div>
			<%=contact.getTelefoon() %>
			<div class="select_header">Mobiel</div>
			<%=contact.getMobiel() %>
			<div class="select_header">Contactpersoon</div>	
			<%=contact.getContactpersoon() %>