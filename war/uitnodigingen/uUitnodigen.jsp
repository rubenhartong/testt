    <!DOCTYPE html>

    <%@page import="model.*"%>
    <%@page import="java.util.*"%>
    <%@page import="domein.*"%>
    <%@include file="../includes/login_check.jsp" %>
    <% String eventID = request.getParameter("eventID");%>

<html>
    <head>
        <title>Incentro Event Inviting System</title>
        <%@include file="../includes/head.html" %>
        <link rel="stylesheet" type="text/css" href="../assets/css/inviteSystem.css" />
    </head>

<body>

     <div class="fix">
     	<div class="right">
			<div class="select_header">Naam</div>
			<div class="select_header">Email</div>
			<div class="select_header">Functie</div>
			<div class="select_header">Bedrijf</div>
			<div class="select_header">Telefoon</div>
			<div class="select_header">Mobiel</div>
			<div class="select_header">Contactpersoon</div>
		</div>	
     </div>
    <div class="wrapper">
        <div class="header">
      
        </div>

        <h3>Contactenlijst - uitnodigen</h3>
	<div class="fixed">
        <div class="search-form">
            <a href="../eOverzicht.jsp"><input type="submit" value="Terug"
                class="button-menu" /></a>
           
			<input type="submit" value="verwijderen" name="Verwijder" id="verwijderen" class="button-menu" /> 
			<input type="submit" value="toevoegen" name="Voeg toe" id="toevoegen" class="button-menu" />
  			<input type="submit" value="Sync met Zoho"  id="syncroniseer" class="button-menu" />
           
            		 <div class='message'>Contacten toegevoegd</div>
            <form class="search-live">         
                <input type="submit" class="button-menu right" id="search-button" name="search-button" value="Search" tabindex="2" />
                <input type="text" class="search-input" name="id" placeholder="Search" tabindex="1" />
            </form>

            <div class="checkbox">
                <input name="eventID" id="eventID" value="<%out.print(eventID);%>" type="hidden" />
            </div>		
        </div>
        
     
        
        <div class="searchform hidden">
			 <%@include file="../includes/searchform.html" %>
		</div>
		<div class="toggle">
			<span class="search">Search</span>		
		<img src="../assets/img/arrow-down.png" class="image"> </div>
	</div>


        
      
        <table class="table sortable">
        <caption><h1>Niet uitgenodigd</h1> </caption>  
            <thead> 
	             <tr>
		                <th class="checkbox"><input type="checkbox" class="selectPotential" /></th>
		                <th>Naam</th>
		                <th>Bedrijf</th>
		                <th>Functie</th>
		                <th>Contactpersoon</th>	                
	             </tr>
            </thead>
            <tbody class="genodigden">
            </tbody>
            </table>
                   
        <table class="table sortable">
        <caption><h1>Uitgenodigd</h1> </caption>   
            <thead>
	             <tr>
	                <th class="checkbox"><input type="checkbox" class="selectInvited" /></th>
	                <th>Naam</th>
	                <th>Bedrijf</th>
	                <th>Functie</th>
	                <th>Contactpersoon</th>	                
	             </tr>
            </thead>
            <tbody class="invited">
           	</tbody> 
            </table>
        
    </div>
    <div id="print"></div>
    
    <script data-main="../assets/js/uitnodigen" src="../assets/js/jquery.1.8.0.min.js"></script>    
    <script src="../assets/js/uitnodigen.js"></script>
	<script type="text/javascript" src="../assets/js/sorttable.min.js"></script> 
</body>
</html>