package penta.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.*;

import penta.database.PentaCRUD;
import penta.shop.Article;

@WebServlet("/ArticleController")
public class ArticleController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public ArticleController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PentaCRUD crud = PentaCRUD.getInstance();
		String productID = request.getParameter("id");
		if(!productID.isEmpty())
		{
			int realID = Integer.parseInt(productID);
			if(realID != 0)
			{
				try {
					Article article = crud.findArticle(realID);
					article = crud.fillArticle(article);
					
					if(article.getAvailability() < 0)
					{
						RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
						dispatcher.forward(request, response);
						
						return;
					}
					
					request.setAttribute("article", article);
					
					RequestDispatcher dispatcher = request.getRequestDispatcher("/product.jsp");
					dispatcher.forward(request, response);
					
				} catch (SQLException e) {
					response.sendRedirect("/PentaShop/index.jsp");
				}
			}
		}
		
		response.sendRedirect("/PentaShop/index.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
