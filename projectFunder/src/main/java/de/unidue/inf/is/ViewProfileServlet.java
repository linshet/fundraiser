package de.unidue.inf.is;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.unidue.inf.is.domain.User;
import de.unidue.inf.is.utils.DBSource;


@WebServlet("/ViewProfileServlet")
public class ViewProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public ViewProfileServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		User user = null;
		
		if(username !=null)
		{
			user = DBSource.get_User(username);
		}
		
		if(user!=null)
		{
			try {
				user.setProjectsCreated(DBSource.get_ProjectsCreated(user));
				user.setProjectsFunded(DBSource.get_ProjectsFunded(user));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			request.setAttribute("user", user);
		}
		request.getRequestDispatcher("view_profil.ftl").forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String action = request.getParameter("submit");
		
		if(action.equals("Mein Profil"))
		{
			User user = null;
			try {
				user = DBSource.get_LoginUser();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(user!=null)
			{
				try {
					user.setProjectsCreated(DBSource.get_ProjectsCreated(user));
					user.setProjectsFunded(DBSource.get_ProjectsFunded(user));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				request.setAttribute("user", user);	
			}
			
			request.getRequestDispatcher("view_profil.ftl").forward(request, response);
		}
		


	}

}
