package penta.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import penta.database.PentaCRUD;
import penta.objects.JsonObject;
import penta.objects.LoginStructure;
import penta.shop.*;

import com.google.gson.*;

@WebServlet("/LoginController")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public LoginController() {
        super();
    }

	@SuppressWarnings("unused")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		JsonObject object = new JsonObject();
		String _response = "";
		
		HttpSession session = request.getSession();
		
		Gson gson = new Gson();
		UserManager manager = UserManager.getInstance();
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
				LoginStructure logStructure = gson.fromJson(gson.toJson(jsonObject.getContent()),
													   LoginStructure.class);
				
				PentaCRUD crud = PentaCRUD.getInstance();
				try
				{
					User user = crud.findUser(logStructure.getUsername());
					if(user == null)
					{
						object.setResult("failed");
						object.setMessage("user not found");
						
						_response = gson.toJson(object);
					}
					else
					{
						User _user = null;
						synchronized(session){
							_user = (User)session.getAttribute("user");
							
						}
						
						if(_user != null)
						{
							object.setResult("failed");
							object.setMessage("user already login (illegal) - " + _user.getUsername());
							
							_response = gson.toJson(object);
						}
						else if(!manager.validatePassword(user.getPassword(), logStructure.getPassword()))
						{
							object.setResult("failed");
							object.setMessage("Invalid password for this user");
							
							_response = gson.toJson(object);
						}
						else
						{
							user = crud.fillUser(user);
							
							synchronized(session){
								if(session != null)
									session.setAttribute("user", user);
							}
							
							object.setResult("ok");
							object.setMessage("user login success");
							
							_response = gson.toJson(object);
						}
					}
				} catch (SQLException | NoSuchAlgorithmException e) {}
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

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
