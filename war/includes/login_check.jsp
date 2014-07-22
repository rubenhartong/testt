<%
boolean debug = true;
String status = "";
if(debug) {
	status = "ingelogd";
}
else {
	if(request.getSession().getAttribute("status") != null){
	    status = (String)request.getSession().getAttribute("status");
	    System.out.println("login status:---------------> " + status);
	    
	}else if(!status.equals("ingelogd")){
	    response.sendRedirect("/Inviting/index.jsp?message=Log+eerst+in+om+verder+te+gaan");
	}
}
%>