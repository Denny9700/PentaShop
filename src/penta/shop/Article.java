package penta.shop;
import penta.database.dto.ArticleDto;
import penta.database.dto.CategoryDto;
import penta.database.dto.ShopWindowDto;

public class Article extends ArticleDto {
	
	private CategoryDto categoryDto;
	private ShopWindowDto shopWindowDto;

	public Article() {}
	public Article(int code, String name, String description, double price, int availability,
					String photo, int category, int shopWindow)
	{
		this.Code 			= code;
		this.Name 			= name;
		this.Description 	= description;
		this.Price 			= price;
		this.Availability 	= availability;
		this.Photo 			= photo;
		this.Category 		= category;
		this.ShopWindow 	= shopWindow;
		
		this.categoryDto 	= new CategoryDto();
		this.shopWindowDto 	= new ShopWindowDto();
	}
	
	public Article(ArticleDto dto)
	{
		this.Code 			= dto.getCode			();
		this.Name 			= dto.getName			();
		this.Description 	= dto.getDescription	();
		this.Price 			= dto.getPrice			();
		this.Availability 	= dto.getAvailability	();
		this.Photo 			= dto.getPhoto			();
		this.Category 		= dto.getCategory		();
		this.ShopWindow 	= dto.getShopWindow		();
		
		this.categoryDto 	= new CategoryDto();
		this.shopWindowDto 	= new ShopWindowDto();
	}
	
	public Article (ArticleDto article, CategoryDto category, ShopWindowDto window)
	{
		this.Code 			= article.getCode			();
		this.Name 			= article.getName			();
		this.Description 	= article.getDescription	();
		this.Price 			= article.getPrice			();
		this.Availability 	= article.getAvailability	();
		this.Photo 			= article.getPhoto			();
		this.Category 		= article.getCategory		();
		this.ShopWindow 	= article.getShopWindow		();
		
		this.categoryDto 	= category;
		this.shopWindowDto 	= window;
	}
	
	public CategoryDto 		getCategoryDto	() { return categoryDto;   }
	public ShopWindowDto 	getShopWindowDto() { return shopWindowDto; }
	
	public void setCategoryDto	(CategoryDto category) { this.categoryDto = category; }
	public void setShopWindowDto(ShopWindowDto window) { this.shopWindowDto = window; }
	
	public boolean isAvailable()
	{
		return this.Availability > 0;
	}
	
	public String toString()
	{
		String str = "";
		str += "Code: " 			+ this.Code 		+ "\n";
		str += "Name: " 			+ this.Name 		+ "\n";
		str += "Description: " 		+ this.Description 	+ "\n";
		str += "Price: " 			+ this.Price 		+ "\n";
		str += "Availability: " 	+ this.Availability + "\n";
		str += "Photo: " 			+ this.Photo 		+ "\n";
		str += "Category"			+ this.Category		+ "\n";
		str += "ShopWindow"			+ this.ShopWindow	+ "\n";
		
		return str;
	}
	
	public ArticleDto getBase()
	{
		ArticleDto dto = new ArticleDto();
		dto.setCode			(this.Code			);
		dto.setName			(this.Name			);
		dto.setDescription	(this.Description	);
		dto.setPrice		(this.Price			);
		dto.setAvailability	(this.Availability	);
		dto.setPhoto		(this.Photo			);
		dto.setCategory		(this.Category		);
		dto.setShopWindow	(this.ShopWindow	);
		
		return dto;
	}
	
	public boolean equals(Object obj)
	{
		if(obj instanceof Article)
		{
			Article article = (Article)obj;
			if(this.Code 				== 	 article.Code 				&&
			   this.Name.equals				(article.Name			) 	&&
			   this.Description.equals		(article.Description	) 	&&
			   this.Price 				== 	 article.Price 				&&
			   this.Availability 		== 	 article.Availability 		&&
			   this.Photo.equals			(article.Photo			) 	&&
			   this.Category			==	 article.Category 			&&
			   this.ShopWindow			==	 article.ShopWindow		)
				return true;
		}
		
		return false;
	}
}
	
