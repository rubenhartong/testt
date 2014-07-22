<%@include file="/includes/login_check.jsp"%>
<%@page import="model.*"%>
<%@page import="domein.*"%>
<%
	String receiver		= request.getParameter("receiver");
String eventIDs 		= request.getParameter("eventID");
String mailIDs		= request.getParameter("mailID");

    	BezoekerIO bio = new BezoekerIO();
    	MailSendIO mso = new MailSendIO();

    	int mailID 			= Integer.parseInt(mailIDs);
    	
    	MailIO mIo			= new MailIO();
    	EvenementenIO eIo 	= new EvenementenIO();
    	LocatieIO lIo		= new LocatieIO();

    	Evenement event	 	= eIo.getEvent(eventIDs); 
    	Locatie		 locatie 	= lIo.getLocatie(event.getLocatieID());
    	Mail 			mail			= mIo.getGegevens(eventIDs, mailID);
    	MailFormat format = mso.getMailFormat(mailID);
    	
    	String message = format.getFormat();
    	
    	
    	message = message.replaceAll( "\\{background-image\\}" , mail.getImage());
    	message = message.replaceAll( "\\{image-header\\}" , mail.getImage_header());
    	message = message.replaceAll( "\\{image-subheader\\}" , mail.getImage_subheader());
    	message = message.replaceAll( "\\{header1\\}" , mail.getHeader1());
    	message = message.replaceAll( "\\{content1\\}" , mail.getContent1());
    	message = message.replaceAll( "\\{header2\\}" , mail.getHeader2());
    	message = message.replaceAll( "\\{content2\\}" , mail.getContent2());
    	message = message.replaceAll( "\\{aanhef\\}" , mail.getAanhef());
    	
    	message = message.replaceAll( "\\{aanmelden\\}" , format.getElement1());
  		 message = message.replaceAll( "\\{locatie-panel\\}" , format.getElement2());
    	
    	message = message.replaceAll( "\\{adres\\}" , locatie.getAdres());
    	message = message.replaceAll( "\\{postcode\\}" , locatie.getPostcode());
    	message = message.replaceAll( "\\{plaats\\}" , locatie.getPlaats());
    	message = message.replaceAll( "\\{telefoon\\}" , locatie.getTelefoonnummer());
    	message = message.replaceAll( "\\{website\\}" , locatie.getWebsite());
    	message = message.replaceAll( "\\{link\\}" , mail.getLink());

out.println(mIo.SendMail(message, receiver, mail.getOnderwerp()));
%> 