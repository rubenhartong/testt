<%@page import="model.*"%>
<%@page import="java.util.*"%>
<%@page import="domein.*"%>
<%@include file="../includes/login_check.jsp" %>
<%		
		String eventID 	= request.getParameter("eventID");
		String search 	= request.getParameter("search");
		GenodigdenIO gIo = new GenodigdenIO();

		ArrayList<ContactZoho> gList = gIo.search(eventID, search);
		
		for(ContactZoho g : gList)
		{
		%>
			<tr class="hover">
			 <td><input class="check" type='checkbox' value="<%=g.getId()%>"></td>
			 <td><%=g.getVoornaam() + " "+ g.getAchternaam()%></td>
			 <td><%=g.getBedrijf() %></td>
			 <td><%=g.getFunctie() %></td>
			 <td><%=g.getContactpersoon()%></td>
			</tr>				
		<%
		}
		%>