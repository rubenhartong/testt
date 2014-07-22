<!DOCTYPE html>
<%@page import="java.sql.*"%>
<%@page import="model.*"%>
<%@page import="domein.*"%>
<%@ page import = "java.util.Date,java.text.SimpleDateFormat,java.text.ParseException"%>
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
			Change Key 

		</h3>
	 <div class="search-form">
            <a href="eOverzicht.jsp"><input type="submit" value="Terug" class="button-menu" /></a>    
            <%
if(request.getParameter("message") != null)	{
	out.println(request.getParameter("message"));
}	
%>        
        </div>
		
 <form id="msform" method="post" class="small" action="changeKey.do">
	<!-- fieldset --> 

	<fieldset class="show">
		<h2 class="fs-title">API key</h2>
		<h3 class="fs-subtitle">Verander hier de API key van Zoho</h3>
		
		 		 <input type="text" name='key' placeholder="Key" />
                <input type="submit"  class="submit action-button" value="Verander" /> 

	</fieldset>
	<!--  ************************************************************************************** -->
</form>

<div id="msform" class="small" >
	<div class="fieldset show">
		
		<form method="post" action="DeleteContacts.do">
		
			<h2 class="fs-title">Contacten</h2>
			<h3 class="fs-subtitle">Hiermee worden alle contacten verwijderd. Dit is eerst nodig voordat de contacten opnieuw kunnen worden toegevoegd in dit systeem.</h3>

               <input type="submit"  class="submit action-button" value="Verwijder" />
       	
   		</form>       
        
		 <form method="post" action="cyncContacts.do">
				<h3 class="fs-subtitle">Verwijder alle contacten en importeer deze opnieuw vanuit Zoho, LET OP! Dit kan enkele minuten duren</h3>
		        <input type="submit"  class="submit action-button" value="Importeer contacten" /> 	
		</form>
	</div>
</div>
	<!--  ************************************************************************************** -->


	<!-- fieldset --> 

	
	<!--  ************************************************************************************** -->

        <%@include file="includes/footer.html" %>
	</div>
</body>

</html>