<!DOCTYPE html>
<%@page import="java.sql.*"%>
<%@page import="model.*"%>
<%@page import="domein.*"%>
<%@include file="includes/login_check.jsp" %>
<html>
<head>
<title>Incentro Event Inviting System</title>
<%@include file="includes/head.html" %>
<link rel="stylesheet" type="text/css" href="assets/css/inviteSystem.css" />
</head>

<body>

	<div class="wrapper">
		<%@include file="includes/header.html" %>
		

		<h3>
			Evenementen overzicht - Welkom
			<%
			out.print(request.getSession().getAttribute("userMail"));
			//request.getSession().removeAttribute("userMail");
			
			if(request.getParameter("message") !=null) {
				out.print("<br />"+request.getParameter("message"));
			}
		%>
		</h3>
		
            <%@include file="includes/menu_overzicht.html" %>
		<table class="table sortable">
			<thead>
				<tr>
					<th class="size200">Naam</th>
					<th class="size150">Contactpersoon</th>
					<th class="datumCol">Datum</th>
					<th >Type</th>
					<th>Contacten</th>
					<th>Hitlist</th>
					<th>Todo</th>
					<th>Verwijderde evenementen</th>
				</tr>
			</thead>
			<tbody class='events'>
			<!--  ajax include (include/get events) -->
			</tbody>
		</table>

        <%@include file="includes/footer.html" %>
	</div>
	<script src="assets/js/jquery.1.8.0.min.js"></script>    
    <script src="assets/js/overzicht.js"></script>
	<script type="text/javascript" src="assets/js/sorttable.min.js"></script> 
</body>

</html>