$("document").ready(function(){
	CheckUser();	
});

function CheckUser() {
	var request = {
			result: "ok",
			message: "no error here",
			content: {}
	}
	
	var jsonString = JSON.stringify(request);
	
	$.ajax({
		cache: false,
		url: "UserController",
		type: "POST",
		data: jsonString,
		dataType: "json",
		success: function(data) {
			if(data.result == "ok")
				updateView(true, data.content);
			else if(data.result == "not_logged_in")
				updateView(false, null);
			else
				showPopup("generic error", 3000);
		},
		error: function(request, error) {
			showPopup("Error: " + error, 3000);
		}
	});
}

function updateView(isLoggedIn, informations) {
	if(isLoggedIn)
	{
		$("#user_no_reg").css("display", "none");
		$("#user_login").css("display", "inline-block");

		$("#user_name").html(informations.username);
	}
	else
	{
		$("#user_no_reg").css("display", "inline-block");
		$("#user_login").css("display", "none");
	}
}