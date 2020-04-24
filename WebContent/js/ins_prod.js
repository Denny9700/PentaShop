$("document").ready(function(){
	$("#search_box").prop("disabled", true);
	$(".product_nav").css("display","none");
	
	getCategories();
});

function getCategories() {
	var request = {
			result: "ok",
			message: "no error here",
			content: {}
	}
	
	var jsonString = JSON.stringify(request);
	
	$.ajax({
		cache: false,
		url: "CategoryController",
		type: "POST",
		data: jsonString,
		dataType: "json",
		success: function(data) {
			if(data.result == "ok")
				populateList(data.content);
			else
				showPopup(data.message, 3000);
		},
		error: function(request, error) {
			showPopup("Error: " + error, 3000);
		}
	});
}

function populateList(categories) {
	var list = $("#input_categories");
	list.empty();
	
	for(var i = 0; i < categories.length; i++)
		list.append(new Option(categories[i].Name, categories[i].Code));
}

$(document).on("submit", "#prod_submit", function(e){
	e.preventDefault();
	
	uploadProduct();
});

function uploadProduct() {
	
	var name 			= $("#input_prodName"			).val();
	var description 	= $("#input_prodDescription"	).val();
	var photo           = $("#input_prodImg"			).val();
	var price 			= $("#input_prodPrice"			).val();
	var availability 	= $("#input_prodAvailability"	).val();
	var category  		= $('#input_categories'			).find(":selected").val();
	var window 			= $('#input_window'				).find(":selected").val();
	
	var request = {
			result: "ok",
			message: "no error here",
			content: {
				name 			: name,
				description 	: description,
				photo			: photo,
				price 			: price,
				availability 	: availability,
				category 		: category,
				window 			: window
			}
	}
	
	var jsonString = JSON.stringify(request);
	
	$.ajax({
		cache: false,
		url: "InsertArticleController",
		type: "POST",
		data: jsonString,
		dataType: "json",
		success: function(data) {
			if(data.result == "ok")
				insertDone();
			else
				showPopup(data.message, 3000);
		},
		error: function(request, error) {
			showPopup("Error: " + error, 3000);
		}
	});
	
}

function insertDone() {
	window.location.href = "/PentaShop/index.jsp";
}