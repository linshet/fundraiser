package de.unidue.inf.is;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.unidue.inf.is.domain.Project;
import de.unidue.inf.is.utils.DBSource;


@WebServlet("/SearchProjectServlet")
public class SearchProjectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public SearchProjectServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("search_project.ftl").forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

			String searchword = request.getParameter("titel").toLowerCase();
			
			List<Project> Projects = new ArrayList<Project>();
	        try {
	        	Projects = DBSource.search_Project(searchword);
	        } catch (SQLException e) {
				Projects = null;
			}
	        if(Projects!=null)
	        {
	        	request.setAttribute("projects", Projects);
	        	request.getRequestDispatcher("search_project.ftl").forward(request, response);
	        }
	        else
	        {
	        	request.setAttribute("message", "No match found.");
	        	request.getRequestDispatcher("search_project.ftl").forward(request, response);

	        }
	}

}
