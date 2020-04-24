package penta.database;
import penta.database.dto.*;
import penta.shop.*;

import java.util.ArrayList;
import java.sql.SQLException;

public class PentaCRUD {

	private static PentaCRUD instance;
	public static PentaCRUD getInstance()
	{
		if(instance == null)
			instance = new PentaCRUD();
		
		return instance;
	}
	
	private PentaCRUD() {}
	
	public int insertArticle(Article article) throws SQLException
	{
		if(article == null)
			return 0;
		
		Database db = Database.GetInstance();
		
		String query = "select * from article where article.Code = '" + article.getCode() + "';";
		ArrayList<ArticleDto> articles = db.Find(new ArticleDto(), query);
		
		int index = 0;
		if(articles.size() <= 0 && article.getCode() == 0)
		{
			ArticleDto dto = article.getBase();
			index = db.Insert(dto);
			
			return index;
		}
		
		return 0;
	}
	
	public boolean deleteArticle(Article article) throws SQLException
	{
		if(article == null)
			return false;
		
		Database db = Database.GetInstance();
		
		String query = "select * from article where article.Code = '" + article.getCode() + "';";
		ArrayList<ArticleDto> articles = db.Find(new ArticleDto(), query);
		
		String query2 = "select * from purchasing where purchasing.Article = '" + article.getCode() + "';";
		int count = db.Find(new PurchasingDto(), query2).size();
		
		if(articles.size() > 0 && count <= 0)
		{
			ArticleDto dto = article.getBase();
			db.Delete(dto);
			
			return true;
		}
		
		return false;
	}
	
	public boolean saveArticle(Article article) throws SQLException
	{
		if(article == null)
			return false;
		
		Database db = Database.GetInstance();
		
		String query = "select * from article where article.Code = '" + article.getCode() + "';";
		ArrayList<ArticleDto> articles = db.Find(new ArticleDto(), query);
		
		if(articles.size() > 0)
		{
			ArticleDto dto = article.getBase();
			db.Update(dto);
			
			return true;
		}
		
		return false;
	}
	
	public Article findArticle(int code) throws SQLException
	{
		Database db = Database.GetInstance();
		
		String query = "select * from article where article.Code = '" + code + "';";
		ArrayList<ArticleDto> articles = db.Find(new ArticleDto(), query);
		
		if(articles.size() > 0)
		{
			ArticleDto article = articles.get(0);
			Article myArticle = new Article(article);
			
			return myArticle;
		}
		
		return null;
	}
	
	public ArrayList<Article> findArticlesByShopWindow(int windowId) throws SQLException
	{
		Database db = Database.GetInstance();
		
		String query = "select * from article where article.ShopWindow = '" + windowId + "';";
		ArrayList<ArticleDto> articles = db.Find(new ArticleDto(), query);
		
		ArrayList<Article> _articles = new ArrayList<Article>();
		if(articles.size() > 0)
		{
			for(int i = 0; i < articles.size(); i++)
				_articles.add(new Article(articles.get(i)));
			
			return _articles;
		}
		
		return null;
	}
	
	public ArrayList<Article> findArticlesByCategory(int categoryId) throws SQLException
	{
		Database db = Database.GetInstance();
		
		String query = "select article.* from article where article.category = '" + categoryId + "';";
		ArrayList<ArticleDto> articles = db.Find(new ArticleDto(), query);
		
		ArrayList<Article> _articles = new ArrayList<Article>();
		if(articles.size() > 0)
		{
			for(int i = 0; i < articles.size(); i++)
				_articles.add(new Article(articles.get(i)));
			
			return _articles;
		}
		
		return null;
	}
	
	public ArrayList<Article> findArticlesByKeyword(String keyword) throws SQLException
	{
		Database db = Database.GetInstance();
		
		String query = "select article.* from article where article.name like '%" + keyword + "%';";
		ArrayList<ArticleDto> articles = db.Find(new ArticleDto(), query);
		
		ArrayList<Article> _articles = new ArrayList<Article>();
		if(articles.size() > 0)
		{
			for(int i = 0; i < articles.size(); i++)
				_articles.add(new Article(articles.get(i)));
			
			return _articles;
		}
		
		return null;
	}
	
