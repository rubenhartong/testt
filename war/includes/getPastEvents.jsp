	<%@page import="model.*"%>
<%@page import="java.util.*"%>
<%@page import="domein.*"%>
<%@include file="../includes/login_check.jsp" %>
<%		
		
				EvenementenIO eIo = new EvenementenIO();
				ActieIO aio				= new ActieIO();
				DateHandler dh = new DateHandler();
				
				for (Evenement e : eIo.listPastEvents()) {

		%><tr class="odd">
			
					<td><a href="eDetails.jsp?eventID=<%out.print(e.getEventID());%>"><%=e.getNaam() %></a></td>	
					<td>
						<%
							out.print(e.getContactPersoon());
						%>
					</td>
					<td>
						<%= dh.dateToString(e.getDatum()) %>
					</td>
					<td>
						<%=  e.getType()	%>
					</td>
					<td><a href="uitnodigingen/uUitnodigen.jsp?eventID=<%out.print(e.getEventID()); %>">Toevoegen</a></td>
					<td><a href="status/status.jsp?eventID=<%out.print(e.getEventID()); %>">Voortgang</a></td>
					<td><a href="actiepage.jsp?eventID=<%=e.getEventID() %>">Todo <% if(aio.checkActies(e.getEventID())){out.println("!");} %>
				</a></td>	
					<td><a href="#" value="<%= e.getEventID() %>" class="removed">
					<%if(e.getRemoved()) {
						out.println("verwijder");
					}	%></a></td>
				
			</tr>
			<%
				}
					
			%>