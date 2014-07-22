<!DOCTYPE html>
<%@page import="java.sql.*"%>
<%@page import="model.*"%>
<%@page import="domein.*"%>
<%@ page import="java.util.Date,java.text.SimpleDateFormat,java.text.ParseException"%>
<%@include file="../includes/login_check.jsp"%>

<html>
<head>
<title>Incentro - Create event</title>
<%@include file="../includes/head.html" %>
<link rel="stylesheet" type="text/css" href="../assets/css/inviteSystem.css" />
</head>
<body>

    <div class="wrapper">
        <div class="header">
        <a href="/InviteSysteem/eOverzicht.jsp" id="logo" ><img src="../assets/img/logo.png" /></a>
    </div>

        <h3>Programma opstellen</h3>
        <div class="search-form">
            <a href="../eOverzicht.jsp"><input type="submit" value="Terug" class="button-menu" /></a>            
        </div>
        <%
        String eventID = request.getParameter("eventID");
        request.getSession().setAttribute("eventID", eventID);
        ProgrammaIO pIo = new ProgrammaIO();
        EvenementenIO eIo = new EvenementenIO();
        Evenement e = eIo.getEvent(eventID);
        int pID = e.getProgrammaID();
        Programma p = pIo.getProgramma(pID);
        
            
        String vanTijd = p.getVanTijd();
        String totTijd = p.getTotTijd();
        String programmaTxt = p.getProgrammatxt();
%>

        <form class="opstelForm" action="../ProgrammaToevoegen.do" method="post">
            <input type="hidden" name="eventID" value="<%out.print(eventID); %>"></input>

            <div class="child">
                <label>Start tijd</label> 
                <input type="time" name="startTijd" id="inputStartTijd"  placeholder="00:00" value="<%=vanTijd %>" /><br />
                <label>Eind tijd</label> 
                <input type="time" name="eindTijd" id="inputEindTijd" placeholder="00:00" value="<%=totTijd %>" />
                
                <textarea rows="15" cols="70" name="programma"
                    placeholder="Compleet programma hier. gebruik een komma als enter"><%=programmaTxt %></textarea>
               <input type="submit" value="Voeg toe" id="form_submit2" />
              </div>
              
              <div class="child right">
                    <h4>Richtlijn programma opstellen:</h4>
                    <p>
                        Voer uw programma op de volgende manier in <i>(voorbeeld)</i><br />
                        <b><i>Let op de komma's</i></b><br /> <br /> 10:00-11:00 Inloop,<br />
                        11:00-12:00 Inleiding,<br /> 12:00-13:30 Spreker 1,<br />
                        13:30-15:00 Pauze,<br /> 15:00-16:00 Spreker 2,<br />
                        16:00-17:30 Workshop,<br /> 17:30-18:00 Afsluiting
                    </p>
                </div>
               </form>

              

                
          

       
    </div>
    <%@include file="../includes/footer.html"%>

</body>

</html>