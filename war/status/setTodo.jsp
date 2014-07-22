<%@page import="model.*" %>
<%@page import="domein.*"%>
<%@include file="/includes/login_check.jsp"%>
			
		<%
		ActieIO aio = new ActieIO();

	  	String text = request.getParameter("text");
	  	String eventID = request.getParameter("eventID");
	  	String user = (String)request.getSession().getAttribute("userMail");
	  	String contact	= request.getParameter("genodigdeID");
	  	String actor	= request.getParameter("actor");
	  	String date = request.getParameter("datum");
	
	  	aio.setActie(eventID, user, date, text, actor, contact);
	  	
	  	%>
	  	<tr>
	  				<td><%= date %></td>
					<td><%=actor  %></td>
					<td><%=text %></td>
					<td><img src="../assets/img/remove-button.png" class="remove" value="<%=contact %>" /></td>
			</tr>
	  	