$(window).load(function() {
	var slide = 0;
	var eventID = $(".checkbox  #eventID").val();
	getInvited();
	$(".slider").click(function(){
		console.log(slide);
		if(slide == 0) {
			$( ".changes" ).animate({left: "0"}, 1000);
			slide =+ 1;
		} else if(slide = 1) {
			$( ".changes" ).animate({left: "-174"}, 1000);
			slide =- 0;
		}
	});

	// Ophalen uitgenodigde klanten
	function getInvited(){
	$.post('getInvited.jsp', {
		beforeSend:load('.invited'), //Show spinner
		eventID : eventID,
	}, function(data) {
		$('.invited').html(data);
	}) . fail(function() {
		alert( "Er is een error is het systeem. Probeer de pagina te verversen." );
		 unload('.invited');
	  });
	}
	

	//vul de sidebar met data.
	$('.invited').on('click', 'tr', function() {
		var id = $(this).attr('value');
		moreData(id);
	});

	// Zoek functie
	$(".search-input").keyup(function() {
		var filter = $(this).val(), count = 0;

		$(".invited tr").each(function() {
			if ($(this).text().search(new RegExp(filter, "i")) < 0) {
				$(this).fadeOut();
			} else {
				$(this).show();
				count++;
			}
		});
	});
	
	$('#sendtemplate').click(function(event) {
		event.preventDefault();
		if(window.confirm("Weet je zeker dat je de geselecteerde mensen wilt mailen?")) {
		var mail = $("#template").val();
		$('.invited tr').each(function() {
			var isChecked = $(this).find("input").is(':checked');			
			if (isChecked) {
				var id = $(this).attr("value");
				
				$.post('mail.jsp', {
					mailid : mail,
					genodigdeID : id,
					eventID : eventID
				});
			}
			
		});
		alert("mails worden verstuurd");
		}
	});
	
	//do something with checked boxes
	$('#changeStatus').click(function(event) {
		var name = $(".bulk-status").val();
		$('.invited tr').each(function() {
			var isChecked = $(this).find("input").is(':checked');
			
			if (isChecked) {
				var id = $(this).attr("value");
				
				$.post("setBulkStatus.jsp", {
					eventID : eventID,
					name: name,
					gebruikerID : id,
				}, function() {
					getInvited();
				});
			}
		});
	});
	
	$('#changeApperance').click(function(event) {
		var value = $(".bulk-apperance").val();
		$('.invited tr').each(function() {
			var isChecked = $(this).find("input").is(':checked');
			
			if (isChecked) {
				var id = $(this).attr("value");
				
				$.post("setBulkApperance.jsp", {
					eventID : eventID,
					value: value,
					gebruikerID : id,
				}, function() {
					getInvited();
				});
			}
		});
	});
	
	$('.sort').change(function() {
		var status = $(this).val();
		$('.invited').html(load('.invited'));
		$.post('getInvitedSorted.jsp', {
			sortBy : status,
			eventID : eventID
		}, function(data) {
			$('.invited').html(data);
		});
	});
	
	function runEffect() {
		$(".message").show().delay(5000).fadeOut();
    }
	
	// alle checkboxes aanvinken
	$(".selectall").click(function() { checkAll('.invited tr td input', this);});
	
	function checkAll(div, e){
		$(div).filter(':visible').attr('checked', e.checked);
	}

	function moreData(id) {
		$.post('getContact.jsp', {
			beforeSend:load('.right'), //Show spinner
	        complete: unload('.right'), 
			id : id,
		}, function(data) {
			$('.right').html(data);
		});
	}

});

function load(div){
	$(div).append("<img class='loader' src='../assets/img/ajax-loader.gif'/>");
}
function unload(div){
	$(div+ " .loader").remove();
}