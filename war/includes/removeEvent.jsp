	<%@page import="model.*"%>
<%@page import="java.util.*"%>
<%@page import="domein.*"%>
<%@include file="../includes/login_check.jsp" %>
<%		
		String id = request.getParameter("id");
		EvenementenIO eIo = new EvenementenIO();
		eIo.removeEvent(id);
		//System.out.println("evenement verwijderd met id: \n" +id);
		%>