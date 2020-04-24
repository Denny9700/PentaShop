package penta.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import penta.objects.JsonObject;
import penta.database.*;
import penta.shop.*;

@WebServlet("/CategoryProductController")
public class CategoryProductController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public CategoryProductController() {
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
				CategoryProductStructure catProdStructure = gson.fromJson(gson.toJson(jsonObject.getContent()),
															CategoryProductStructure.class);
				
				int code = catProdStructure.getCode();
				PentaCRUD crud = PentaCRUD.getInstance();
				try
				{
					ArrayList<Article> articles = crud.findArticlesByCategory(code);
					ArrayList<Article> _articles = new ArrayList<Article>();
					
					object.setResult("ok");
					object.setMessage("all is ok");
					
					if(articles != null && articles.size() > 0)
					{
						for(int i = 0; i < articles.size(); i++)
							if(articles.get(i).getShopWindow() == 2)
								_articles.add(articles.get(i));
						
						object.setContent(_articles);
					}
					
					_response = gson.toJson(object);
				} catch (SQLException e) {}
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

class CategoryProductStructure
{
	private int code;
	
	public CategoryProductStructure() {}
	public CategoryProductStructure(int code)
	{
		this.code = code;
	}
	
	public int getCode	(			) { return code; 		}
	public void setCode	(int code	) { this.code = code; 	}
	
}
