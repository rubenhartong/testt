<%@include file="includes/login_check.jsp"%>
<%@page import="model.*"%>
<%@page import="domein.*"%>
<!DOCTYPE html>
<html>

<head>
<title>Incentro Event Inviting System</title>
<%@include file="includes/head.html"%>
<link rel="stylesheet" type="text/css" href="assets/css/inviteSystem.css" />
</head>
<body>

	<div class="wrapper">

		<%@include file="includes/header.html"%>

		<h3>Mailing</h3>
		<div class="search-form">
            <a href="status/status.jsp?eventID=<%= request.getParameter("eventID")%>"><input type="submit" value="Terug" class="button-menu" /></a>     
            <a href="Aanmaken/createMail.jsp?eventID=<%= request.getParameter("eventID")%>&mailID=0"><input type="submit" value="Nieuw" class="button-menu right" /></a>        
        </div>
		
		<table class="table" summary="Evenementen overzicht">
			<thead>
				<tr>
					<th scope="col">Naam</th>
					<th scope="col">onderwerp</th>
				</tr>
			</thead>
			<tbody>
				<%
						String eventID = request.getParameter("eventID");
						MailIO mIo = new MailIO();
						
						for (Mail m : mIo.getEventsGegevens(eventID)) {
						%>	
					
					<tr href="Aanmaken/createMail.jsp?eventID=<%=eventID%>&mailID=<%=m.getId()%>" class="clickable">
						<td class="click">
							<%=m.getNaam() %>
						</td> 
						<td class="click">
							<%=m.getOnderwerp()  %>
						</td>
					</tr>				
					<% 
						}
					%> 
				</tbody>
		</table>

	</div>
<script src="assets/js/jquery.1.8.0.min.js"></script>    
    <script src="assets/js/mailoverzicht.js"></script> 

</body>

</html>