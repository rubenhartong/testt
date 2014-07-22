    <!DOCTYPE html>
    <%@page import="model.*"%>
    <%@page import="java.util.*"%>
    <%@page import="domein.*"%>
    <%@include file="../includes/login_check.jsp" %>
    <% 
    	String eventID = request.getParameter("eventID");
		MailIO mio = new MailIO();
		ArrayList<Mail> mail = mio.getEventsGegevens(eventID);
    %>

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
     <div class="changes">
      <div class="slider"><img alt="change" src="../assets/img/Pencil.png"></div>
      <div class="content">
     Verander de status:
     <p>
			 <select class="bulk-status">
            <option value="bellen">gebeld</option>
            <option value="mailen">gemaild</option>
            <option value="hardcopy">hard copy gestuurd</option>
            </select>
            <input type='submit' value='verander status' id="changeStatus" />
     </p>
     <p>
     	aanwezigheid:
			 <select class="bulk-apperance">
            <option value="1">geaccepteerd</option>
            <option value="3">geweigerd</option>
            <option value="2">afwachtend</option>
            </select>
            <input type='submit' value='verander status' id="changeApperance" />
     </p>
     <p>
            Verstuur een mail
            <select name="mailid" id='template'>
							<%
							for (Mail m : mail){
								out.println("<option name='mailid' value='"+m.getId()+"'>"+m.getOnderwerp()+"</option>");
							}
							%>
			</select>
			<a href="../mailen.jsp?eventID=<%=eventID%>">nieuwe mail</a>
			<input type='submit' value='verstuur mail' id="sendtemplate" />
    </p>
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
			
            <div class="search-live">
                <input type="text" class="search-input" name="id" placeholder="Search"></input>
            </div>
       
            <div class="checkbox">
                <input name="eventID" id="eventID" value="<%out.print(eventID);%>" type="hidden" />
            </div>		
        </div>
	      </div>        
        <table class="table sortable" >
         <caption><h1>Uitgenodigd</h1> </caption>   
            <thead>
             <tr>
             	<th class="checkbox"><input type="checkbox"  class="selectall" /></th>
                <th>Naam</th>
                <th>Bedrijf</th>
                <th>Functie</th>
                <th>Contactpersoon</th>
                <th>Status sorteer<br />
                

                	<input type="radio" name="sort" class='sort' value='mailen' /> | 
                	<input type="radio" name="sort" class='sort' value='bellen' /> | 
                	<input type="radio" name="sort" class='sort' value='hardcopy' /> | 
                	<input type="radio" name="sort" class='sort' value='status' selected /> | 
                	<input type="radio" name="sort" class='sort' value='prioriteit' />             
                </th>           
             </tr> 
            </thead>
            <tbody class="invited">
            <!--  ajax include  -->
           	</tbody> 
            </table>  
    </div>
    
    <script src="../assets/js/jquery.1.8.0.min.js"></script>    
    <script src="../assets/js/status.js"></script>
    <!--<script src="../assets/js/sorttable.min.js"></script> -->
</body>
</html>