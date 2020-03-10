package de.unidue.inf.is;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.unidue.inf.is.domain.Project;
import de.unidue.inf.is.domain.User;
import de.unidue.inf.is.utils.DBSource;


@WebServlet("/EditProjectServlet")
public class EditProjectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public EditProjectServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String submit = request.getParameter("submit");
		String projectid = request.getParameter("projectid"); 
		

		Project project = null;
		if(projectid!=null)
		{
			project = DBSource.get_ProjectByID(projectid);
		}
		
		User loginUser = null;
		try {
			loginUser =  DBSource.get_LoginUser();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(loginUser!=null && project!=null) 
		{
			if(loginUser.getEmail().equals(project.getCreated_by().getEmail()) && project.getStatus().equals("offen"))
			{
				try {
					loginUser.setProjectsCreated(DBSource.get_ProjectsCreated(loginUser));
					loginUser.setProjectsFunded(DBSource.get_ProjectsFunded(loginUser));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		if(submit.equals("Projekt Editieren"))
		{
			if(loginUser.getEmail().equals(project.getCreated_by().getEmail()))
			{
				if(project.getStatus().equals("offen"))
				{
					request.setAttribute("user", loginUser);
					request.setAttribute("project", project);
					request.getRequestDispatcher("edit_project.ftl").forward(request, response);
				}
				else
				{
					request.setAttribute("projectid", project.getId());
					request.setAttribute("message", "This Project is closed!");
					request.getRequestDispatcher("projectRedirect.ftl").forward(request, response);
				}

			}
			else
			{
				request.setAttribute("projectid", project.getId());
				request.setAttribute("message", "you dont have the permission to edit the project!");
				request.getRequestDispatcher("projectRedirect.ftl").forward(request, response);
			}

		}
		
		else if(submit.equals("Aktualisieren"))
		{
			String name = request.getParameter("titel");
			String fundlimit = request.getParameter("fundlimit");
			String category = request.getParameter("category");
			String vorgaenger = request.getParameter("vorgaenger");
			String comment = request.getParameter("comment");
			
			String result = null;
			
			if(Double.valueOf(fundlimit)>=project.getFund_limit())
			{
				result = DBSource.update_Project(projectid, name, fundlimit, category, vorgaenger, comment);
			}
			if(result!=null)
			{
				if(result.equals("0"))
				{
					request.setAttribute("projectid", project.getId());
					request.getRequestDispatcher("ViewProjectServlet").forward(request, response);
				}
				else
				{
					request.setAttribute("projectid", projectid);
					request.setAttribute("message", result);
					request.getRequestDispatcher("projectRedirect.ftl").forward(request, response);
				}
			}
		}

	}

}