	public ArrayList<Article> findArticles() throws SQLException
	{
		Database db = Database.GetInstance();
		
		String query = "select * from article";
		ArrayList<ArticleDto> articles = db.Find(new ArticleDto(), query);
		
		ArrayList<Article> _articles = new ArrayList<Article>();
		if(articles.size() > 0)
		{
			for(int i = 0; i < articles.size(); i++)
				_articles.add(new Article(articles.get(i)));
			
			return _articles;
		}
		
		return null;
	}

	public Article fillArticle(Article article) throws SQLException
	{
		if(article == null)
			return null;
		
		Database db = Database.GetInstance();
		
		String query1 = "select * from category where category.Code = '" + article.getCategory() + "';";
		ArrayList<CategoryDto> categories = db.Find(new CategoryDto(), query1);
		
		if(categories.size() > 0)
			article.setCategoryDto(categories.get(0));
		
		String query2 = "select * from shopwindow where shopwindow.Code = '" + article.getShopWindow() + "';";
		ArrayList<ShopWindowDto> windows = db.Find(new ShopWindowDto(), query2);
		
		if(windows.size() > 0)
			article.setShopWindowDto(windows.get(0));
		
		return article;
	}
	
	
	
	public int insertPurchasing(Purchasing purchasing) throws SQLException
	{
		if(purchasing == null)
			return 0;
		
		Database db = Database.GetInstance();
		
		String query = "select * from purchasing where purchasing.Code = '" + purchasing.getCode() + "';";
		ArrayList<PurchasingDto> purchasings = db.Find(new PurchasingDto(), query);
		
		int index = 0;
		if(purchasings.size() <= 0 && purchasing.getCode() == 0)
		{
			PurchasingDto dto = purchasing.getBase();
			index = db.Insert(dto);
			
			return index;
		}
		
		return 0;
	}
	
	public boolean deletePurchasing(Purchasing purchasing) throws SQLException
	{
		if(purchasing == null)
			return false;
		
		Database db = Database.GetInstance();
		
		String query = "select * from purchasing where purchasing.Code = '" + purchasing.getCode() + "';";
		ArrayList<PurchasingDto> purchasings = db.Find(new PurchasingDto(), query);
		
		if(purchasings.size() > 0)
		{
			PurchasingDto dto = purchasing.getBase();
			db.Delete(dto);
			
			return true;
		}
		
		return false;
	}
	
	public boolean savePurchasing(Purchasing purchasing) throws SQLException
	{
		if(purchasing == null)
			return false;
		
		Database db = Database.GetInstance();
		
		String query = "select * from purchasing where purchasing.Code = '" + purchasing.getCode() + "';";
		ArrayList<PurchasingDto> purchasings = db.Find(new PurchasingDto(), query);
		
		if(purchasings.size() > 0)
		{
			PurchasingDto dto = purchasing.getBase();
			db.Update(dto);
			
			return true;
		}
		
		return false;
	}
	
	public Purchasing findPurchasing(int code) throws SQLException
	{
		Database db = Database.GetInstance();
		
		String query = "select * from purchasing where purchasing.Code = '" + code + "';";
		ArrayList<PurchasingDto> purchasings = db.Find(new PurchasingDto(), query);
		
		if(purchasings.size() > 0)
		{
			PurchasingDto purchasing = purchasings.get(0);
			Purchasing myPurchase = new Purchasing(purchasing);
			
			return myPurchase;
		}
		
		return null;
	}
	
	public ArrayList<Purchasing> findPurchasingsByOrderId(int orderId) throws SQLException
	{
		Database db = Database.GetInstance();
		
		String query = "select * from purchasing where purchasing.Orders = '" + orderId + "';";
		ArrayList<PurchasingDto> purchasings = db.Find(new PurchasingDto(), query);
		
		ArrayList<Purchasing> _purchasings = new ArrayList<Purchasing>();
		if(purchasings.size() > 0)
		{
			for(int i = 0; i < purchasings.size(); i++)
			{
				PurchasingDto purchasing = purchasings.get(i);
				Purchasing myPurchase = new Purchasing(purchasing);
				
				_purchasings.add(myPurchase);
			}
			
			return _purchasings;
		}
		
		return null;
	}
	
