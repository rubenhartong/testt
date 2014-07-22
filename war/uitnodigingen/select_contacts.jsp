<%@page import="model.*"%>
<%@page import="java.util.*"%>
<%@page import="domein.*"%>

<%              
                String eventID  = request.getParameter("eventID");     
                String level = request.getParameter("level");
                String domain = request.getParameter("domain");

                        GenodigdenIO gIo = new GenodigdenIO();       
                        for(ContactZoho g : gIo.checkboxSearch(eventID, level, domain))
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
                