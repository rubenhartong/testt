<%@include file="/includes/login_check.jsp"%>
<%@page import="model.*"%>
<%@page import="domein.*"%>
<!DOCTYPE html>
<html>

<head>
<title>Incentro Event Inviting System</title>
<%@include file="/includes/head.html"%>
<link rel="stylesheet" type="text/css"
	href="../assets/css/inviteSystem.css" />
</head>
<%
	boolean exist = true;
	//check of dit klopt
	if (request.getParameter("mailID").equals("0")) {
		exist = false;
	}

	int mailID = Integer.parseInt(request.getParameter("mailID"));

	MailFormat mf = new MailFormat(0, "", "" , "", "", "");
	
	if (exist) {
		TemplateIO mIo = new TemplateIO();
		mf =  mIo.getGegevens(mailID);
	}
	
%>
<body>

	<div class="wrapper">
		<div class="header">
			<a href="../eOverzicht.jsp" class="logo"><img
				src="../assets/img/logo.png" alt="" /></a>
		</div>
		
		<h2>Email Maken</h2>
		<div class="search-form">
			<a href="javascript:javascript:history.go(-1)"><input
				type="submit" value="Terug" class="button-menu" /></a>
		</div>

		<form class="createmail" action="../CreateTemplate.do" method="post">
			<input type="hidden" name="mailID" value="<%=mailID%>"> 
			<input type="hidden" name="exist" value="<%=exist%>">
			<div class="content">
				<p class="toptext">
				Hier word een nieuw template aangemaakt voor een mail. Dit is de opmaak van de mail. Bij de voortgang kan de template worden bekeken en kan de template inhoud gegeven worden.
				</p>
				<fieldset>
				<div class="dynamic">
				
					<div class="row label">
						<label>Naam</label> <input type="text" name="naam"
							value="<%=mf.getNaam()%>" class="mail_content" title="geef aan hoe de mail in dit systeem moet heten" />
					</div>

				<div class="row textblock">
 					<label>Format</label>
					<textarea name="format" title="voeg hier de tekst van de tweede paragraaf in" ><%=mf.getFormat()%></textarea>
				</div>
				<div class="row textblock">
 					<label>Element 1</label>
					<textarea name="element1" title="voeg hier de tekst van de tweede paragraaf in" ><%=mf.getElement1()%></textarea>
				</div>
				<div class="row textblock">
 					<label>Element 2</label>
					<textarea class="mail_content" name="element2" title="voeg hier de tekst van de tweede paragraaf in" ><%=mf.getElement2()%></textarea>
				</div>
				<div class="row textblock">
 					<label>Element 3</label>
					<textarea class="mail_content" name="element3" title="voeg hier de tekst van de tweede paragraaf in" ><%=mf.getElement3()%></textarea>
				</div>
					<input type="submit" value="Opslaan" />
					
				</div>
				</fieldset>
			</div>
		</form>

	</div>

	<script src="//ajax.googleapis.com/ajax/libs/jquery/1.8.0/jquery.min.js"></script>
	<script src="../assets/js/tooltip.js" type="text/javascript"></script>
	<script src="../assets/js/mailForm.js"></script>
	
</body>
</html>