function IncrementCart(quantity){
	$("document").ready(function(){
		var element = $("div#cart_quantity h6");
		var currNumber = parseInt(element.text());
		if(currNumber >= 99)
			element.text("99+");
		else
			element.text(quantity);
	});
}

$(function(){	
	$("document").ready(function(){
	$("#cart_img").hover(function()
	{ $("#cart_img").attr("src", "img/cart_hover.svg"); },
	function() { $("#cart_img").attr("src", "img/cart.svg"); });
	});
});

function getQuantity() {
	var quantity = $("#prod_quantity").val();
	return quantity;
}

function insertElement(elementId, quantity) {
	var request = {
			result: "ok",
			message: "no error here",
			content: {
				id : elementId,
				quantity : quantity
			}
	}
	
	var jsonString = JSON.stringify(request);
	
	$.ajax({
		cache: false,
		url: "UpdateCartController",
		type: "POST",
		data: jsonString,
		dataType: "json",
		success: function(data) {
			if(data.result == "ok")
				updateCartInfo(data.content.quantity);
			else
				showPopup(data.message, 3000);
		},
		error: function(request, error) {
			showPopup("Error: " + error, 3000);
		}
	});
}

function updateCartInfo(quantity) {
	IncrementCart(quantity);
	showPopup("Product added to cart", 3000);
}