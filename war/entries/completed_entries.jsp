    <!DOCTYPE html>

    <%@page import="model.*"%>
    <%@page import="java.util.*"%>
    <%@page import="domein.*"%>
    <%//@include file="../includes/login_check.jsp" %>
    <% String eventID = request.getParameter("eventID");%>
    <% System.out.println("----------------------------->" + eventID); %>

<html>
    <head>
        <title>Incentro Event Inviting System</title>
        <%@include file="../includes/head.html" %>
        <link rel="stylesheet" type="text/css" href="../assets/css/inviteSystem.css" />
        
        
    </head>
    
    <%
    EntryHandler eh = new EntryHandler();
    %>
<body>

   
        <table class="table sortable">
			<thead>
				<tr>
					<th class="size200">Naam</th>
					<th class="size200">email</th>
					<th>bedrijf</th>
					<th >functie</th>
					<th >telefoon</th>
				</tr>
			</thead>
			<tbody class='events'>
			   	<% for (Entry e : eh.getCompletedEntries(eventID)) { %>
			<tr>
				<td><%=e.getFirst_name() + " "+ e.getLast_name() %></td>
				<td><%=e.getEmail()  %></td>
				<td><%=e.getCompany() %></td>
				<td><%=e.getFunction() %></td>
				<td><%= e.getTelephone()%></td>
			</tr>
		<%} %>
			</tbody>
		</table> 
</body>
</html>