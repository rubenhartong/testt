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
			Change Password
		</h3>
	<div class="search-form">
            <a href="eOverzicht.jsp"><input type="submit" value="Terug" class="button-menu" /></a>    
            <%
				if(request.getParameter("message") != null)	{
					out.println(request.getParameter("message"));
				}	
			%>        
        </div>
		<form id="msform" method="post" action="changePass.do">
	<!-- fieldset --> 

	<fieldset class="show">
		<h2 class="fs-title">Wachtwoord wijzigen</h2>
		<h3 class="fs-subtitle">Verander hier je wachtwoord van dit account</h3>

		 		<input type="text"   name='username'  value='<%=(String)request.getSession().getAttribute("userMail") %>'  readonly   />
		 		<input type="password" class="" name='current' placeholder="huidig" title="Geef je huidige wachtwoord op" required/>
		 		<input type="password" class="" name='new' placeholder="nieuw" title="geef je nieuwe wachtwoord op" required/>
		 		<input type="password" class="" name='newcheck' placeholder="check" title="geef nogmaals je nieuwe wachtwoord op" required/>

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