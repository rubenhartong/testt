<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Uitloggen</title>
</head>
<body>
	<%
				request.getSession().removeAttribute("status");
				request.getSession().removeAttribute("userMail");
				response.sendRedirect("index.jsp");
				%>
</body>
</html>