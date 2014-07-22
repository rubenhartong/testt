$(window).load(function() {

$('.click').click(function() {
	    if($(this).parent().attr('href') !== undefined)
	{
	    document.location = $(this).parent().attr('href');
	}
});

$('.send').click(function(event) {
	event.preventDefault();
	var mail =  $(this).attr('mail');
	var event =  $(this).attr('event');
	console.log(mail + event);
	 ajaxhandler(mail, event, this);
	 
});

function ajaxhandler(mail, event, e){
	$.post('sendmail.jsp', {
		beforeSend:load(e), //Show spinner
	    complete: unload(e),
		mailID : mail,
		eventID : event,
	}, function(data) {
		alert(data);
	});
}

function load(div){
	$(div).html("<img class='loader' src='assets/img/ajax-loader.gif'/>");
}
function unload(div){
	$(div).remove();
}

});
