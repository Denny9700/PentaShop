package penta.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import penta.database.PentaCRUD;
import penta.objects.JsonObject;
import penta.shop.*;

@WebServlet("/BuyController")
public class BuyController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public BuyController() {
        super();
    }

	@SuppressWarnings("unused")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		JsonObject object = new JsonObject();
		String _response = "";
		
		HttpSession session = request.getSession();
		
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
				PentaCRUD crud = PentaCRUD.getInstance();
				
				User user = (User)session.getAttribute("user");
				Cart cart = (Cart)session.getAttribute("cart");
				
				if(user == null)
				{
					object.setResult("failed");
					object.setMessage("user not logged in");
					
					_response = gson.toJson(object);
				}
				else
				{
					if(cart.size() <= 0)
					{
						object.setResult("failed");
						object.setMessage("cart is empty");
						
						_response = gson.toJson(object);
					}
					else
					{
						boolean error = false;
						for(int i = 0; i < cart.size(); i++)
						{
							Purchasing purchasing = cart.getPurchasingByIndex(i);
							Article article = purchasing.getArticleDto();
							
							if(purchasing.getQuantity() > article.getAvailability())
								error = true;
						}
						
						if(error)
						{
							object.setResult("failed");
							object.setMessage("failed to buy products");
							
							_response = gson.toJson(object);
						}
						else
						{
							try {
								
								for(int i = 0; i < cart.size(); i++)
								{
									Purchasing purchasing = cart.getPurchasingByIndex(i);
									Article article = purchasing.getArticleDto();
									
									int currentQuantity = article.getAvailability();
									int newQuantity = currentQuantity - purchasing.getQuantity();
									
									article.setAvailability(newQuantity);
									crud.saveArticle(article);
								}
								
								cart.finalize(user);
								cart.clear();
								
								object.setResult("ok");
								object.setMessage("success");
								
								_response = gson.toJson(object);
							} catch (SQLException e) {
								e.printStackTrace();
							}
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
