<%@page import="model.*"%>
<%@page import="java.util.*"%>
<%@page import="domein.*"%>
<%@include file="../includes/login_check.jsp" %>
<%      
	String mail_1 			= "<img class='icon_status' src='../assets/img/iconset_status/email_green.png' title='Mail is verstuurd' /> ";
	String mail_2 			= "<img class='icon_status' src='../assets/img/iconset_status/email_blue.png ' title='Reminder gestuurd' />";
	String mail_3 			= "<img class='icon_status' src='../assets/img/iconset_status/email_red.png' title='Mail is niet verstuurd' />";
	String accept 		= "<img class='icon_status' src='../assets/img/iconset_status/check.png' title='Uitnodiging geaccepteerd' />";
	String denied 		= "<img class='icon_status' src='../assets/img/iconset_status/delete.png' title='Uitnodiging geweigerd' />";
	String undefined 	= "<img class='icon_status' src='../assets/img/iconset_status/rendom.png ' title='Nog niet gereageerd' />";
	String phone_1 		= "<img class='icon_status' src='../assets/img/iconset_status/phone_green.png' title='Is gebeld' />";
	String phone_2 		= "<img class='icon_status' src='../assets/img/iconset_status/phone_orange.png' title='gebeld en voicemail' />";
	String phone_3		 = "<img class='icon_status' src='../assets/img/iconset_status/phone_red.png' title='Is nog niet gebeld' />";
	String hardcopy_1 		= "<img class='icon_status' src='../assets/img/iconset_status/printer-red.png' title='Hardcopy is niet verstuurd' />";
	String hardcopy_2		 = "<img class='icon_status' src='../assets/img/iconset_status/printer-green.png' title='Hardcopy verstuurd' />";

        String eventID  = request.getParameter("eventID");
        String sortBy  = request.getParameter("sortBy");
            BezoekerIO bIo = new BezoekerIO();
            OpmerkingIO oio = new OpmerkingIO();
            ArrayList<ContactZoho> gList = bIo.naamBezoekersSorted(eventID, sortBy);
        for(ContactZoho g : gList) {
        %>		
           <tr class="hover" value="<%=g.getId()%>">
           		<td><input type="checkbox"  /></td>
              <td class="check"><a  href="persoonlijk_profiel.jsp?genodigdeID=<%=g.getId()%>&eventID=<%=eventID%>" ><%=g.getVoornaam() + " "+ g.getAchternaam()%></a></td>
			 <td><%=g.getBedrijf() %></td>
			  <td><%=g.getFunctie() %></td>
			 <td><%=g.getContactpersoon()%></td>
			 <td>
			  <%
			  switch(g.getMailen()){
				  case  1 : out.println(mail_1); 
				  break;  
				  case  2 : out.println(mail_3); 
				  break; 
				  default:  out.println(mail_2);       
			  break;
			  }
			  out.println(" | ");
			  switch(g.getBellen()){
				  case  1 : out.println(phone_3); 
				  break;  
				  case  2 : out.println(phone_2); 
				  break; 
				  default:  out.println(phone_1);       
				  break;
			  }
			  out.println(" | " );
			  switch(g.getHardcopy()){
				  case  1 : out.println(hardcopy_2); 
				  break;
				  case  2 : out.println(hardcopy_1);
				  break;
				  default:  out.println("x");       
				  break;
			  }
			  out.println(" | ");
			  switch(g.getStatus()){
				  case  1 : out.println(accept); 
				  break;  
				  case  2 : out.println(undefined); 
				  break; 
				  case  3 : out.println(denied); 
				  break; 
				  default:  out.println(undefined);       
				  break;
			  }
	
			  out.println(" | ");
			  out.println(g.getPrioriteit());

                        %>
            </td>
          
           </tr>                
        <%
        }
        %>