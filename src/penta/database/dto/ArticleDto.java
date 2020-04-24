package penta.database.dto;
import penta.database.core.EntityType;
import penta.database.core.TableName;
import penta.database.core.enums.PKType;
import penta.database.core.enums.Type;;

public class ArticleDto {

	@EntityType(type = Type.PrimaryKey, pkType = PKType.Auto_Increment)
	protected int Code;
	
	protected String Name;
	protected String Description;
	protected double Price;
	protected int Availability;
	protected String Photo;
	protected int Category;
	protected int ShopWindow;
	
	@TableName(name = "article")
	public ArticleDto() {}

	public int getCode() {
		return Code;
	}

	public void setCode(int code) {
		Code = code;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public double getPrice() {
		return Price;
	}

	public void setPrice(double price) {
		Price = price;
	}

	public int getAvailability() {
		return Availability;
	}

	public void setAvailability(int availability) {
		Availability = availability;
	}
	
	public String getPhoto() {
		return Photo;
	}

	public void setPhoto(String photo) {
		Photo = photo;
	}

	public int getCategory() {
		return Category;
	}

	public void setCategory(int category) {
		Category = category;
	}
	
	public int getShopWindow() {
		return ShopWindow;
	}
	
	public void setShopWindow(int shopwindow) {
		ShopWindow = shopwindow;
	}
	
}
