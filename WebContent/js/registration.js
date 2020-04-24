$("document").ready(function(){
	$("#search_box").prop("disabled", true);
	$(".product_nav").css("display","none");
});

$(document).on("submit", "#reg_submit", function(e){
	e.preventDefault();
	
	$(".status").html("");
	checkData();
});

function checkData() {
	var username 		= $("#input_username"		).val();
	var name 			= $("#input_name"			).val();
	var surname 		= $("#input_surname"		).val();
	var email 			= $("#input_email"			).val();
	var password 		= $("#input_password"		).val();
	var repeatPassword 	= $("#input_repeatPassword"	).val();
	var date 			= $("#input_date"			).val();
	
	//Check username
	if(!isValidUsername(username))
	{
		$(".status").html("Username can contain only Letters and Numbers");
		return false;
	}
	
	//Check ConfirmPW
	if(password !== repeatPassword)
	{
		$(".status").html("Password does not match");
		return false;
	}
	
	var request = {
			result: "ok",
			message: "no error here",
			content: {
				username		: username,
				name			: name,
				surname			: surname,
				email			: email,
				password		: password,
				repeatPassword	: repeatPassword,
				birthday		: date
			}
	}
	
	var jsonString = JSON.stringify(request);
	
	$.ajax({
		cache: false,
		url: "RegistrationController",
		type: "POST",
		data: jsonString,
		dataType: "json",
		success: function(data) {
			if(data.result == "ok")
				registrationComplete();
			else
				$(".status").html(data.message);
		},
		error: function(request, error) {
			showPopup("Error: " + error, 3000);
		}
	});
}

function registrationComplete() {
	var html = "<h1 align='center' style='color:green; font-weight:bold;'> Registration complete </h1> <h4 align='center' style='color:#989787;'> Thanks for your registration, you will redirect to main page in <span id='span_time' style='color:black;'>5</span> seconds </h4>";
	$(".logRegDiv").empty();
	$(".logRegDiv").append(html);
	$("footer").css("position", "absolute");
	
	var spanTime = $("#span_time");
	var _seconds = 5;
	
	setInterval(function(){
		if(_seconds <= 0)
			_seconds = 0;
		else
			_seconds -= 1;
		
		spanTime.html(_seconds);
	}, 1000);
	
	setTimeout(function(){
		window.location.href = "/PentaShop/index.jsp";
	}, 5000);
}

function isValidUsername(username) {
	var regex = /^[a-zA-Z0-9]+$/;
	return regex.test(username);
}

function isEmail(email) {
	  var regex = /^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	  return regex.test(email);
}

function validatePW(password) {
	var regex = /^.*(?=.{4,10})(?=.*\d)(?=.*[a-zA-Z]).*$/;
	return regex.test(password);
}