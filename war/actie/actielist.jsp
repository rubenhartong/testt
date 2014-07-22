<%@page import="model.*"%>
<%@page import="java.util.*"%>
<%@page import="domein.*"%>
<%@include file="../includes/login_check.jsp" %>
<%		
		String eventID 		= request.getParameter("eventID");
	String genodigdeID 	= request.getParameter("genodigdeID");
			ActieIO aio = new ActieIO();
			DateHandler dh = new DateHandler();
			
			 ArrayList<Actie> acties = aio.getActies(eventID);
		for(Actie a : acties)
		{
			if(aio.checkDate(a.getDate())) {
				out.println("<tr class='hover alert' value='"+a.getContact()+"'>");
			} else {
				out.println("<tr class='hover' value='"+a.getContact()+"'>");
			}
		%>
			 <td><%= dh.dateToString(a.getDate())%></td>
			 <td><%= a.getUser_vn() + " " + a.getUser_an()%></td>
			 <td><%=a.getActor() %></td>
			 <td><%=a.getText() %></td>
			 <td><img src="/Inviting/assets/img/remove-button.png" class="remove" value="<%=a.getId() %>" /></td>
			</tr>				
		<%
		}
		%>