	public ArrayList<Purchasing> findPurchasingByUser(String username) throws SQLException
	{
		Database db = Database.GetInstance();
		
		String query = "select * from purchasing where purchasing.User = '" + username + "';";
		ArrayList<PurchasingDto> purchasings = db.Find(new PurchasingDto(), query);
		
		ArrayList<Purchasing> _purchasings = new ArrayList<Purchasing>();
		if(purchasings.size() > 0)
		{
			for(int i = 0; i < purchasings.size(); i++)
			{
				PurchasingDto purchasing = purchasings.get(i);
				Purchasing myPurchase = new Purchasing(purchasing);
				
				_purchasings.add(myPurchase);
			}
			
			return _purchasings;
		}
		
		return null;
	}
	
	public ArrayList<Purchasing> findPurchasings() throws SQLException
	{
		Database db = Database.GetInstance();
		
		String query = "select * from purchasing";
		ArrayList<PurchasingDto> purchasings = db.Find(new PurchasingDto(), query);
		
		ArrayList<Purchasing> _purchasings = new ArrayList<Purchasing>();
		if(purchasings.size() > 0)
		{
			for(int i = 0; i < purchasings.size(); i++)
				_purchasings.add(new Purchasing(purchasings.get(i)));
			
			return _purchasings;
		}
		
		return null;
	}
	
	public Purchasing fillPurchase(Purchasing purchasing) throws SQLException
	{
		if(purchasing == null)
			return null;
		
		Database db = Database.GetInstance();
		
		Article article = this.findArticle(purchasing.getArticle());
		if(article != null)
			purchasing.setArticleDto(article);
		
		String query = "select * from orders where orders.Code = '" + purchasing.getOrders() + "';";
		ArrayList<OrdersDto> orders = db.Find(new OrdersDto(), query);
		
		if(orders.size() > 0)
			purchasing.setOrderDto(orders.get(0));
		
		User user = this.findUser(purchasing.getUser());
		if(user != null)
			purchasing.setUserDto(user);
		
		return purchasing;
	}
	
	
	
	public int insertUser(User user) throws SQLException
	{
		if(user == null)
			return 0;
		
		Database db = Database.GetInstance();
		
		String query = "select * from user where user.Username = '" + user.getUsername() + "';";
		ArrayList<UserDto> users = db.Find(new UserDto(), query);
		
		int index = 0;
		if(users.size() <= 0)
		{
			UserDto dto = user.getBase();
			index = db.Insert(dto);
			
			return index;
		}
		
		return 0;
	}
	
	public boolean deleteUser(User user) throws SQLException
	{
		if(user == null)
			return false;
		
		Database db = Database.GetInstance();
		
		String query = "select * from user where user.Username = '" + user.getUsername() + "';";
		ArrayList<UserDto> users = db.Find(new UserDto(), query);
		
		if(users.size() > 0)
		{
			UserDto dto = user.getBase();
			db.Delete(dto);
			
			return true;
		}
		
		return false;
	}
	
	public boolean saveUser(User user) throws SQLException
	{
		if(user == null)
		{
			return false;
		}
		
		Database db = Database.GetInstance();
		
		String query = "select * from user where user.Username = '" + user.getUsername() + "';";
		ArrayList<UserDto> users = db.Find(new UserDto(), query);
		
		System.out.println(users.size());
		
		if(users.size() > 0)
		{
			UserDto dto = user.getBase();
			db.Update(dto);
			
			return true;
		}
		
		return false;
	}
	
	public User findUser(String username) throws SQLException
	{
		Database db = Database.GetInstance();
		
		String query = "select * from user where user.Username = '" + username + "';";
		ArrayList<UserDto> users = db.Find(new UserDto(), query);
		
		if(users.size() > 0)
		{
			User user = new User(users.get(0));
			return user;			
		}
		
		return null;
	}
	
	public ArrayList<User> findUsers() throws SQLException
	{
		Database db = Database.GetInstance();
		
		String query = "select * from user";
		ArrayList<UserDto> users = db.Find(new UserDto(), query);
		
		ArrayList<User> _users = new ArrayList<User>();
		if(users.size() > 0)
		{
			for(int i = 0; i < users.size(); i++)
				_users.add(new User(users.get(i)));
			
			return _users;
		}
		
		return null;
	}
	
