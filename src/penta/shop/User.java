package penta.shop;
import penta.database.dto.UserDto;
import penta.database.dto.ResidenceDto;
import penta.shop.enums.Grade;

import java.util.ArrayList;
import java.sql.Date;

public class User extends UserDto {
	
	private ArrayList<ResidenceDto> residences;
	private ArrayList<Purchasing> 	purchasing;
	private Grade 					userGrade;
	
	
	public User() {}
	public User(String username, String name, String surname, Date birthday, String email,
				String password, int grade)
	{
		this.Username 	= username;
		this.Name 		= name;
		this.Surname 	= surname;
		this.Birthday 	= birthday;
		this.Email 		= email;
		this.Password 	= password;
		this.Grade 		= grade;
		
		this.residences = new ArrayList<ResidenceDto>	();
		this.purchasing = new ArrayList<Purchasing>		();
		this.userGrade  = penta.shop.enums.Grade.User;
	}
	
	public User(UserDto dto)
	{
		this.Username 	= dto.getUsername	();
		this.Name 		= dto.getName		();
		this.Surname 	= dto.getSurname	();
		this.Birthday 	= dto.getBirthday	();
		this.Email 		= dto.getEmail		();
		this.Password 	= dto.getPassword	();
		this.Grade 		= dto.getGrade		();
		
		this.residences = new ArrayList<ResidenceDto>	();
		this.purchasing = new ArrayList<Purchasing>		();
		this.userGrade  = penta.shop.enums.Grade.User;
	}
	
	public User(UserDto user, ArrayList<ResidenceDto> residences, ArrayList<Purchasing> purchasing, Grade grade)
	{
		this.Username 	= user.getUsername	();
		this.Name 		= user.getName		();
		this.Surname 	= user.getSurname	();
		this.Birthday 	= user.getBirthday	();
		this.Email 		= user.getEmail		();
		this.Password 	= user.getPassword	();
		this.Grade 		= user.getGrade		();
		
		this.residences = residences;
		this.purchasing = purchasing;
		this.userGrade  = grade;
	}
	
	public UserDto getBase()
	{
		UserDto dto = new UserDto();
		dto.setUsername	(this.Username	);
		dto.setName		(this.Name		);
		dto.setSurname	(this.Surname	);
		dto.setBirthday	(this.Birthday	);
		dto.setEmail	(this.Email		);
		dto.setPassword	(this.Password	);
		dto.setGrade	(this.Grade		);
		
		return dto;
	}
	
	public ArrayList<ResidenceDto> 	getResidences	() { return residences; }
	public ArrayList<Purchasing> 	getPurchasing	() { return purchasing; }
	public Grade 					getUserGrade	() { return userGrade;  }
	
	public void addResidence(ResidenceDto residence)
	{
		boolean contains = false;
		for(int i = 0; i < this.residences.size(); i++)
			if(this.residences.get(i).equals(residence))
				contains = true;
		
		if(!contains)
			this.residences.add(residence);
	}
	
	public void removeResidence(ResidenceDto residence)
	{
		this.residences.remove(residence);
	}
	
	public ResidenceDto getResidenceByCityName(String name)
	{
		for(int i = 0; i < this.residences.size(); i++)
			if(this.residences.get(i).getCity().equals(name))
				return this.residences.get(i);
		
		return null;
	}
	
	public ResidenceDto getResidenceByIndex(int index)
	{
		return this.residences.get(index);
	}
	
	public void addOrder(Purchasing purchasing)
	{
		boolean contains = false;
		for(int i = 0; i < this.purchasing.size(); i++)
			if(this.purchasing.get(i).equals(purchasing))
				contains = true;
		
		if(!contains)
			this.purchasing.add(purchasing);
	}
	
	public void removePurchase(Purchasing purchasing)
	{
		this.purchasing.remove(purchasing);
	}
	
	public Purchasing getPurchaseByIndex(int index)
	{
		return this.purchasing.get(index);
	}
	
	public void setGrade(int gradeId)
	{
		switch(gradeId)
		{
		case 0:
			this.userGrade = penta.shop.enums.Grade.User;
			break;
		case 1:
			this.userGrade = penta.shop.enums.Grade.Developer;
			break;
		case 2:
			this.userGrade = penta.shop.enums.Grade.Administrator;
			break;
		}
	}
	
	public boolean equals(Object obj)
	{
		if(obj instanceof User)
		{
			User user = (User)obj;
			if(this.Username.equals		(user.Username	) &&
			   this.Name.equals			(user.Name		) &&
			   this.Surname.equals		(user.Surname	) &&
			   this.Birthday.equals		(user.Birthday	) &&
			   this.Email.equals		(user.Email		) &&
			   this.Password.equals		(user.Password	) &&
			   this.Grade			==   user.Grade		)
				return true;
		}
		
		return false;
	}
}
