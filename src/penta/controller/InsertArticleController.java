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

import penta.objects.JsonObject;
import penta.objects.ArticleStructure;
import penta.database.*;
import penta.shop.*;

import com.google.gson.*;

@WebServlet("/InsertArticleController")
public class InsertArticleController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public InsertArticleController() {
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
				ArticleStructure artStruct = gson.fromJson(gson.toJson(jsonObject.getContent()),
													   ArticleStructure.class);
				
				PentaCRUD crud = PentaCRUD.getInstance();
				
				Article article = new Article();
				article.setName			(artStruct.getName()		);
				article.setDescription	(artStruct.getDescription()	);
				article.setPrice		(artStruct.getPrice()		);
				article.setAvailability	(artStruct.getAvailability());
				article.setCategory		(artStruct.getCategory()	);
				article.setShopWindow	(artStruct.getWindow()		);
				article.setPhoto		(artStruct.getPhoto()		);
				
				try {
					crud.insertArticle(article);
					
					object.setResult("ok");
					object.setMessage("success");
					
					_response = gson.toJson(object);
				} catch (SQLException e) {
					e.printStackTrace();
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
