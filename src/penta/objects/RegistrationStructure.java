package penta.objects;

public class RegistrationStructure {

	private String 	username;
	private String 	name;
	private String 	surname;
	private String 	email;
	private String 	password;
	private String 	repeatPassword;
	private String 	birthday;
	
	public RegistrationStructure() {}
	public RegistrationStructure(String username, String name, String surname, String email,
								 String password, String repeatPassword, String birthday)
	{
		this.username 		= username;
		this.name 			= name;
		this.surname 		= surname;
		this.email 			= email;
		this.password 		= password;
		this.repeatPassword = repeatPassword;
		this.birthday 		= birthday;
	}
	
	public String getUsername		(						) { return username; 						}
	public void setUsername			(String username		) { this.username = username; 				}
	
	public String getName			(						) { return name; 							}
	public void setName				(String name			) { this.name = name; 						}
	
	public String getSurname		(						) { return surname; 						}
	public void setSurname			(String surname			) { this.surname = surname; 				}
	
	public String getEmail			(			 			) { return email; 							}
	public void setEmail			(String email			) { this.email = email; 					}
	
	public String getPassword		(						) { return password; 						}
	public void setPassword			(String password		) { this.password = password; 				}
	
	public String getRepeatPassword	(						) { return repeatPassword; 					}
	public void setRepeatPassword	(String repeatPassword	) { this.repeatPassword = repeatPassword; 	}
	
	public String getBirthday		(						) { return birthday; 						}
	public void setBirthday			(String birthday		) { this.birthday = birthday; 				}
}
