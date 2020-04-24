package penta.database.dto;
import java.sql.Date;

import penta.database.core.EntityType;
import penta.database.core.TableName;
import penta.database.core.enums.PKType;
import penta.database.core.enums.Type;

public class PurchasingDto {

	@EntityType(type = Type.PrimaryKey, pkType = PKType.Auto_Increment)
	protected int Code;
	
	protected double Price;
	protected Date PurchaseDate;
	protected int Quantity;
	protected int Article;
	protected int Orders;
	protected String User;
	
	@TableName(name = "purchasing")
	public PurchasingDto() {}

	public int getCode() {
		return Code;
	}

	public void setCode(int code) {
		Code = code;
	}

	public double getPrice() {
		return Price;
	}

	public void setPrice(double price) {
		Price = price;
	}

	public Date getPurchaseDate() {
		return PurchaseDate;
	}

	public void setPurchaseDate(Date purchaseDate) {
		PurchaseDate = purchaseDate;
	}

	public int getQuantity() {
		return Quantity;
	}

	public void setQuantity(int quantity) {
		Quantity = quantity;
	}

	public int getArticle() {
		return Article;
	}

	public void setArticle(int article) {
		Article = article;
	}

	public int getOrders() {
		return Orders;
	}

	public void setOrders(int orders) {
		Orders = orders;
	}

	public String getUser() {
		return User;
	}

	public void setUser(String user) {
		User = user;
	}
}
