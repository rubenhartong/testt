<!DOCTYPE html>
<html>
<%@page import="java.sql.*"%>
<%@page import="model.*"%>
<%@page import="domein.*"%>
<%@ page import = "java.util.Date,java.text.SimpleDateFormat,java.text.ParseException"%>
<%@include file="includes/login_check.jsp" %>


<head>
<title>Incentro Event Inviting System</title>
<%@include file="includes/head.html" %>
<link rel="stylesheet" type="text/css" href="assets/css/inviteSystem.css" />
</head>

<body>

	<div class="wrapper">

		<%@include file="includes/header.html" %>
		
		<% 
		String eventID = request.getParameter("eventID");
		EvenementenIO eIO = new EvenementenIO(); 
		DateHandler dh = new DateHandler();
		LocatieIO lIo = new LocatieIO();
		UitnodigingIO uIo = new UitnodigingIO();
		ProgrammaIO pIo = new ProgrammaIO();
		Evenement e = eIO.getEvent(eventID);
		Locatie l = lIo.getLocatie(e.getLocatieID());

	        int pID = e.getProgrammaID();
	        Programma p = pIo.getProgramma(pID);
	        
	        String vanTijd = p.getVanTijd();
	        String totTijd = p.getTotTijd();
	        String programmaTxt = p.getProgrammatxt();
		
		%>

		<h3>Details  - <%out.print(e.getNaam()); %></h3>
		
		<div class="search-form">
            <a href="eOverzicht.jsp"><input type="submit" value="Terug" class="button-menu" /></a>
        </div>
			<ul class="eDetails"> 
				<li><label>Titel</label><span><%out.print(e.getNaam()); %></span></li>
				<li><label>Subtitel</label><span><%out.print(e.getSubtitel()); %></span></li>
				<li><label>Datum</label><span><%= dh.dateToString(e.getDatum()) %></span></li>
				<li></li>
				<li><label>Adres</label><span><%out.print(l.getAdres()); %></span></li>	
				<li><label>Postcode</label><span><%out.print(l.getPostcode()); %></span></li>		
				<li><label>Plaats</label><span><%out.print(l.getPlaats()); %></span></li>
				<li><label>Telefoonnummer</label><span><%out.print(l.getTelefoonnummer()); %></span></li>
				<li><label>Website</label><span><%out.print(l.getWebsite()); %></span></li>	
				<li></li>
				<li><label>Contactpersoon</label><span><%out.print(e.getContactPersoon()); %></span></li>
				<li class="lastDetail"><label>Organisator</label><span><%out.print(e.getOrganisator()); %></span></li>
				<li><input type="button" onclick="location.href = 'event/eAanpassen.jsp?eventID=<%out.print(eventID);%>'" value="Aanpassen" class="button-menu" /></li>
			</ul>
			
			 <ul class="eDetails right">

               <li><label>Begintijd</label><span><%=vanTijd %></span></li>
				<li><label>Eindtijd</label><span><%=totTijd %></span></li>
                <li>
                <textarea rows="15" name="programma" style="width: 100%;" disabled ><%   out.println(programmaTxt);       %>
                    </textarea></li>
               <li><input type="button" onclick="location.href = 'Aanmaken/programmaMaken.jsp?eventID=<%out.print(eventID);%>'" value="Aanpassen" class="button-menu" /></li>


               </ul>
	
	</div>
    <%@include file="includes/footer.html" %>
</body>

</html>