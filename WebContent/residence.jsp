<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="javax.servlet.http.HttpSession,penta.shop.Cart,penta.shop.User"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		
		<link rel="stylesheet" type="text/css" href="css/header_style.css">
		<link rel="stylesheet" type="text/css" href="css/hamburger_menu.css">
		<link rel="stylesheet" type="text/css" href="css/dropdown_menu.css">
		<link rel="stylesheet" type="text/css" href="css/nav_bar.css">
		<link rel="stylesheet" type="text/css" href="css/footer.css">
		<link rel="stylesheet" type="text/css" href="css/residence.css">
		<link rel="stylesheet" type="text/css" href="css/popup.css">
		<link rel="shortcut icon" href="img/favicon.ico" type="image/x-icon" />
		
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
		<script src="js/login_checker.js"></script>
		<script src="js/logout.js"></script>
		<script src="js/dropdown.js"></script>
		<script src="js/residence.js"></script>
		<script src="js/cart.js"></script>
		<script src="js/func.js"></script>
		<script src="js/popup.js"></script>
		
		<title>PentaShop - Insert residence</title>
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
				<script type="text/javascript">
				$("document").ready(function(){
					$("#user_id").val('<%= user.getUsername() %>');
				});
				</script>
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
   		
   		<div class="residenceDiv">
   			<form id="res_submit" action="resService" method="post">
   			
   				<div class="resText">
   					<h1>Residence</h1>
   					<p>Insert your residence information</p>
   				</div>
   				
   				<hr>
   				<label for="region"><b>Region</b></label>
   				<select id="input_regions" name="regions">
   					<option value="italy">Italy</option>
   				</select>
   				
   				<label for="city"><b>City</b></label>
   				<select id="input_cities" name="cities">
   					<option value="napoli">Napoli</option>
   					<option value="salerno">Salerno</option>
   					<option value="benevento">Benevento</option>
   					<option value="casoria">Casoria</option>
   					<option value="acerra">Acerra</option>
   					<option value="avellino">Avellino</option>
   					<option value="ercolano">Ercolano</option>
   				</select>
   				
   				<label for="address"><b>Address</b></label>
   				<input id="input_address" type="text" autocomplete="off" placeholder="Enter Address" name="address" required>
   				
   				<label for="cap"><b>CAP</b></label>
   				<input id="input_cap" type="text" maxlength="5" onkeypress="validateText(event)" autocomplete="off" placeholder="Enter CAP" name="cap" required>
   				<hr>
   				
   				<input id="user_id" type="hidden" autocomplete="off" name="user">
   				
   				<label class="status"></label>
   				<button type="submit" class="resBtn">Insert Residence</button>
   			</form>
   		</div>
   		
   		<%@ include file="fragment/footer.jsp" %> 
   		
	</body>
</html>