	public User fillUser(User user) throws SQLException
	{
		if(user == null)
			return null;
		
		Database db = Database.GetInstance();
		
		String query = "select * from residence where residence.User = '" + user.getUsername() + "';";
		ArrayList<ResidenceDto> residences = db.Find(new ResidenceDto(), query);
		
		if(residences != null)
		{
			if(residences.size() > 0)
			{
				for(int i = 0; i < residences.size(); i++)
					user.addResidence(residences.get(i));
			}
		}
		
		ArrayList<Purchasing> purchasings = this.findPurchasingByUser(user.getUsername());
		if(purchasings != null)
		{
			if(purchasings.size() > 0)
			{
				for(int i = 0; i < purchasings.size(); i++)
					user.addOrder(purchasings.get(i));
			}
		}
		
		user.setGrade(user.getGrade());
		
		return user;
	}
	
	
	
	
	/*									Simple functions								*/
	
	public int insertCategory(CategoryDto category) throws SQLException
	{
		if(category == null)
			return 0;
		
		Database db = Database.GetInstance();
		
		String query = "select * from category where Code = '" + category.getCode() + "';";
		ArrayList<CategoryDto> categories = db.Find(new CategoryDto(), query);
		
		int index = 0;
		if(categories.size() <= 0 && category.getCode() == 0)
		{
			index = db.Insert(category);
			return index;
		}
		
		return 0;
	}
	
	public ArrayList<CategoryDto> findCategories() throws SQLException
	{
		Database db = Database.GetInstance();
		
		String query = "select * from category;";
		ArrayList<CategoryDto> categories = db.Find(new CategoryDto(), query);
		
		if(categories.size() > 0)
			return categories;
		
		return null;
	}
	
	public int insertOrder(OrdersDto order) throws SQLException
	{
		if(order == null)
			return 0;
		
		Database db = Database.GetInstance();
		
		String query = "select * from orders where orders.Code = '" + order.getCode() + "';";
		ArrayList<OrdersDto> orders = db.Find(new OrdersDto(), query);
		
		int index = 0;
		if(orders.size() <= 0 && order.getCode() == 0)
		{
			index = db.Insert(order);
			return index;
		}
		
		return 0;
	}
	
	public int createOrder(double totalPrice) throws SQLException
	{
		Database db = Database.GetInstance();
		
		OrdersDto dto = new OrdersDto();
		dto.setTotal(totalPrice);
		
		return db.Insert(dto);
	}
	
	public OrdersDto findOrder(int code) throws SQLException
	{
		if(code == 0)
			return null;
		
		Database db = Database.GetInstance();
		
		String query = "select * from orders where orders.Code = '" + code + "';";
		ArrayList<OrdersDto> orders = db.Find(new OrdersDto(), query);
		
		if(orders.size() > 0)
		{
			OrdersDto _order = orders.get(0);
			return _order;
		}
		
		return null;
	}
	
	public int insertResidence(ResidenceDto residence) throws SQLException
	{
		if(residence == null)
			return 0;
		
		Database db = Database.GetInstance();
		
		String query = "select * from residence where residence.Code = '" + residence.getCode() + "';";
		ArrayList<ResidenceDto> residences = db.Find(new ResidenceDto(), query);
		
		int index = 0;
		if(residences.size() <= 0 && residence.getCode() == 0)
		{
			index = db.Insert(residence);
			return index;
		}
		
		return 0;
	}
	
	public int insertShopWindow(ShopWindowDto window) throws SQLException
	{
		if(window == null)
			return 0;
		
		Database db = Database.GetInstance();
		
		String query = "select * from window where window.Code = '" + window.getCode() + "';";
		ArrayList<ShopWindowDto> windows = db.Find(new ShopWindowDto(), query);
		
		int index = 0;
		if(windows.size() <= 0 && window.getCode() == 0)
		{
			index = db.Insert(window);
			return index;
		}
		
		return 0;
	}
	
	public boolean existUser(String username) throws SQLException
	{
		if(username == null)
			return false;
		
		Database db = Database.GetInstance();
		
		String query = "select * from user where user.Username = '" + username + "';";
		ArrayList<UserDto> users = db.Find(new UserDto(), query);
		
		return users.size() > 0;
	}
	
	public boolean existEmail(String email) throws SQLException
	{
		if(email == null)
			return false;
		
		Database db = Database.GetInstance();
		
		String query = "select * from user where user.Email = '" + email + "';";
		ArrayList<UserDto> users = db.Find(new UserDto(), query);
		
		return users.size() > 0;
	}
	
}
