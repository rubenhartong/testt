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

	String eventID = request.getParameter("eventID");
	int mailID = Integer.parseInt(request.getParameter("mailID"));
	request.getSession().setAttribute("eventID", eventID);

	String naam = "";
	String onderwerp = "";
	String aanhef = "";
	String image_header = "";
	String image_subheader = "";
	String image = "";
	String link = "";
	String header1 = "";
	String header2 = "";
	String content1 = "";
	String content2 = "";

	if (exist) {
		MailIO mIo = new MailIO();
		for (Mail m : mIo.getEventGegevens(mailID, eventID)) {
			naam = m.getNaam();
			onderwerp = m.getOnderwerp();
			aanhef = m.getAanhef();
			link = m.getLink();
			image_header = m.getImage_header();
			image_subheader = m.getImage_subheader();
			image = m.getImage();
			header1 = m.getHeader1();
			header2 = m.getHeader2();
			content1 = m.getContent1();
			content2 = m.getContent2();

			content1 = content1.replaceAll("<br />", "\n");
			content2 = content2.replaceAll("<br />", "\n");
		}
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
			<%
				if (exist) {
			%>
			<input type="submit" class='button-menu right' value="Voorbeeld"
				onclick="window.open('mail_voorbeeld.jsp?eventID=<%=eventID%>&mailID=<%=mailID%>','Test', 'width=600,height=620,scrollbars=no,toolbar=no,location=no'); return false" />
			<input
				type="hidden" class="eventID" value="<%=eventID%>"> <input
				type="hidden" class="mailID" value="<%=mailID%>"> 
			<%
				}
			%>

		</div>

		<form class="createmail" action="../CreateMail.do" method="post">
			<input type="hidden" name="eventID" value="<%=eventID%>"> <input
				type="hidden" name="mailID" value="<%=mailID%>"> <input
				type="hidden" name="exist" value="<%=exist%>">
			<div class="content">
				<p class="toptext">
				Op deze pagina word de mail vorm gegeven. Hier kan worden aangegeven welke inhoud de tekst heeft. 
				Je dient de mail eerst op te slaan voordat je een een voorbeeld kan zien of een testmail kan versturen.
				</p>
				<fieldset>
				<div class="sidebar">
<%-- 				<% --%>
<%--				if (exist) {
<%-- 			%> --%>
<!-- 				Vestuur een test mail naar een emailadres naar keuze -->
<!-- 				<input type="text" class="receiver" name="receiver"  placeholder="je-email@domein.nl"> -->
<!-- 				<input	type="submit" class='button-menu' value="verstuur mail" id="verstuurTest" /> <span class='lader'></span> -->
<%-- 				<% --%>
<%--				}
<%-- 			%> --%>
			<br  /><br /><br />
			<p>
			De tags hieronder kunnen gebruikt worden in de tekst. Dit word veranderd in de gegevens van de ontvanger:
			</p>
			<input type="text" value="{voornaam}" title="voornaam van het contact" class="small">
			<input type="text" value="{achternaam}" title="achternaam van het contact" class="small">
			<input type="text" value="{email}" title="email adres van het contact" class="small">
			<input type="text" value="{funtie}" title="de functie in het bedrijf van het contact" class="small">
			<input type="text" value="{bedrijf}" title="het bedrijf waar het contact werkt" class="small">
			<input type="text" value="{contactpersoon}" title="het contactpersoon van dit contact" class="small">
			<input type="text" value="{element1}" title="Dit is element 1, dit is een variabel element die kan worden toegevoegd in de mail." class="small">
			<input type="text" value="{element2}" title="Dit is element 2, dit is een variabel element die kan worden toegevoegd in de mail." class="small">
			<input type="text" value="{element3}" title="Dit is element 3, dit is een variabel element die kan worden toegevoegd in de mail." class="small">		
					
				</div>
				<div class="dynamic">
				
					<div class="row label">
						<label>Naam</label> <input type="text" name="naam"
							value="<%=naam%>" class="mail_content" title="geef aan hoe de mail in dit systeem moet heten" />
					</div>

					<div class="row label">
						<label>Onderwerp e-mail</label> <input type="text"
							name="onderwerp" value="<%=onderwerp%>" class="mail_content"  title="voer het onderwerp van de mail in"/>
					</div>

					<div class="row textblock">
						<label>Website</label> <input type="text" name="link"
							value="<%=link%>" class="mail_content" title="geef aan naar welke website de gebruiker heen gestuurd moet worden in de mail" />
					</div>

					<div class="row textblock">
						<label>Large Header</label> <input type="text" name="image_header"
							value="<%=image_header%>" class="mail_content"  title="voer de titel in die over de image heen komt" />
					</div>

					<div class="row textblock">
						<label>Sub Header</label> <input type="text"
							name="image_subheader" value="<%=image_subheader%>"
							class="mail_content" title="Voer de subtitel in die onder de large header komt." />
					</div>

					<div class="row textblock">
						<label>Image</label> <input type="text" name="image"
							value="<%=image%>" class="mail_content" title="voeg hier de image toe die in de header word toegevoegd">
					</div>

					<div class="row">
						<label>Aanhef</label> <input type="text" name="aanhef"
							value="<%=aanhef%>" class="mail_content" title="voeg hier de aanhef in. gebruik de tags voor een persoonlijk tintje" />
					</div>

					<div class="row textblock">
						<label>Header</label> <input type="text" name="header1"
							value="<%=header1%>" class="mail_content" title="voer eventueel de titel van de eerste paragraaf in(kan ook leeg gelaten worden)"> <label>Content</label>
						<textarea class="mail_content" name="content1" title="voer hier de eerste paragraaf in."><%=content1%></textarea>
					</div>

					<div class="row textblock">
						<label>Header</label> <input type="text" name="header2"
							value="<%=header2%>" class="mail_content" title="voer hier de titel van de tweede paragraaf in"> <label>Content</label>
						<textarea class="mail_content" name="content2" title="voeg hier de tekst van de tweede paragraaf in" ><%=content2%></textarea>
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