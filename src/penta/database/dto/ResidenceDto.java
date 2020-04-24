package penta.database.dto;
import penta.database.core.EntityType;
import penta.database.core.TableName;
import penta.database.core.enums.PKType;
import penta.database.core.enums.Type;

public class ResidenceDto {

	@EntityType(type = Type.PrimaryKey, pkType = PKType.Auto_Increment)
	protected int Code;
	
	protected String Region;
	protected String City;
	protected String Address;
	protected int CAP;
	protected String User;
	
	@TableName(name = "residence")
	public ResidenceDto() {}

	public int getCode() {
		return Code;
	}

	public void setCode(int code) {
		Code = code;
	}

	public String getRegion() {
		return Region;
	}

	public void setRegion(String region) {
		Region = region;
	}

	public String getCity() {
		return City;
	}

	public void setCity(String city) {
		City = city;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public int getCAP() {
		return CAP;
	}

	public void setCAP(int cAP) {
		CAP = cAP;
	}

	public String getUser() {
		return User;
	}

	public void setUser(String user) {
		User = user;
	}
}
