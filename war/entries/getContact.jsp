
<%@page import="model.*"%>
<%@page import="domein.*"%>
<%@page import="java.util.*"%>

<%		
GenodigdenIO gIo 	= new GenodigdenIO();
EntryHandler eh = new EntryHandler();

		String personalID 						= request.getParameter("id");
		String entry 				= request.getParameter("entry");
		
		Entry e = eh.getEntrieByID(entry);
 
		ContactZoho c	 		= gIo.getContactDataI(personalID); 
%>
<div class="closeButton">X</div>
<table >
<tr >		<th class="first">Gegevens</th> 	<th class="contact">Contact</th>														<th class="contact">Aanmelding</th></tr>
<tr >		<th>Naam</th>								 <td><%=c.getVoornaam() + " " + c.getAchternaam() %></td> 	<td><%= e.getFirst_name() + " " + e.getLast_name()%><td></tr>
<tr >		<th>Mail</th> 								<td><%=c.getMail()  %></td>																<td><%= e.getEmail() %><td></tr>
<tr >		<th>Bedrijf</th> 							<td><%=c.getBedrijf() %></td>															<td><%= e.getCompany() %><td></tr>
<tr >		<th>Functie</th> 							<td><%=c.getFunctie() %></td>															<td><%= e.getFunction()%><td></tr>
<tr >		<th>telefoon</th> 							<td><%= c.getTelefoon() + "<br />" + c.getMobiel()%></td>		<td><%= e.getTelephone() %><td></tr>
</table>
<button>Accepteer</button>
<button>Afwijzen</button>
<br /><br />
<button>Geen match</button>

