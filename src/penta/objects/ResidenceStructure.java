package penta.objects;

public class ResidenceStructure {

	private String region;
	private String city;
	private String address;
	private int cap;
	private String user;
	
	public ResidenceStructure() {}
	public ResidenceStructure(String region, String city, String address, int cap, String user)
	{
		this.region = region;
		this.city = city;
		this.address = address;
		this.cap = cap;
		this.user = user;
	}
	
	public String getRegion	(				) { return region; 			}
	public void setRegion	(String region	) { this.region = region; 	}
	
	public String getCity	(				) { return city; 			}
	public void setCity		(String city	) { this.city = city; 		}
	
	public String getAddress(				) { return address; 		}
	public void setAddress	(String address	) { this.address = address; }
	
	public int getCap		(				) { return cap; 			}
	public void setCap		(int cap		) { this.cap = cap; 		}
	
	public String getUser	(				) { return user; 			}
	public void setUser		(String user	) { this.user = user; 		}
	
}
