package de.unidue.inf.is;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.unidue.inf.is.domain.Comment;
import de.unidue.inf.is.domain.Project;
import de.unidue.inf.is.domain.Spenden;
import de.unidue.inf.is.domain.User;
import de.unidue.inf.is.utils.DBSource;

/**
 * Servlet implementation class ViewProjectServlet
 */
@WebServlet("/ViewProjectServlet")
public class ViewProjectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public ViewProjectServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String projectid = request.getParameter("projectid");
		Project project = null;
		if(projectid!=null)
		{
			project = DBSource.get_ProjectByID(projectid);
		}
		
		if(project!=null)
		{
			List<Comment> comments = DBSource.get_ProjectComments(projectid);
			List<Spenden> spendens = DBSource.get_ProjectSpendens(projectid);
			project.setComments(comments);
			project.setSpendens(spendens);
			request.setAttribute("project", project);
			request.getRequestDispatcher("view_project.ftl").forward(request, response);
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String submit = request.getParameter("submit");
		String projectid = request.getParameter("projectid");
		String projectowner = request.getParameter("projectowner");
		
		User user = null;
		try {
			user = DBSource.get_LoginUser();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
		if(submit.equals("Projekt Editieren") && projectid!=null)
		{
			if(user.getEmail().equals(projectowner))
			{
				request.setAttribute("submit", submit);
				request.setAttribute("projectid", projectid);
				request.getRequestDispatcher("EditProjectServlet").forward(request, response);
			}
			else
			{
				request.setAttribute("projectid", projectid);
				request.setAttribute("message", "you do not have permission to edit this project!!");
				request.getRequestDispatcher("projectRedirect.ftl").forward(request, response);
			}
		}
		else if(submit.equals("Spenden"))
		{
			request.setAttribute("submit", submit);
			request.setAttribute("projectid", projectid);
			request.getRequestDispatcher("NewFundServlet").forward(request, response);
		}
		else if(submit.equals("Comment"))
		{
			request.setAttribute("submit", submit);
			request.setAttribute("projectid", projectid);
			request.getRequestDispatcher("NewCommentServlet").forward(request, response);
		}
		else if(submit.equals("Projekt Loeschen"))
		{
			if(user.getEmail().equals(projectowner))
			{
				String result = DBSource.delete_Project(projectid);
				if(result.equals("0"))
				{
					request.setAttribute("message", "project deleted!");
					request.getRequestDispatcher("projectRedirect.ftl").forward(request, response);
				}
				else
				{
					request.setAttribute("message", result);
					request.getRequestDispatcher("projectRedirect.ftl").forward(request, response);
				}
			}
			else
			{
				request.setAttribute("projectid", projectid);
				request.setAttribute("message", "you do not have permission to delete this project!!");
				request.getRequestDispatcher("projectRedirect.ftl").forward(request, response);
			}
		}
		doGet(request, response);

	}

}
