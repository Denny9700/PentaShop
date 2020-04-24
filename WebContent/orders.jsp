<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="javax.servlet.http.HttpSession,penta.shop.Cart,penta.shop.Article,penta.shop.User,
    							 penta.database.PentaCRUD, penta.shop.Purchasing,java.util.ArrayList"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		
		<link rel="stylesheet" type="text/css" href="css/orders.css">
		<link rel="stylesheet" type="text/css" href="css/header_style.css">
		<link rel="stylesheet" type="text/css" href="css/hamburger_menu.css">
		<link rel="stylesheet" type="text/css" href="css/dropdown_menu.css">
		<link rel="stylesheet" type="text/css" href="css/nav_bar.css">
		<link rel="stylesheet" type="text/css" href="css/footer.css">
		<link rel="stylesheet" type="text/css" href="css/popup.css">
		<link rel="shortcut icon" href="img/favicon.ico" type="image/x-icon" />
		
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
		<script src="js/login_checker.js"></script>
		<script src="js/logout.js"></script>
		<script src="js/dropdown.js"></script>
		<script src="js/cart.js"></script>
		<script src="js/func.js"></script>
		<script src="js/popup.js"></script>
		<script src="js/orders.js"></script>
		
		<title>PentaShop - User Orders</title>
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
   		
   		<div class="order_products">
		 	 <table class="order_products_table">
		 	 <!-- 
		 	 <tr>
		 	 	<th>
		 	 		<div id='product_1'>
		 	 		<img src=''>
		 	 		<div class='title'><p><a href='ArticleController?id=1'>Test</a></p></div>
		 	 		<div class='author'><p>Author: Daniele De Falco</p></div>
		 	 		<div class='price'><p>EUR  500</p></div>
		 	 		<div class='availability'><p><span>Manc na un</span> more pieces are available, order now</p></div>
		 	 		</div>
	
		 	 		<div class='separator'></div>
		 	 		
		 	 	</th>
		 	 </tr>
		 	 
		 	 <tr>
		 	 	<th>
		 	 		<div id='product_1'>
		 	 		<img src=''>
		 	 		<div class='title'><p><a href='ArticleController?id=1'>Test</a></p></div>
		 	 		<div class='author'><p>Author: Daniele De Falco</p></div>
		 	 		<div class='price'><p>EUR  500</p></div>
		 	 		<div class='availability'><p><span>Manc na un</span> more pieces are available, order now</p></div>
		 	 		</div>
		 	 		
		 	 		<div class="total_spent">Totale speso: 5€</div>
		 	 		<div class='separator'></div>
		 	 		<div class='separator'></div>
		 	 		
		 	 	</th>
		 	 </tr>
		 	  -->
		 	 
		 	 <%
		 	 	User user = (User)session.getAttribute("user");
		 	 	if(user != null)
		 	 	{
		 	 		PentaCRUD crud = PentaCRUD.getInstance();
			 	 	ArrayList<Purchasing> purchasings = crud.findPurchasingByUser(user.getUsername());
			 	 	
			 	 	double price = 0;
			 	 	
			 	 	for(int i = 0; i < purchasings.size(); i++)
			 	 	{
			 	 		Purchasing purchasing = purchasings.get(i);
			 	 		purchasing = crud.fillPurchase(purchasing);
			 			Article article = purchasing.getArticleDto();
			 			
			 			price += (purchasing.getPrice() * purchasing.getQuantity());
			 %>
			 			<tr>
		 	 				<th>
		 	 					<div id='product_<%= article.getCode() %>'>
		 	 						<img src='<%= article.getPhoto() %>'>
		 	 						<div class='title'><p><a href='ArticleController?id=<%= article.getCode() %>'><%= article.getName() %></a></p></div>
		 	 						<div class='author'><p>Author: Daniele De Falco</p></div>
		 	 						<div class='price'><p>EUR  <%= article.getPrice() %></p></div>
		 	 						<div class='availability'><p><span><%= purchasing.getQuantity() %></span> purchased items</p></div>
		 	 					</div>
		 	 					
		 	 					<div class='separator'></div>
		 	 				</th>
		 	 			</tr>
			 <%
			 	 	}
			 	 	
			 %>
			 			<div class="total_spent">Total Spent: EUR <%= Math.round(price * 100) / 100.0 %></div>
			 <%
			 	 	
		 	 	}
		 	 	
		 	 %>
		 	 
		 	 </table>
		 </div>
   		
	</body>
</html>