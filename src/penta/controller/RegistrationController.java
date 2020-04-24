package penta.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Date;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import penta.database.PentaCRUD;
import penta.objects.JsonObject;
import penta.objects.RegistrationStructure;
import penta.shop.*;

import com.google.gson.*;

@WebServlet("/RegistrationController")
public class RegistrationController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public RegistrationController() {
        super();
    }

	@SuppressWarnings({ "unused", "deprecation" })
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		JsonObject object = new JsonObject();
		String _response = "";
		
		Gson gson = new Gson();
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
		String json = "";
		
		if(br != null)
		{
			json = br.readLine();
			JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
			
			if(jsonObject == null)
			{
				object.setResult("failed");
				object.setMessage("object is null");
				
				_response = gson.toJson(object);
			}
			else if(jsonObject.getResult().equals("ok"))
			{
				RegistrationStructure regStruct = gson.fromJson(gson.toJson(jsonObject.getContent()),
													   			RegistrationStructure.class);
				
				PentaCRUD crud = PentaCRUD.getInstance();
				UserManager manager = UserManager.getInstance();
				try
				{
					String username = regStruct.getUsername();
					String name 	= regStruct.getName();
					String surname 	= regStruct.getSurname();
					Date   birthday = Date.valueOf(regStruct.getBirthday());
					String email 	= regStruct.getEmail();
					String password = regStruct.getPassword();
					
					java.util.Date min = new java.util.Date(00, 01, 01);
					java.util.Date max = new java.util.Date();
					
					java.util.Date userDate = new java.util.Date(birthday.getTime());
					
					if(!checkUsername(username))
					{
						object.setResult("failed");
						object.setMessage("Username must contain a minimum of 4 and a maximum of 10 characters");
						_response = gson.toJson(object);
					}
					else if(!checkMail(email))
					{
						object.setResult("failed");
						object.setMessage("Wrong email format");
						_response = gson.toJson(object);
					}
					else if(!checkPassword(password))
					{
						object.setResult("failed");
						object.setMessage("The password must contain at least one digit and must be at least 4 and maximum 10 characters");
						_response = gson.toJson(object);
					}
					else if(userDate.getYear() < min.getYear() || userDate.getYear() > max.getYear())
					{
						object.setResult("failed");
						object.setMessage("Invalid date");
						_response = gson.toJson(object);
					}
					else if(crud.existUser(username) || crud.existEmail(email))
					{
						object.setResult("failed");
						object.setMessage("Username or Email already exist");
						_response = gson.toJson(object);
					}
					else
					{
						manager.register(username, name, surname, birthday, email, password, 0);
						
						object.setResult("ok");
						object.setMessage("registration ok");
						_response = gson.toJson(object);
					}
					
				} catch(Exception e){
					object.setResult("failed");
					object.setMessage("unable to register");
					_response = gson.toJson(object);
				}
			}
			else
			{
				object.setResult("failed");
				object.setMessage("generic error");
				
				_response = gson.toJson(object);
			}
		}
		else
		{
			object.setResult("failed");
			object.setMessage("unable to read json request");
			
			_response = gson.toJson(object);
		}
		
		response.getWriter().println(_response);
	}
	
	private boolean checkUsername(String username)
	{
		return username.length() > 4 && username.length() <= 10;
	}
	
	private boolean checkMail(String email)
	{
		String regex = "^([a-zA-Z0-9_.+-])+\\@(([a-zA-Z0-9-])+\\.)+([a-zA-Z0-9]{2,4})+$";
		return Pattern.matches(regex, email);
	}
	
	private boolean checkPassword(String password)
	{
		String regex = "^.*(?=.{4,10})(?=.*\\d)(?=.*[a-zA-Z]).*$";
		return Pattern.matches(regex, password);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
