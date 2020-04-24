<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="javax.servlet.http.HttpSession,penta.shop.Cart,penta.shop.User"%>
<!DOCTYPE html">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		
		<link rel="stylesheet" type="text/css" href="css/header_style.css">
		<link rel="stylesheet" type="text/css" href="css/hamburger_menu.css">
		<link rel="stylesheet" type="text/css" href="css/dropdown_menu.css">
		<link rel="stylesheet" type="text/css" href="css/insert_product.css">
		<link rel="stylesheet" type="text/css" href="css/nav_bar.css">
		<link rel="stylesheet" type="text/css" href="css/footer.css">
		<link rel="stylesheet" type="text/css" href="css/popup.css">
		<link rel="shortcut icon" href="img/favicon.ico" type="image/x-icon" />
		
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
		<script src="js/login_checker.js"></script>
		<script src="js/logout.js"></script>
		<script src="js/dropdown.js"></script>
		<script src="js/ins_prod.js"></script>
		<script src="js/cart.js"></script>
		<script src="js/func.js"></script>
		<script src="js/popup.js"></script>
		
		<title>PentaShop - Insert article</title>
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
		
		<%
		
		if(session != null)
		{
			User user = (User)session.getAttribute("user");
			if(user == null)
				response.sendRedirect("/PentaShop/index.jsp");
			else
			{
		%>
				<!-- Do nothing -->
		<%
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
   		
   		<div class="insProduct">
   			<div class="insProdText">
   				<h1>Insert Product</h1>
   				<p>Insert your product into shop</p>
   			</div>
   			
   			<hr>
   		
   			<form id="prod_submit" action="resService" method="post">
   					<label for="prod_name"><b>Article Name</b></label>
   					<input id="input_prodName" type="text" autocomplete="off" placeholder="Enter Article Name" name="prodName" required>
   				
   					<label for="prod_description"><b>Article Description</b></label>
   					<textarea id="input_prodDescription" placeholder="Enter Article Description" name="prodDescription" cols="40" rows="5" required></textarea>
   					
   					<label for="prod_price"><b>Price</b></label>
   					<input id="input_prodPrice" type="text" autocomplete="off" placeholder="Enter Article Price" name="prodPrice" required>
   					
   					<label for="prod_availability"><b>Article Availability</b></label>
   					<input id="input_prodAvailability" type="text" autocomplete="off" placeholder="Enter Article Availability" name="prodAvailability" required>
   					
   					<label for="prod_img"><b>Article Photo URL</b></label>
   					<input id="input_prodImg" type="text" autocomplete="off" placeholder="Enter Article Image URL" name="prodImg" required>
   					
   					<label for="categories"><b>Category</b></label>
   					<select id="input_categories" name="categories">
   						<!-- Auto populate -->
   					</select>
   					
   					<label for="window"><b>Shop Windows</b></label>
   					<select id="input_window" name="window">
   						<option value="1">Flash Deals</option>
   						<option value="2">Components</option>
   						<option value="3">Pre Assembled</option>
   					</select>
   				<hr>
   				
   				<button type="submit" class="button_prod">Insert Article</button>
   			</form>
   		</div>
   		
   		<%@ include file="fragment/footer.jsp" %>
	</body>
</html>