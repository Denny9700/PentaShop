package penta.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import penta.shop.*;
import penta.objects.JsonObject;

@WebServlet("/UserController")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public UserController() {
        super();
    }

    @SuppressWarnings("unused")
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
				HttpSession session = request.getSession();
				if(session != null)
				{
					synchronized(session){
						User user = (User)session.getAttribute("user");
						if(user == null)
						{
							object.setResult("not_logged_in");
							object.setMessage("user not logged in");
							
							_response = gson.toJson(object);
						}
						else
						{
							userInfo info = new userInfo(user.getUsername());
							
							object.setResult("ok");
							object.setMessage("user logged in");
							object.setContent(info);
							
							_response = gson.toJson(object);
						}
					}
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

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

class userInfo
{
	private String username;
	
	public userInfo() {}
	public userInfo(String username)
	{
		this.username = username;
	}
	
	public String getUsername	(				) { return username; 			}
	public void setUsername		(String username) { this.username = username; 	}

}
