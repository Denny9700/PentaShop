package penta.objects;

public class ArticleStructure {

	private String name;
	private String description;
	private String photo;
	private double price;
	private int availability;
	private int category;
	private int window;
	
	public ArticleStructure() {}
	public ArticleStructure(String name, String description, String photo, double price, int availability,
							int category, int window)
	{
		this.name 			= name;
		this.description 	= description;
		this.photo			= photo;
		this.price 			= price;
		this.availability 	= availability;
		this.category 		= category;
		this.window 		= window;
	}
	
	public String getName		(					) {return name;						}
	public void setName			(String name		) {this.name = name;				}
	
	public String getDescription(					) {return description;				}
	public void setDescription	(String description	) {this.description = description;	}
	
	public String getPhoto		(					) {return photo;					}
	public void setPhoto		(String photo		) {this.photo = photo;				}
	
	public double getPrice		(					) {return price;					}
	public void setPrice		(double price		) {this.price = price;				}
	
	public int getAvailability	(					) {return availability;				}
	public void setAvailability	(int availability	) {this.availability = availability;}
	
	public int getCategory		(					) {return category;					}
	public void setCategory		(int category		) {this.category = category;		}
	
	public int getWindow		(					) {return window;					}
	public void setWindow		(int window			) {this.window = window;			}
}
