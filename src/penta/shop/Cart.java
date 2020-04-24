package penta.shop;
import java.util.ArrayList;
import java.util.Calendar;
import java.sql.Date;
import java.sql.SQLException;

import penta.database.PentaCRUD;
import penta.database.dto.*;

public class Cart {
	
	private ArrayList<Purchasing> 	purchasings;
	
	public Cart() { this.purchasings = new ArrayList<Purchasing>(); }
	public Cart(ArrayList<Purchasing> purchasings)
	{
		this.purchasings 	= purchasings;
	}
	
	public void add(Article article)
	{
		add(article, 1);
	}
	
	public void add(Article article, int quantity)
	{
		boolean exist = false;
		for(int i = 0; i < this.purchasings.size(); i++)
		{
			Purchasing purchasing = this.purchasings.get(i);
			Article _article = purchasing.getArticleDto();
			if(_article.getCode() == article.getCode())
			{
				purchasing.setQuantity(purchasing.getQuantity() + quantity);
				exist = true;
			}
		}
		
		if(!exist)
		{
			Purchasing purchasing = new Purchasing();
			purchasing.setArticle(article.getCode());
			purchasing.setArticleDto(article);
			purchasing.setPrice(article.getPrice());
			purchasing.setPurchaseDate(new Date(Calendar.getInstance().getTimeInMillis()));
			purchasing.setQuantity(quantity);
			
			this.purchasings.add(purchasing);
		}
	}
	
	public void delete(Article article)
	{
		for(int i = 0; i < this.purchasings.size(); i++)
		{
			Purchasing purchasing = this.purchasings.get(i);
			Article _article = purchasing.getArticleDto();
			if(_article.getCode() == article.getCode())
				this.purchasings.remove(purchasing);
		}
	}
	
	public void delete(Article article, int quantity)
	{
		for(int i = 0; i < this.purchasings.size(); i++)
		{
			Purchasing purchasing = this.purchasings.get(i);
			if(purchasing.getArticleDto().getCode() == article.getCode())
			{
				if(purchasing.getQuantity() <= quantity)
				{
					delete(article);
					return;
				}
				
				purchasing.setQuantity(purchasing.getQuantity() - quantity);
			}
		}
	}
	
	public void clear()
	{
		this.purchasings.clear();
	}
	
	public int size()
	{
		return this.purchasings.size();
	}
	
	public double calculateTotal()
	{
		double total = 0;
		for(int i = 0; i < this.purchasings.size(); i++)
		{
			Purchasing purchasing = this.purchasings.get(i);
			total += (purchasing.getPrice() * purchasing.getQuantity());
		}
		
		total = Math.round(total* 100) / 100.0;
		return total;
	}
	
	public Article getArticle(int id)
	{
		for(int i = 0; i < purchasings.size(); i++)
		{
			Article article = purchasings.get(i).getArticleDto();
			if(article.getCode() == id)
				return article;
			
		}
		
		return null;
	}
	
	public Purchasing getPurchasingByArticle(int id)
	{
		for(int i = 0; i < purchasings.size(); i++)
		{
			Article article = purchasings.get(i).getArticleDto();
			if(article.getCode() == id)
				return purchasings.get(i);
			
		}
		
		return null;
	}
	
	public Purchasing getPurchasingByIndex(int index)
	{
		return purchasings.get(index);
	}
	
	public void finalize(User user) throws SQLException
	{
		PentaCRUD crud = PentaCRUD.getInstance();
		
		int orderNumber = crud.createOrder(calculateTotal());
		OrdersDto orderDto = crud.findOrder(orderNumber);
		
		for(int i = 0; i < this.purchasings.size(); i++)
		{
			Purchasing purchasing = this.purchasings.get(i);
			purchasing.setOrders(orderNumber);
			purchasing.setOrderDto(orderDto);
			purchasing.setUserDto(user);
			purchasing.setUser(user.getUsername());
			
			crud.insertPurchasing(purchasing);
		}
	}
}
