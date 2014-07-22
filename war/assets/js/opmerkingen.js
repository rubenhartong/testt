$(window).load(function() {
	var eventID = $("#eventID").val();
	var gebruikerID = $(" #genodigdeID").val();
	getOpmerkingen();
	getActie();

	// Ophalen uitgenodigde klanten
	function getOpmerkingen() {
	$.post('getOpmerkingen.jsp', {
		beforeSend:load('.invited'), //Show spinner
		eventID : eventID,
		genodigdeID : gebruikerID,
	}, function(data) {
		$('.opmerkingen').html(data);
	}) . fail(function() {
		alert( "Er is een error is het systeem. Probeer de pagina te verversen." );
		 unload('.invited');
	  });
	}
	$('#addOpmerking').click(function(event) {
		event.preventDefault();
		var opmerking = $("#opmerking").val();
		$.post('setStatus.jsp', {
			opmerking : opmerking,
			genodigdeID : gebruikerID,
			eventID : eventID,
		}, function(data) {
			$(".opmerkingen").prepend(data);
			alert("de opmerking is toegevoegd");
			
		});
	});
	
	$('#sendtemplate').click(function(event) {
		event.preventDefault();
		var mail = $("#template").val();
		$.post('mail.jsp', {
			mailid : mail,
			genodigdeID : gebruikerID,
			eventID : eventID,
		}, function(data) {
			alert(data);
			getOpmerkingen();
		});
	});
	
	$('#sendSingleMail').click(function(event) {
		event.preventDefault();
		var to = $("#singleMail_receiver").val();
		var from = $("#singleMail_user").val();
		var subject = $("#singleMail_onderwerp").val();
		var message = $("#singleMail_message").val();
		$.post('mailSingle.jsp', {
			from : from,
			to : to,
			subject : subject,
			message : message,
		}, function(data) {
			alert(data);
			getOpmerkingen();
		});
	});
	
	$('#addTodo').click(function(event) {
		event.preventDefault();
		var actor = $("#actor").val();
		var datum = $("#date").val();
		var text = $("#todo").val();
		$.post('setTodo.jsp', {
			text : text,
			datum : datum,
			actor : actor,
			genodigdeID : gebruikerID,
			eventID : eventID,
		}, function(data) {
			$(".todolist").prepend(data);
			alert("de todo is toegevoegd.");
		});
	});
	
	$('#addLead').click(function(event) {
		event.preventDefault();
		var owner_id 		= $("#leading").val();
		var owner_email 	= $("#leading :selected").attr('email');
		var company 		= $("#leadCompany").val();
		var firstName 		= $("#leadFirst").val();
		var lastName 		= $("#leadLast").val();
		var level 				= $("#leadLevel").val();
		var email 				= $("#leadEmail").val();
		var phone 				= $("#leadPhone").val();
		var mobile 			= $("#leadMobile").val();
		var description		= $("#lead").val();
		
		$.post('setLead.jsp', {
			owner_id : owner_id,
			owner_email : owner_email,
			company : company,
			firstName : firstName,
			lastName : lastName,
			level : level,
			email : email,
			phone : phone,
			mobile : mobile,
			description : description
		}, function(data) {
			alert("de lead is toegevoegd.");
		});
	});
	
	$('#addZohoOpmerking').click(function(event) {
		event.preventDefault();
		var title 					= $("#opmerking-title").val();
		var description 		= $("#zohoOpmerking").val();
		var contact 			= $("#genodigdeID").val();

		$.post('setZhohoOpmerking.jsp', {
			contact : contact,
			title : title,
			description : description,
		}, function(data) {
			alert("de opmerking is toegevoegd.");
		});
	});
	
	function getActie(){
		$.post('../actie/actielistContact.jsp', {
			eventID : eventID,
			genodigdeID : gebruikerID,
		}, function(data) {
			$('.todolist').html(data);
		});
	}
	$('.todolist').on('click', '.remove', function() { 
		var id = $(this).attr('value');
		remove(id);
		$(this).parent().parent().fadeOut();
	});
	function remove(id){
		$.post('../actie/deleteActie.jsp', {
			id : id,
		});
	}
	
	$(".status input:radio").change(function() {
		var name = $(this).attr("name");
		var value = $(this).val();
		changeHandlerStatus(name, value);
	});
	$(".status .range").change(function() {
		var name = $(this).attr("name");
		var value = $(this).val();
		changeHandlerStatus(name, value);
		$(".currentprio").html(value);
	});
	
	function changeHandlerStatus(name, value) {
		$.get( "setRadioStatus.jsp", { name: name, value: value, eventID:eventID, gebruikerID:gebruikerID } );
	}
	
	function load(div){
		$(div).append("<img class='loader' src='../assets/img/ajax-loader.gif'/>");
	}
	function unload(div){
		$(div+ " .loader").remove();
	}
});