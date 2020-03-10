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


@WebServlet("/NewProjectServlet")
public class NewProjectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public NewProjectServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String submit = request.getParameter("submit");
		User loginUser = null;
		try {
			loginUser =  DBSource.get_LoginUser();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(loginUser!=null)
		{
			try {
				loginUser.setProjectsCreated(DBSource.get_ProjectsCreated(loginUser));
				loginUser.setProjectsFunded(DBSource.get_ProjectsFunded(loginUser));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(submit.equals("Projekt erstellen") && loginUser!=null)
		{
			request.setAttribute("user", loginUser);
			request.getRequestDispatcher("new_project.ftl").forward(request, response);
		}
		else if(submit.equals("Erstellen") && loginUser!=null)
		{
			String titel = request.getParameter("titel");
			String finanzierungLimit = request.getParameter("fundlimit");
			String category = request.getParameter("category");
			String vorgaenger = request.getParameter("vorgaenger");
			String comment = request.getParameter("comment");
			int cate = Integer.valueOf(category);
			int vorg = Integer.valueOf(vorgaenger);
			String result = DBSource.post_newProject(titel, finanzierungLimit, cate  , vorg, comment, loginUser.getEmail());

			request.setAttribute("message",result);
			request.getRequestDispatcher("projectRedirect.ftl").forward(request, response);
		}
	}

}
