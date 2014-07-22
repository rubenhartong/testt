    <!DOCTYPE html>

    <%@page import="model.*"%>
    <%@page import="java.util.*"%>
    <%@page import="domein.*"%>
    <%//@include file="../includes/login_check.jsp" %>
    <% String eventID = request.getParameter("eventID");%>

<html>
    <head>
        <title>Incentro Event Inviting System</title>
        <%@include file="../includes/head.html" %>
        <link rel="stylesheet" type="text/css" href="../assets/css/inviteSystem.css" />
        
    </head>
<body>

    <div class="wrapper">
        <div class="header">
      
        </div>

        <h3>Nieuwe aanmeldingen</h3>

        <div class="search-form">
            <a href="../eOverzicht.jsp"><input type="submit" value="Terug"
                class="button-menu" /></a>	
        </div>

		<ul id="tabs-nav">
		  <li class="tabs"><a href="founded_entries.jsp?eventID=123">Bekend</a></li>
		  <li class="tabs"><a href="unfound_entries.jsp">Onbekend</a></li>
		  <li class="tabs"><a href="completed_entries.jsp">Behandeld</a></li>
		</ul>
		 
		<div id="ajax-content">Selecteer hierboven de gewenste optie. </div>
        
        <div id="popup">
        	<div id="screen"></div>
        </div>
    </div>    
</body>
    <script src="../assets/js/jquery.1.8.0.min.js"></script>
    <script src="../assets/js/entries.js"></script>        
</html>