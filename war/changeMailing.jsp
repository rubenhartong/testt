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
			Verander mailing wachtwoord
		</h3>
	 	<div class="search-form">
            <a href="eOverzicht.jsp"><input type="submit" value="Terug" class="button-menu" /></a>    
            <%
				if(request.getParameter("message") != null)	{
					out.println(request.getParameter("message"));
				}	
			%>        
        </div>
		<form id="msform" method="post" action="changeMail.do">
	<!-- fieldset --> 

	<fieldset class="show">
		<h2 class="fs-title">Mail adres wijzigen</h2>
		<h3 class="fs-subtitle">Hier kan het algemene e-mailadres gewijzigd worden waarmee alle mails verstuurd worden. Let op! Dit moet wel een gmail gerelateerd e-mailadres zijn.</h3>
		
		 		<input type="text"   			name='email'  placeholder='email adres'  required="required"/>
		 		<input type="password" class="" name='pass' placeholder="nieuw" title="geef het wachtwoord op" required="required" />
		 		<input type="password" class="" name='passcheck' placeholder="check" title="geef nogmaals het wachtwoord op" required="required" />

                <input type="submit"  class="submit action-button" value="verander" /> 

	</fieldset>
	<!--  ************************************************************************************** -->
</form>

 
        <%@include file="includes/footer.html" %>
	</div>
	<script src="assets/js/jquery.1.8.0.min.js"></script>    
    <script src="assets/js/tooltip.js"></script>
</body>

</html>