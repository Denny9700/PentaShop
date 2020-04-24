$("document").ready(function(){
	$("#search_box").prop("disabled", true);
	$(".product_nav").css("display","none");
});

function removeArticle(divId) {
	var element = $("#product_" + divId);
	element.remove();
}

function removeArticleFromSession(prod) {
	
	var request = {
			result: "ok",
			message: "no error here",
			content: {
				id : prod
			}
	}
	
	var jsonString = JSON.stringify(request);
	
	$.ajax({
		cache: false,
		url: "CartDeleteController",
		type: "POST",
		data: jsonString,
		dataType: "json",
		success: function(data) {
			if(data.result == "ok")
			{
				removeArticle(prod);
				updateInfo(data.content.cartSize, data.content.total);
			}
			else
				showPopup(data.message, 3000);
		},
		error: function(request, error) {
			showPopup("Error: " + error, 3000);
		}
	});
	
}

function updateInfo(cartSize, total) {
	IncrementCart(cartSize);
	$(".total").html("Total purchases (" + cartSize + " articles): <br> <span class='t_money'> EUR: " + total.toFixed(2) + "</span>");
}

function buyAll() {
	var request = {
			result: "ok",
			message: "no error here",
			content: {}
	}
	
	var jsonString = JSON.stringify(request);
	
	$.ajax({
		cache: false,
		url: "BuyController",
		type: "POST",
		data: jsonString,
		dataType: "json",
		success: function(data) {
			if(data.result == "ok")
				successMessage();
			else
				showPopup(data.message, 3000);
		},
		error: function(request, error) {
			alert("ERROR: " + error);
		}
	});
}

function successMessage() {
	$(".cart_elements").empty();
	
	var html = "<style> .cart_elements{padding-top: 100px;} </style> <h1 align='center' style='color:green; font-weight:bold;'> Purchase Completed </h1> <h4 align='center' style='color:#989787;'> Thanks for your purchase </h4>";
	$(".cart_elements").append(html);
	//Costruzione pagina dinamica
}