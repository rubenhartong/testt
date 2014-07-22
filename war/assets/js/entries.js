$(window).load(function() {
	$(document).ready(function() {
//		function getUrlVars() {
//	    var vars = {};
//	    var parts = window.location.href.replace(/[?&]+([^=&]+)=([^&]*)/gi, function(m,key,value) {
//	        vars[key] = value;
//	    });
//	    return vars;
//	}
//	 var eventID = getUrlVars()["eventID"];
//	 console.log("dicobal" + eventID);
		
	    $("#tabs-nav li a").click(function() {
	 
	        $("#ajax-content").empty().append("<div id='loading'><img src='../assets/img/ajax-loader.gif' alt='Loading' /></div>");
	        $("#tabs-nav li a").removeClass('current');
	        $(this).addClass('current');
	 
	        $.ajax({ url: this.href, success: function(html) {
	            $("#ajax-content").empty().append(html);
	            }
	    });
	    return false;
	    });
	    
		$('#ajax-content').on('click', '.table, .events .contact', function(e) {
			var id = $(this).attr("value");
			var entry = $(this).attr("entry");
			console.log(id + " " + entry);
			$("#screen").empty().append("<div id='loading'><img src='../assets/img/ajax-loader.gif' alt='Loading' /></div>");
			$.post('getContact.jsp', {
				id : id,
				entry:entry
			}, function(data) {
				$("#screen").html(data);
			});
			$("#popup").toggle();
			
			e.stopImmediatePropagation();
		});
		
		$("#popup, .closeButton").click(function() {
			
			$("#popup").toggle();
		});
		$("#screen").click(function() {
			debugger;
			return false;
		});

		$('body').on('click', '.closeButton', function(e) {
			alert("hoi");
		});
	});


});

