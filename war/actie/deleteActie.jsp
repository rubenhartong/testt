<%@page import="model.*"%>
<%@page import="java.util.*"%>
<%@page import="domein.*"%>
<%@include file="../includes/login_check.jsp" %>
<%		

String actieID 	= request.getParameter("id");
			ActieIO aio = new ActieIO();
			
			aio.deleteActie(actieID);
		%>