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
import penta.database.dto.ResidenceDto;
import penta.objects.JsonObject;
import penta.objects.ResidenceStructure;
import penta.shop.UserManager;
import penta.shop.User;

@WebServlet("/ResidenceController")
public class ResidenceController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public ResidenceController() {
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
				ResidenceStructure resStructure = gson.fromJson(gson.toJson(jsonObject.getContent()),
													   ResidenceStructure.class);
				
				PentaCRUD crud = PentaCRUD.getInstance();
				try
				{
					ResidenceDto residence = new ResidenceDto();
					residence.setRegion(resStructure.getRegion());
					residence.setCity(resStructure.getCity());
					residence.setAddress(resStructure.getAddress());
					residence.setCAP(resStructure.getCap());
					residence.setUser(resStructure.getUser());
					
					crud.insertResidence(residence);
					User user = (User)session.getAttribute("user");
					user.addResidence(residence);
					
					object.setResult("ok");
					object.setMessage("success");
					
					_response = gson.toJson(object);
					
				} catch (Exception e) {}
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
