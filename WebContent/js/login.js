$("document").ready(function(){
	$("#search_box").prop("disabled", true);
	$(".product_nav").css("display","none");
});

$(document).on("submit", "#log_submit", function(e){
	e.preventDefault();
	
	$(".status").html("");
	login();
});

function login() {
	var username = $("#input_username").val();
	var password = $("#input_password").val();
	
	var request = {
			result: "ok",
			message: "no error here",
			content: {
				username : username,
				password : password
			}
	}
	
	var jsonString = JSON.stringify(request);
	
	$.ajax({
		cache: false,
		url: "LoginController",
		type: "POST",
		data: jsonString,
		dataType: "json",
		success: function(data) {
			if(data.result == "ok")
				loginComplete();
			else
				$(".status").html(data.message);
		},
		error: function(request, error) {
			showPopup("Error: " + error, 3000);
		}
	});
}

function loginComplete() {
	window.location.href = "/PentaShop/index.jsp";
}