$(window).load(function() {
	$(".message").hide( );
	var eventID = $(".checkbox  #eventID").val();
	// Ophalen potentiele klanten
	
	getNotinvited();
	getInvited();
	
	function getNotinvited() {
		$.post('not_invited.jsp', {
	
		beforeSend:load('.genodigden'), //Show spinner
        complete: unload('.genodigden'), 
		eventID : eventID,
		}, function(data) {
			$('.genodigden').html(data);
		});
	}
	// Ophalen uitgenodigde klanten
	function getInvited() {
		$.post('invited.jsp', {
			beforeSend:load('.invited'), //Show spinner
	        complete: unload('.invited'),
			eventID : eventID,
		}, function(data) {
			$('.invited').html(data);
		});
	}
	
	function load(div){
		$(div).append("<img class='loader' src='../assets/img/ajax-loader.gif'/>");
	}
	function unload(div){
		$(div+ " loader").remove();
	}
	

	//vul de sidebar met data.
	$('.genodigden, .invited').on('click', 'tr', function() {
		var id = $(this).find('.check').val();
		moreData(id);
	});

	// Toevoegen aan uitgenodigd lijst
	$('#toevoegen').click(function() {
		inviteCostumer();
		$('.genodigden td :checked').closest('tr').appendTo('.invited');
	});
	
	// verwijderen van uitgenodigd lijst
	$('#verwijderen').click(function() {
		uninviteCostumer();
		$('.invited td :checked').closest('tr').appendTo('.genodigden');
	});
	
	$('#syncroniseer').click(function() {
		$.post('ZohoInvite.jsp', {
			beforeSend:load('.genodigden'), //Show spinner
	        complete: unload('.genodigden'), 
			eventID : eventID,
		}, function(data) {
			alert("De contacten zijn gesynchroniseerd");
			getNotinvited();
			getInvited();
			console.log(data);
		});
	});

	// Zoek functie
	$("#search-button").click(function(e) {
		e.preventDefault();
		var search = $(".search-input").val();
		console.log("zoek: " + search);
		$.post('search-invited.jsp', {
			beforeSend:load('.genodigden'), //Show spinner
	        complete: unload('.genodigden'), 
			eventID : eventID,
			search : search
		}, function(data) {
			$('.genodigden').html(data);
		});
		
	});
	// alle checkboxes aanvinken
	$(".selectPotential").click(function() { checkAll('.genodigden tr td .check', this);});
	$(".selectInvited").click(function() { checkAll('.invited tr td .check', this);});
	$("#selectSearch").click(function() { checkAll('.block input', this);});
	
	function checkAll(div, e){
		$(div).filter(':visible').attr('checked', e.checked);
	}

	// richting uitzoeken en actie ondernemen
	$('.searchcheck').click(function() {
		var level = $("#level :checkbox:checked").map(function() {
			return "'" + this.value + "'";
		}).get().join();
		var domain = $("#domain :checkbox:checked").map(function() {
			return "'" + this.value + "'";
		}).get().join();
		
		console.log("De array: \n"+level + "\n volgende:" + domain);

		$.post('select_contacts.jsp', {
			beforeSend:load('.genodigden'), //Show spinner
	        complete: unload('.genodigden'),
			eventID : eventID,
			level : level,
			domain : domain
		}, function(data) {
			$('.genodigden').html(data);
		});
	});

	function inviteCostumer() {
		$('.genodigden tr').each(function() {
			var isChecked = $(this).find(".check").is(':checked');
			console.log("uitnodigen");
			if (isChecked) {
				var id = $(this).find(".check").val();
				console.log("checked: " + id);
				$.post('invite-costumer.jsp', {
					complete: runEffect(),
					eventID : eventID,
					id : id

				}, function(data) {
					$('#print').html(data);
				});
			}
		});
	}

	function uninviteCostumer() {
		$('.invited tr').each(function() {

			var isChecked = $(this).find(".check").is(':checked');
			if (isChecked) {
				var id = $(this).find(".check").val();
				$.post('uninvite-costumer.jsp', {
					eventID : eventID,
					id : id
				}, function(data) {
					$('#print').html(data);
				});
			}
		});
	}
	$('.toggle').click(function() {
		$('.searchform').slideToggle("slow");
	});

	$(window).bind('scroll', function(e) {
		e.stopPropagation();
		var position = $(window).scrollTop();
		if (position > 200) {
			$('.fixed').addClass('sticky');
		} else {
			$('.fixed').removeClass('sticky');
		}
	});

	function moreData(id) {
		$.post('getContact.jsp', {
			beforeSend:load('.right'), //Show spinner
	        complete: unload('.right'),
			id : id,
		}, function(data) {
			$('.right').html(data);
		});
	}
	
	function runEffect() {
		$(".message").show().delay(5000).fadeOut();
    }
});