<%@include file="../includes/login_check.jsp"%>
<%@page import="model.*"%>
<%@page import="domein.*"%>
<!DOCTYPE html>
<html>

<head>
<title>Incentro Event Inviting System</title>
<%@include file="../includes/head.html"%>
<link rel="stylesheet" type="text/css" href="../assets/css/inviteSystem.css" />
</head>
<body>

	<div class="wrapper">
		<div class='header'>
			<a href="/Inviting/eOverzicht.jsp" id="logo"><img src="../assets/img/logo.png"></a>
		</div>
		<h3>Mailing</h3>
		<div class="search-form">
			<a href="../eOverzicht.jsp"><input type="submit" value="Terug" class="button-menu" /></a> 
            <a href="createtemplate.jsp?mailID=0"><input type="submit" value="Nieuw" class="button-menu right" /></a>        
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
						TemplateIO mIo = new TemplateIO();
						
						for (MailFormat m : mIo.getEventGegevens()) {
						%>	
					
					<tr href="createtemplate.jsp?mailID=<%=m.getId()%>" class="clickable">
						<td class="click">
							<%=m.getId() %>
						</td> 
						<td class="click">
							<%=m.getNaam() %>
						</td> 
					</tr>				
					<% 
						}
					%> 
				</tbody>
		</table>

	</div>
<script src="../assets/js/jquery.1.8.0.min.js"></script>    
    <script src="../assets/js/templateoverzicht.js"></script> 

</body>

</html>