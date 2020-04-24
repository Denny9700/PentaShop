package penta.database.dto;
import penta.database.core.EntityType;
import penta.database.core.TableName;
import penta.database.core.enums.PKType;
import penta.database.core.enums.Type;
import java.sql.Date;

public class UserDto {

	@EntityType(type = Type.PrimaryKey, pkType = PKType.None)
	protected String Username;
	protected String Name;
	protected String Surname;
	protected Date Birthday;
	protected String Email;
	protected String Password;
	protected int Grade;
	
	@TableName(name = "user")
	public UserDto() {}

	public String getUsername() {
		return Username;
	}

	public void setUsername(String username) {
		Username = username;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getSurname() {
		return Surname;
	}

	public void setSurname(String surname) {
		Surname = surname;
	}

	public Date getBirthday() {
		return Birthday;
	}

	public void setBirthday(Date birthday) {
		Birthday = birthday;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	public int getGrade() {
		return Grade;
	}

	public void setGrade(int grade) {
		Grade = grade;
	}
}
