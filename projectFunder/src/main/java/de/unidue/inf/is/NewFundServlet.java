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
 * Servlet implementation class NewFundServlet
 */
@WebServlet("/NewFundServlet")
public class NewFundServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public NewFundServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    private boolean check_userFunded(User loginUser, String projectid)
    {
		for(int i = 0; i<loginUser.getProjectsFunded_Count();i++)
		{
			if(loginUser.getProjectsFunded().get(i).getId().equals(projectid))
				return true;
		}
		return false;
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
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
			loginUser.setProjectsFunded(DBSource.get_ProjectsFunded(loginUser));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		if(submit.equals("Spenden"))
		{
			if(loginUser!=null && project!=null) 
			{
				if(project.getStatus().equals("offen"))
				{
					if(!check_userFunded(loginUser, projectid))
					{
						request.setAttribute("project", project);
						request.getRequestDispatcher("new_project_fund.ftl").forward(request, response);					
					}
					else
					{
						request.setAttribute("projectid", projectid);
						request.setAttribute("message", "you already funded this project");
						request.getRequestDispatcher("projectRedirect.ftl").forward(request, response);
					}	
				}
				else
				{
					request.setAttribute("projectid", projectid);
					request.setAttribute("message", "This project is closed.");
					request.getRequestDispatcher("projectRedirect.ftl").forward(request, response);
				}		
			}
		}
		
		else if(submit.equals("confirm spenden"))
		{
			if(!check_userFunded(loginUser, projectid))
			{
				if(project.getStatus().equals("offen"))
				{
					String anonym = request.getParameter("anonym");
					double fund = Double.valueOf(request.getParameter("spendenbetrag"));
					String GetGuthaben = DBSource.get_UserGuthaben(loginUser.getEmail());
					
					if(GetGuthaben!=null)
					{
						Double guthaben = Double.valueOf(GetGuthaben);
						
						if(fund > guthaben)
						{
							request.setAttribute("message", "You do not have enough guthaben left. Current balance: " + String.valueOf(guthaben));
							request.setAttribute("project", project);
							request.getRequestDispatcher("new_project_fund.ftl").forward(request, response);	
						}
						else
						{
							String result;
							if(anonym==null)
							{
								result = DBSource.fund_Project(loginUser.getEmail(), String.valueOf(fund), project.getId(), "oeffentlich");
							}
							else 
							{
								result = DBSource.fund_Project(loginUser.getEmail(),  String.valueOf(fund), project.getId(), "privat");
							}

							if(result.equals("0"))
							{
								request.getRequestDispatcher("/ViewProjectServlet?projectid="+project.getId()).forward(request, response);
							}
							else
							{
								request.setAttribute("message", result);
								request.setAttribute("project", project);
								request.getRequestDispatcher("new_project_fund.ftl").forward(request, response);	
							}
						}
					}
					else
					{
						request.setAttribute("message", "Error occured while checking konto.");
						request.setAttribute("project", project);
						request.getRequestDispatcher("new_project_fund.ftl").forward(request, response);	
					}
				}
				else
				{
					request.setAttribute("projectid", projectid);
					request.setAttribute("message", "This project is closed.");
					request.getRequestDispatcher("projectRedirect.ftl").forward(request, response);
				}
			}
			else
			{
				request.setAttribute("projectid", projectid);
				request.setAttribute("message", "you already funded this project");
				request.getRequestDispatcher("projectRedirect.ftl").forward(request, response);
			}	
		}
	}

}
