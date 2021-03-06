package penta.database.dto;
import penta.database.core.EntityType;
import penta.database.core.TableName;
import penta.database.core.enums.PKType;
import penta.database.core.enums.Type;

public class ShopWindowDto {
	
	@EntityType(type = Type.PrimaryKey, pkType = PKType.Auto_Increment)
	protected int Code;
	protected String Name;
	
	@TableName(name = "shopwindow")
	public ShopWindowDto() {}

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
}
