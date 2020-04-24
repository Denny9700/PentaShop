package penta.shop;
import penta.database.PentaCRUD;

import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.sql.SQLException;

import penta.cryptography.MD5Hash;

public class UserManager {

	private static UserManager instance;
	public static UserManager getInstance()
	{
		if(instance == null)
			instance = new UserManager();
		
		return instance;
	}
	
	private UserManager() {}
	
	public User register(String username, String name, String surname, Date birthday, String email,
			String password, int grade) throws NoSuchAlgorithmException, SQLException
	{
		String hashPswd = MD5Hash.Hash(password);
		User user = new User(username, name, surname, birthday, email, hashPswd, 0);
		
		PentaCRUD crud = PentaCRUD.getInstance();
		int result = crud.insertUser(user);
		
		if(result > 0)		
			return user;
		
		return null;
	}
	
	public User changePassword(User user, String oldPassword, String newPassword)
			throws SQLException, NoSuchAlgorithmException
	{
		PentaCRUD crud = PentaCRUD.getInstance();
		User _user = crud.findUser(user.getUsername());
		
		if(_user == null)
			return user;
		
		String _oldPW = user.getPassword();
		String _newPW = MD5Hash.Hash(newPassword);
		
		String cryptedOld = MD5Hash.Hash(oldPassword);
		
		if(!cryptedOld.equals(_oldPW))
			return user;
		
		if(_oldPW.equals(_newPW))
			return user;
		
		_user.setPassword(_newPW);
		crud.saveUser(_user);
		
		return _user;
	}
	
	public boolean validatePassword(String password, String userPassword) throws NoSuchAlgorithmException
	{
		String cryptedPW = MD5Hash.Hash(userPassword);
		if(password.equals(cryptedPW))
			return true;
		
		return false;
	}
	
}
