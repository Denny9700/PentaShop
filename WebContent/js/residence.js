$("document").ready(function(){
	$("#search_box").prop("disabled", true);
	$(".product_nav").css("display","none");
});

$(document).on("submit", "#res_submit", function(e){
	e.preventDefault();
	
	$(".status").html("");
	checkData();
});

function checkData() {
	var region  		= $('#input_regions'		).find(":selected").text();
	var city 			= $('#input_cities'			).find(":selected").text();
	var address 		= $("#input_address"		).val();
	var cap 			= $("#input_cap"			).val();
	var user 			= $("#user_id"				).val();
	
	var request = {
			result: "ok",
			message: "no error here",
			content: {
				region		: region,
				city		: city,
				address		: address,
				cap			: cap,
				user		: user
			}
	}
	
	var jsonString = JSON.stringify(request);
	
	$.ajax({
		cache: false,
		url: "ResidenceController",
		type: "POST",
		data: jsonString,
		dataType: "json",
		success: function(data) {
			if(data.result == "ok")
				window.location.href = "/PentaShop/index.jsp";
			else
				$(".status").html(data.message);
		},
		error: function(request, error) {
			showPopup("Error: " + error, 3000);
		}
	});
}

function validateText(evt) {
	var theEvent = evt || window.event;
	  var key = theEvent.keyCode || theEvent.which;
	  key = String.fromCharCode( key );
	  var regex = /[0-9]|\./;
	  if( !regex.test(key) ) {
	    theEvent.returnValue = false;
	    if(theEvent.preventDefault) theEvent.preventDefault();
	  }
}