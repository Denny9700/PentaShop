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

import penta.objects.JsonObject;
import penta.database.*;
import penta.shop.*;

@WebServlet("/UpdateCartController")
public class UpdateCartController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public UpdateCartController() {
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
				CartProductStructure cartProdStructure = gson.fromJson(gson.toJson(jsonObject.getContent()),
														CartProductStructure.class);
				
				PentaCRUD crud = PentaCRUD.getInstance();
				
				HttpSession session = request.getSession();
				if(session != null)
				{
					Cart cart = (Cart)session.getAttribute("cart");
					if(cart == null)
					{
						cart = new Cart();
						session.setAttribute("cart", cart);
					}
					
					try {
						Article article = crud.findArticle(cartProdStructure.getId());
						article = crud.fillArticle(article);
						
						if(article != null)
						{
							if(article.getAvailability() < cartProdStructure.getQuantity())
							{
								object.setResult("failed");
								object.setMessage("wrong quantity");
								
								_response = gson.toJson(object);
							}
							else
							{
								cart.add(article, cartProdStructure.getQuantity());
								
								object.setResult("ok");
								object.setMessage("success");
								
								int quantity = cart.size();
								CartElementStructure struct = new CartElementStructure();
								struct.setQuantity(quantity);
								
								object.setContent(struct);
								_response = gson.toJson(object);
								
								System.out.println("Oggetti nel carrello: " + cart.size());
								System.out.println("Total: " + cart.calculateTotal());
							}
						}
						
					} catch (SQLException e) {
						e.printStackTrace();
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

class CartProductStructure
{
	private int id;
	private int quantity;
	
	public CartProductStructure() {}
	public CartProductStructure(int id, int quantity)
	{
		this.id = id;
		this.quantity = quantity;
	}
	
	public int getId	(			) { return id; 		}
	public void setId	(int id		) { this.id = id; 	}
	
	public int getQuantity	(					) { return quantity; 			}
	public void setQuantity	(int quantity		) { this.quantity = quantity; 	}
	
}

class CartElementStructure
{
	private int quantity;
	
	public CartElementStructure() {}
	public CartElementStructure(int quantity)
	{
		this.quantity = quantity;
	}
	
	public int getQuantity	(					) { return quantity; 			}
	public void setQuantity	(int quantity		) { this.quantity = quantity; 	}
}
