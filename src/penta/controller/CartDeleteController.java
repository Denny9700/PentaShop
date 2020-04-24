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

import penta.database.PentaCRUD;
import penta.objects.JsonObject;
import penta.shop.Article;
import penta.shop.Cart;

@WebServlet("/CartDeleteController")
public class CartDeleteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
    public CartDeleteController() {
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
				CartProdInfo prodInfo = gson.fromJson(gson.toJson(jsonObject.getContent()),
						CartProdInfo.class);
				
				PentaCRUD crud = PentaCRUD.getInstance();
				
				int itemId = prodInfo.getId();
				if(itemId == 0)
				{
					object.setResult("failed");
					object.setMessage("no product to delete");
					
					_response = gson.toJson(object);
				}
				else
				{
					Cart cart = (Cart)session.getAttribute("cart");
					if(cart == null)
					{
						object.setResult("failed");
						object.setMessage("no product to delete");
						
						_response = gson.toJson(object);
					}
					else
					{
						Article article = cart.getArticle(itemId);
						cart.delete(article);
						
						CartProdResponse resp = new CartProdResponse();
						resp.setCartSize(cart.size());
						resp.setTotal(cart.calculateTotal());
						
						object.setResult("ok");
						object.setMessage("success");
						object.setContent(resp);
						
						_response = gson.toJson(object);
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

class CartProdInfo
{
	private int id;
	
	public CartProdInfo() {}
	public CartProdInfo(int id)
	{
		this.id = id;
	}
	
	public int getId	(		) { return id; 		}
	public void setId	(int id	) { this.id = id; 	}
}

class CartProdResponse
{
	private int cartSize;
	private double total;
	
	public CartProdResponse() {}
	public CartProdResponse(int cartSize, double total)
	{
		this.cartSize = cartSize;
		this.total = total;
	}
	
	public int getCartSize	(				) { return cartSize; 		}
	public void setCartSize	(int size		) { this.cartSize = size; 	}
	
	public double getTotal	(				) { return total; 			}
	public void setTotal	(double total	) { this.total = total; 	}
}
