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

/**
 * Servlet implementation class NewCommentServlet
 */
@WebServlet("/NewCommentServlet")
public class NewCommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public NewCommentServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("submit");

		if(action!=null)
		{
			if(action.equals("Comment"))
			{
				String projectid = request.getParameter("projectid"); 
				
				User loginUser = null;
				try {
					loginUser =  DBSource.get_LoginUser();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				Project project = null;
				if(projectid!=null)
				{
					project = DBSource.get_ProjectByID(projectid);
				}
				
				if(loginUser!=null)
				{
					if(projectid!=null)
					{
						request.setAttribute("project", project);
						request.getRequestDispatcher("new_comment.ftl").forward(request, response);
					}
					else
					{
						request.setAttribute("message", "no project found");
						request.getRequestDispatcher("projectRedirect.ftl").forward(request, response);
					}
				}
				else
				{
					request.setAttribute("projectid", projectid);
					request.setAttribute("message", "no login user found");
					request.getRequestDispatcher("projectRedirect.ftl").forward(request, response);
				}
			}
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
		String action = request.getParameter("submit");

		if(action!=null)
		{
			if(action.equals("Kommentar hinzufuegen"))
			{
				String anonym = request.getParameter("anonym");
				String projectid = request.getParameter("projectid"); 
				String comment = request.getParameter("comment"); 
				
				User loginUser = null;
				try {
					loginUser =  DBSource.get_LoginUser();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if(loginUser!=null)
				{
					if(projectid!=null)
					{
						String result;
						if(anonym!=null)
						{
							result = DBSource.post_newComment(loginUser.getEmail(), projectid, comment, "privat");
						}
						else
						{
							result = DBSource.post_newComment(loginUser.getEmail(), projectid, comment, "oeffentlich");
						}
						if(result=="0") 
						{
							request.setAttribute("projectid", projectid);
							request.getRequestDispatcher("/ViewProjectServlet?projectid="+projectid).forward(request, response);
						}
						else
						{
							request.setAttribute("projectid", projectid);
							request.setAttribute("message", result);
							request.getRequestDispatcher("projectRedirect.ftl").forward(request, response);
						}
					}
					else
					{
						request.setAttribute("message", "no project found");
						request.getRequestDispatcher("projectRedirect.ftl").forward(request, response);
					}
				}
				else
				{
					request.setAttribute("projectid", projectid);
					request.setAttribute("message", "no login user found");
					request.getRequestDispatcher("projectRedirect.ftl").forward(request, response);
				}
			}
		}
			
	}

}
