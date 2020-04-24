<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="javax.servlet.http.HttpSession,penta.shop.Cart,penta.shop.Article,penta.shop.User"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		
		<link rel="stylesheet" type="text/css" href="css/product.css">
		<link rel="stylesheet" type="text/css" href="css/header_style.css">
		<link rel="stylesheet" type="text/css" href="css/hamburger_menu.css">
		<link rel="stylesheet" type="text/css" href="css/dropdown_menu.css">
		<link rel="stylesheet" type="text/css" href="css/nav_bar.css">
		<link rel="stylesheet" type="text/css" href="css/footer.css">
		<link rel="stylesheet" type="text/css" href="css/popup.css">
		<link rel="shortcut icon" href="img/favicon.ico" type="image/x-icon" />
				
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
		<script src="js/product.js"></script>
		<script src="js/login_checker.js"></script>
		<script src="js/logout.js"></script>
		<script src="js/dropdown.js"></script>
		<script src="js/cart.js"></script>
		<script src="js/func.js"></script>
		<script src="js/popup.js"></script>

		<title>Shopping Online With PentaShop</title>
	</head>
	<body>
		<%
		
		HttpSession _session = request.getSession();
		if(_session != null)
		{
			synchronized(_session){
				Cart cart = (Cart)_session.getAttribute("cart");
				if(cart == null)
					_session.setAttribute("cart", new Cart());
				else
				{
					int cart_element = cart.size();
		%>
					<script>
						IncrementCart(<%= cart_element %>);
					</script>
		<%
				}
			}
		}
		
		%>
		
		<%@ include file="fragment/header.jsp" %>
		
		<input type="checkbox" id="drawer-toggle" name="drawer-toggle"/>
   		<label for="drawer-toggle" id="drawer-toggle-label"></label>
   		<nav id="drawer">
      		<ul>         		
         		<%
         		
         		if(_session != null)
         		{
         			User user = (User)session.getAttribute("user");
         			if(user == null)
         			{
         		%>
         			<li><a href="login.jsp">Login</a></li>
         			<li><a href="registration.jsp">Register</a></li>
         			<li><a href="cart.jsp">Cart</a></li>
         		<%
         			}
         			else
         			{
         		%>
         			<li><a href="insert_product.jsp">Insert product</a></li>
    				<li><a href="residence.jsp">Insert address</a></li>
    				<li><a href="orders.jsp">Show orders</a></li>
    				<li><a href="cart.jsp">Cart</a></li>
    				
    				<li id="toDivide"></li>
    				
         			<li><a onclick="logout()" href="#">Logout</a></li>
         		<%
         			}
         		}
         		else
         		{
         		%>
         			<li><a href="login.jsp">Login</a></li>
         			<li><a href="registration.jsp">Register</a></li>
         			<li><a href="cart.jsp">Cart</a></li>
         		<%
         		}
         		
         		%>
         	</ul>
   		</nav>
   		
   		<%
   		
   		Article article = (Article)request.getAttribute("article");
   		if(article == null)
   			response.sendRedirect("/PentaShop/index.jsp");
   		
   		%>
		 
		 <div class="product">

		 	<img src="<%= article.getPhoto() %>">
		 	<div class="title"><%= article.getName() %></div>
		 	<div class="author">Author: Daniele De Falco</div>
		 	<div class="price"><span class="p_text">Advised Price: </span>EUR <%= article.getPrice() %></div>
		 	<div class="availability"><span class="number"><%= article.getAvailability() %></span> more pieces are available, order now</div>
		 	<div class="quantity"> Quantity: <input type="number" id="prod_quantity" value="1" name="prod_quantity" min="1" max="<%= article.getAvailability() %>"></div>
		 	<button class="cartBtn" onclick="insertElement(<%= article.getCode() %>, getQuantity())">Add to Cart</button>
		 	
		<%
		
			if(!article.isAvailable())
			{
		%>
				<script>
					$(".cartBtn").css("display", "none");
					$(".quantity").css("display", "none");
				</script>
		<%
			}
		
		%>
		 	
		 	<div class="description">
		 		<h4> Description </h4>
		 		<div class="description_text"><%= article.getDescription() %></div>
		 	</div>
		 	
		 </div>
		 
	</body>
</html>