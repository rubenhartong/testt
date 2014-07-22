$(window).load(function() {

	var eventID = $(".checkbox  #eventID").val();
	// Ophalen potentiele klanten
	getActie();
	
	//vul de sidebar met data.
	$('.actielist').on('click', 'tr', function() { 
		var id = $(this).attr('value');
		moreData(id);
	});
	
	$('.actielist').on('click', '.remove', function() { 
		var id = $(this).attr('value');
		remove(id);
		$(this).parent().parent().fadeOut();
	});

	// Zoek functie
	$(".search-input").keyup(function() {
		var filter = $(this).val(), count = 0;

		$(".actielist tr").each(function() {
			if ($(this).text().search(new RegExp(filter, "i")) < 0) {
				$(this).fadeOut();
			} else {
				$(this).show();
				count++;
			}
		});
	});
	
	function remove(id){
		$.post('actie/deleteActie.jsp', {
			id : id,
		});
	}


	function moreData(id) {
		$.post('actie/getContact.jsp', {
			id : id,
		}, function(data) {
			$('.right').html(data);
		});
	}
	
	function getActie(){
		$.post('actie/actielist.jsp', {
			eventID : eventID,
		}, function(data) {
			$('.actielist').html(data);
		});
	}
});
