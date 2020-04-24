function logout() {

	var request = {
			result: "ok",
			message: "no error here",
			content: {}
	}
	
	var jsonString = JSON.stringify(request);
	
	$.ajax({
		cache: false,
		url: "LogoutController",
		type: "POST",
		data: jsonString,
		dataType: "json",
		success: function(data) {
			if(data.result == "ok")
				window.location.href = "/PentaShop/index.jsp";
			else
				showPopup(data.message, 3000);
		},
		error: function(request, error) {
			alert("ERROR: " + error);
		}
	});
	
}