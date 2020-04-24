package penta.shop;
import penta.database.dto.PurchasingDto;
import penta.database.dto.OrdersDto;

import java.sql.Date;

public class Purchasing extends PurchasingDto {
	
	private Article 	articleDto;
	private OrdersDto 	orderDto;
	private User 		userDto;
	
	public Purchasing() {}
	public Purchasing(int code, double price, Date purchaseDate, int quantity, int article,
					  int order, String user)
	{
		this.Code 			= code;
		this.Price 			= price;
		this.PurchaseDate 	= purchaseDate;
		this.Quantity 		= quantity;
		this.Article 		= article;
		this.Orders			= order;
		this.User 			= user;
		
		this.articleDto     = new Article	();
		this.orderDto		= new OrdersDto	();
		this.userDto		= new User		();
	}
	
	public Purchasing(PurchasingDto dto)
	{
		this.Code 			= dto.getCode			();
		this.Price 			= dto.getPrice			();
		this.PurchaseDate 	= dto.getPurchaseDate	();
		this.Quantity 		= dto.getQuantity		();
		this.Article 		= dto.getArticle		();
		this.Orders			= dto.getOrders			();
		this.User 			= dto.getUser			();
		
		this.articleDto     = new Article	();
		this.orderDto		= new OrdersDto	();
		this.userDto		= new User		();
	}
	
	public Purchasing(PurchasingDto purchasing, Article article, OrdersDto order, User user)
	{
		this.Code 			= purchasing.getCode			();
		this.Price 			= purchasing.getPrice			();
		this.PurchaseDate 	= purchasing.getPurchaseDate	();
		this.Quantity 		= purchasing.getQuantity		();
		this.Article 		= purchasing.getArticle			();
		this.Orders			= purchasing.getOrders			();
		this.User 			= purchasing.getUser			();
		
		this.articleDto     = article;
		this.orderDto       = order;
		this.userDto		= user;
	}
	
	public Article   	getArticleDto 	() { return this.articleDto; }				
	public OrdersDto 	getOrderDto		() { return this.orderDto;	 }
	public User 		getUserDto		() { return this.userDto;	 }
	
	public void setArticleDto	(Article article	) { this.articleDto = article; 	}
	public void setOrderDto		(OrdersDto orders	) { this.orderDto = orders; 	}
	public void setUserDto		(User user			) { this.userDto = user; 		}
	
	public PurchasingDto getBase()
	{
		PurchasingDto dto = new PurchasingDto();
		dto.setCode			(this.Code			);
		dto.setPrice		(this.Price			);
		dto.setPurchaseDate	(this.PurchaseDate	);
		dto.setQuantity		(this.Quantity		);
		dto.setArticle		(this.Article		);
		dto.setOrders		(this.Orders		);
		dto.setUser			(this.User			);
		
		return dto;
	}
	
	public String toString()
	{
		String str = "";
		str += "Code: " 		+ this.Code 					+ "\n";
		str += "Price: " 		+ this.Price 					+ "\n";
		str += "PurchaseDate: " + this.PurchaseDate.toString() 	+ "\n";
		str += "Quantity: " 	+ this.Quantity 				+ "\n";
		str += "Article: " 		+ this.Article			 		+ "\n";
		str += "Order: " 		+ this.Orders			 		+ "\n";
		str += "User: " 		+ this.User.toString() 			+ "\n";
		
		return str;
	}
	
	public boolean equals(Object obj)
	{
		if(obj instanceof Purchasing)
		{
			Purchasing purchasing = (Purchasing)obj;
			if(this.Code 				== purchasing.Code				&&
			   this.Price 				== purchasing.Price 			&&
			   this.PurchaseDate.equals	  (purchasing.PurchaseDate	) 	&&
			   this.Quantity 			== purchasing.Quantity			&&
			   this.Article				== purchasing.Article		 	&&
			   this.Orders				== purchasing.Orders		 	&&
			   this.User				== purchasing.User			)
				return true;
		}
		
		return false;
	}
}
