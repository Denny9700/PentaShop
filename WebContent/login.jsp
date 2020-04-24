<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="javax.servlet.http.HttpSession,penta.shop.User,penta.shop.Cart"%>
<!DOCTYPE html">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		
		<link rel="stylesheet" type="text/css" href="css/header_style.css">
		<link rel="stylesheet" type="text/css" href="css/hamburger_menu.css">
		<link rel="stylesheet" type="text/css" href="css/dropdown_menu.css">
		<link rel="stylesheet" type="text/css" href="css/nav_bar.css">
		<link rel="stylesheet" type="text/css" href="css/log_reg_style.css">
		<link rel="stylesheet" type="text/css" href="css/footer.css">
		<link rel="stylesheet" type="text/css" href="css/popup.css">
		<link rel="shortcut icon" href="img/favicon.ico" type="image/x-icon" />
		
		<style>
			footer { position: absolute; }
			
			@media only screen and (max-width: 570px) {
				footer { position: relative; }
			}
		</style>
		
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
		<script src="js/login.js"></script>
		<script src="js/login_checker.js"></script>
		<script src="js/logout.js"></script>
		<script src="js/dropdown.js"></script>
		<script src="js/cart.js"></script>
		<script src="js/func.js"></script>
		<script src="js/popup.js"></script>

		<title>Register to PentaShop</title>
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
			if(user != null)
				response.sendRedirect("/PentaShop/index.jsp");
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
         		
         		<li id="toDivide"></li>
         	</ul>
   		</nav>
   		
   		<div class="logRegDiv">
   			<form id="log_submit" action="logService" method="post">
   			
   				<div class="LogRegText">
   					<h1>Login</h1>
   					<p>Enter in your Pentashop Account</p>
   				</div>
   				
   				<hr>
   				<label for="username"><b>Username</b></label>
   				<input id="input_username" type="text" autocomplete="off" placeholder="Enter Username" name="username" required>
   				
   				<label for="password"><b>Password</b></label>
   				<input id="input_password" type="password" placeholder="Enter Password" name="password" required>
   				<hr>
   				
   				<label class="status"></label>
   				<button type="submit" class="LogRegBtn">Enter</button>
   			</form>
   		</div>
   		
   		<%@ include file="fragment/footer.jsp" %>   		
</body>
</html>