<header>
	<div class="l_header">
		<a href="index.jsp" class="l_desktop">PentaShop</a>
		<a href="index.jsp" class="l_mobile">PS</a>
	</div>

	<div class="r_header">
		<div class="user_head">
			<img src="img/user.svg">
			<div id="user_no_reg">
				<ul>
					<li><a href="login.jsp">Sign in</a> or</li>
					<li><a href="registration.jsp">Register</a></li>
				</ul>
			</div>
			<div id="user_login">
				<ul>
					<li>Welcome,</li>
					<li>
						<span  id="user_name" onclick="showMenu()" class="dropbtn">Username</span>
							<div id="myDropdown" class="dropdown-content">
    							<span class="dropBtn" onclick="insertArticle()">Insert product</span>
    							<span class="dropBtn" onclick="insertResidence()">Insert address</span>
    							<span class="dropBtn" onclick="orders()">Show orders</span>
    							<span class="dropBtn" onclick="logout()">Log Out</span>
  							</div>
					</li>
				</ul>
			</div>
		</div>
		<div class="cart_head">
			<a href="cart.jsp" href="#"><img id="cart_img" src="img/cart.svg" alt="Cart"></a>
			<div id="cart_quantity"><h6>0</h6></div>
		</div>
	</div>

	<div class="c_header">
		<form id="search_form" method="post" action="">
			<input id="search_box" type="text" name="search" autocomplete="off" placeholder="Search a product">
		</form>
	</div>
	
	<nav class="product_nav">
	<ul>
		<li><span class="NavButton" onclick="SelectTab(1)" id="nav_active" >Flash Deals</span></li>
		<li><span class="NavButton" onclick="SelectTab(2)" >Components</span></li>
		<li><span class="NavButton" onclick="SelectTab(3)" >Pre-Assembled</span></li>
		<li><span class="NavButton" onclick="SelectTab(4)" >Search</span></li>
	</ul>
	</nav>
	
	<div class="category_menu">
   		<ul>
   			<!-- Auto populate -->
   		</ul>
   	</div>
</header>