<%@page import="model.*"%>
<%@page import="java.util.*"%>
<%@page import="domein.*"%>
<%@include file="../includes/login_check.jsp" %>
<% 
String genodigdeID = request.getParameter("genodigdeID");
String eventID = request.getParameter("eventID");
OpmerkingIO oIo = new OpmerkingIO();
			for(Opmerking o : oIo.listOpmerkingen(genodigdeID, eventID)){
					%>
				<tr>
					<td><%out.print(o.getGebruikerID());%></td>
					<td><%out.print(o.getDatum()); %></td>
					<td><%out.print(o.getOpmerking()); %></td>
				</tr>
<% } %>