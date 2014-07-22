
	$('#verstuurTest').click(function(){
		event.preventDefault();
		var eventID = $('.eventID').val();
		var mailID = $('.mailID').val();
		var receiver = $('.receiver').val();
		$.post('sendSingleMail.jsp', {
			beforeSend:load('.lader'), //Show spinner
			eventID : eventID,
			mailID : mailID,
			receiver : receiver
		}, function(data) {
			alert(data + receiver);
			unload('.lader');
		});
	});	
	
	function load(div){
		$(div).append("<img class='loader' src='../assets/img/ajax-loader.gif'/>");
	}
	function unload(div){
		$(div + " .loader").remove();
	}



