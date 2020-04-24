<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="javax.servlet.http.HttpSession,penta.shop.Cart,penta.shop.Article,penta.shop.User,penta.shop.Purchasing,
    							java.util.ArrayList,penta.database.dto.ResidenceDto"%>
<!DOCTYPE html">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		
		<link rel="stylesheet" type="text/css" href="css/header_style.css">
		<link rel="stylesheet" type="text/css" href="css/hamburger_menu.css">
		<link rel="stylesheet" type="text/css" href="css/dropdown_menu.css">
		<link rel="stylesheet" type="text/css" href="css/nav_bar.css">
		<link rel="stylesheet" type="text/css" href="css/cart_product.css">
		<link rel="stylesheet" type="text/css" href="css/footer.css">
		<link rel="stylesheet" type="text/css" href="css/popup.css">
		<link rel="shortcut icon" href="img/favicon.ico" type="image/x-icon" />
		
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
		<script src="js/login_checker.js"></script>
		<script src="js/logout.js"></script>
		<script src="js/dropdown.js"></script>
		<script src="js/cart_script.js"></script>
		<script src="js/cart.js"></script>
		<script src="js/func.js"></script>
		<script src="js/popup.js"></script>
		
		<title>PentaShop - Your Cart</title>
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
   		
   		Cart cart = null;
	 	 synchronized(_session) {
	 		 cart = (Cart)_session.getAttribute("cart");
	 	 }
	 	 
	 	 if(cart.size() <= 0)
	 	 {
	 	%>
	 		<script>
	 			showPopup("Cart is empty, add a product first", 5000);
	 		</script>
	 	<%
	 	 }
   		
   		%>
   		
   		<div class="cart_elements">
   		<div class="cart_products">
		 	 <table class="cart_products_table">
		 	<%		 	 
		 	 for(int i = 0; i < cart.size(); i++)
		 	 {
		 		 Purchasing purchasing = cart.getPurchasingByIndex(i);
		 		 Article article = purchasing.getArticleDto();
		 	%>
		 	 
		 	 	<tr><th>
		 	 	<div id='product_<%= article.getCode() %>'>
		 	 	<img src='<%= article.getPhoto() %>'>
		 	 	<div class='title'><p><a href='ArticleController?id=<%= article.getCode() %>'><%= article.getName() %></a></p></div>
		 	 	<div class='author'><p>Author: Daniele De Falco</p></div>
		 	 	<div class='price'><p>EUR <%= article.getPrice() %></p></div>
		 	 	<div class='availability'><p><span><%= purchasing.getQuantity() %></span> chosen pieces</p></div>
		 	 	<button class="button_delete" onclick="removeArticleFromSession(<%= article.getCode() %>)">Remove item</button>
		 	 	<div class='separator'></div>
		 	 	</div>
		 	 	</th></tr>
		 	 
		 	<%
		 	 }
		 	 
		 	 %>
		 	 </table>
		 </div>
		 
		 <div class="cart_info">
		 	<div class="total"> Total purchases (<%= cart.size() %> articles): <br> <span class="t_money">EUR <%= cart.calculateTotal() %></span> </div>
		 		<table>
		 			<tr>
		 				<td>
		 					<label for="residence"><b>Select your residence</b></label>
		 				</td>
		 			</tr>
		 			<tr>
		 				<td>
		 					<select id="input_residence" name="residence">
   								<%
   								
   								User user = (User)_session.getAttribute("user");
		 						if(user == null)
		 						{
		 						%>
		 							<option value="1">No address available</option>
		 						<%
		 						}
		 						else
		 						{
		 							ArrayList<ResidenceDto> residences = user.getResidences();
		 							if(residences.size() <= 0)
		 							{
		 						%>
		 								<option value="1">No address available</option>
		 						<%
		 							}
		 							else
		 							{
		 								for(int i = 0; i < residences.size(); i++)
		 								{
		 									ResidenceDto residence = residences.get(i);
		 						%>
		 									<option value="<%= i + 2 %>">(<%= residence.getCity() %>) <%= residence.getAddress() %></option>
		 						<%
		 								}
		 							}
		 						}
   								
   								%>
   							</select>
		 				</td>
		 			</tr>
		 			<tr>
		 				<td>
		 					<button class="button_buy" onclick="buyAll()">Buy Now</button>
		 				</td>
		 			</tr>
		 		</table>
		 </div>
		 </div>
	</body>
</html>