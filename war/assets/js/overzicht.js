$(window).load(function() {
	getUpcomingEvents(true);
	
	$(".search-input").keyup(function() {
		var filter = $(this).val();

		$(".events tr").each(function() {
			if ($(this).text().search(new RegExp(filter, "i")) < 0) {
				$(this).fadeOut();
			} else {
				$(this).show();
			}
		});
	});
	
	function getUpcomingEvents(past) {
		$.post('includes/getUpcomingEvents.jsp', {
			beforeSend:load('.events'), //Show spinner
		}, function(data) {
			$('.events').html(data);
			if(past){
				getPastEvents(false);
				} 
		}) . fail(function() {
			alert( "Er is een error is het systeem. Probeer de pagina te verversen." );
			unload('.events');
		  });
		}
	function getPastEvents(replace) {
		$.post('includes/getPastEvents.jsp', {
			beforeSend:load('.events'), //Show spinner
		}, function(data) {
			if(replace){
			$('.events').html(data);
			} else {
				$('.events').append(data);
			}
			unload('.events');
		}) . fail(function() {
			alert( "Er is een error is het systeem. Probeer de pagina te verversen." );
			 unload('.events');
		  });
		}
	$(".checkbox").change(function() {
	var e = 	$("select[name=select]").val();
	console.log(e);
	if (e == 3){
		getUpcomingEvents(false);
	} else if (e == 2){
		getPastEvents(true);
	} else {
		getUpcomingEvents(true);
	}
	});
	
	$('.table').on('click', 'tr td a.removed', function(event) { 
		event.preventDefault();
		var id = $(this).attr('value');
		remove(id);
		$(this).parent().parent().fadeOut();
	});

	function remove(id) {
		
		$.post('includes/removeEvent.jsp', {
			id : id,
		}, function(data) {
		}) . fail(function() {
			alert( "Er is een error is het systeem. Probeer de pagina te verversen." );
		  });
		}
	
	function load(div){
		$(div).append("<img class='loader' src='assets/img/ajax-loader.gif'/>");
	}
	function unload(div){
		$(div+ " .loader").remove();
	}
});
