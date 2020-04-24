var searching = false;
var selectedIndex = 1;

$("document").ready(function(){
	$("#search_box").prop("disabled", false);
	$(".product_nav").css("display","block");
	UpdateMenu(1);
});

$(window).on("resize", function(){
	$("document").ready(function(){
		var productTableHeight = $(".shop_products_table").outerHeight();
		var screenHeight = window.innerHeight - 155;
		var screenWidth = screen.width;
		
		if(productTableHeight < screenHeight)
			$("footer").css("position", "absolute");
		else
			$("footer").css("position", "relative");
		
		if(selectedIndex == 2 && screenWidth > 570)
			showCategoryMenu(true);
		else
			showCategoryMenu(false);
	});
});

function SelectTab(index) {
	$("document").ready(function(){
		$(".shop_products_table").empty();
		$(".loading_img").css("display","inline-block");
		searching = false;
		
		var elements = [];
		elements = $(".NavButton");
		
		if(elements.length <= 0)
			alert("No elements in array");
		
		var lastElement = document.getElementById("nav_active");
		lastElement.setAttribute("id","");

		var selectedElement = elements[index - 1];
		selectedElement.setAttribute("id", "nav_active");
		
		var screenWidth = screen.width;
		if(index == 2 && screenWidth > 570)
			showCategoryMenu(true);
		else
			showCategoryMenu(false);
		
		selectedIndex = index;		
		startFinding(index);
	});
}

function SelectHTab(index) {
	$("document").ready(function(){
		searching = false;
		
		var elements = [];
		elements = $("#drawer").find("li a");
		
		selectedIndex = index;	

		if(elements.length <= 0)
			showPopup("no elements into array", 3000);
		else
		{
			SelectTab(index)
			
			var checkbox = $("#drawer-toggle");
			checkbox.prop("checked", false);
		}
	});
}

function showCategoryMenu(enabled) {
	if(enabled)
	{
		$(".category_menu").css("display", "block");
		
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
					updateCategoryMenu(data.content);
				else
					showPopup(data.message, 3000);
			},
			error: function(request, error) {
				showPopup("Error: " + error, 3000);
			}
		});
	}
	else
	{
		$(".category_menu").css("display", "none");
	}
}

function updateCategoryMenu(categories) {

	var list = $(".category_menu ul");
	list.empty();
	
	for(var i = 0; i < categories.length; i++)
	{
		var el = "<li>";
		el += "<a onclick='searchProductByCategory(" + categories[i].Code + ")' href='#''>";
		el += categories[i].Name;
		el += "</a></li>";
		
		list.append(el);
	}
}

function searchProductByCategory(categoryId) {
	var request = {
			result: "ok",
			message: "no error here",
			content: {
				code: categoryId
			}
	}
	
	var jsonString = JSON.stringify(request);
	
	$.ajax({
		cache: false,
		url: "CategoryProductController",
		type: "POST",
		data: jsonString,
		dataType: "json",
		success: function(data) {
			if(data.result == "ok")
			{
				$(".shop_products_table").empty();
				UpdateProducts(data.content);
			}
			else
				showPopup(data.message, 3000);
		},
		error: function(request, error) {
			showPopup("Error: " + error, 3000);
		}
	});
}

function startFinding(index) {
	if(index == 4) //Search index
	{
		searching = true;
		SearchProduct();
	}
	else
		UpdateMenu(index);
}

function UpdateMenu(index) {
	var request = {
			result: "ok",
			message: "no error here",
			content: {
				tabIndex: index
			}
	}
	
	var jsonString = JSON.stringify(request);
	
	$.ajax({
		cache: false,
		url: "ProductController",
		type: "POST",
		data: jsonString,
		dataType: "json",
		success: function(data) {
			if(data.result == "ok")
				UpdateProducts(data.content);
			else
				showPopup(data.message, 3000);
		},
		error: function(request, error) {
			showPopup("Error: " + error, 3000);
		}
	});
}

$(document).on("submit", "#search_form", function(e){
	e.preventDefault();
	
	SelectTab(4);
    return false;
});

function SearchProduct() {
	$("document").ready(function(){
		$(".shop_products_table").empty();
		$(".loading_img").css("display","inline-block");
		
	searching = true;
    var keyword = $("#search_box").val();
    
    if(keyword == "")
    {
    	SelectTab(1);
    	searching = false;
    	
    	return false;
    }
    
	var request = {
			result: "ok",
			message: "no error here",
			content: {
				keyword: keyword
			}
	}
	
	var jsonString = JSON.stringify(request);
	
	$.ajax({
		cache: false,
		url: "SearchController",
		type: "POST",
		data: jsonString,
		dataType: "json",
		success: function(data) {
			if(data.result == "ok")
				UpdateProducts(data.content);
			else
				showPopup(data.message, 3000);
		},
		error: function(request, error) {
			showPopup("Error: " + error, 3000);
		}
	});
    
	$(":text").blur();
	});
}

function UpdateProducts(products) {
	if(products != null)
	{
		$(".loading_img").css("display","none");
		
		for(var i = 0; i < products.length; i++)
		{
			if(products[i].Availability == -1)
				continue;
			
			var el = "<tr><th>";
			el += "<div id='product_" + products[i].Code + "'>";
			el += "<img src='" + products[i].Photo + "'>";
			el += "<div class='title'><p><a href='ArticleController?id=" + products[i].Code + "'>" + products[i].Name + "</a></p></div>";
			el += "<div class='author'><p>Author: Daniele De Falco</p></div>";
			el += "<div class='price'><p>EUR  " + products[i].Price + "</p></div>";
			el += "<div class='availability'><p><span>" + products[i].Availability + "</span> more pieces are available, order now</p></div>";
			el += "<div class='separator'></div></div>";
			el += "</th></tr>";
			
			$(".shop_products_table").append(el);
		}
	}
	
	var productTableHeight = $(".shop_products_table").outerHeight();
	var screenHeight = window.innerHeight - 155;
	
	if(productTableHeight < screenHeight)
		$("footer").css("position", "absolute");
	else
		$("footer").css("position", "relative");
}