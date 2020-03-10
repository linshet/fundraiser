package de.unidue.inf.is;

import java.io.IOException;
import java.sql.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ibm.db2.jcc.am.*;

import de.unidue.inf.is.domain.Project;
import de.unidue.inf.is.domain.User;
import de.unidue.inf.is.utils.*;



/**
 * Das könnte die Eintrittsseite sein.
 */
public class ProjectFunderServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

	
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
                    throws ServletException, IOException {
  
        boolean databaseExists = DBUtil.checkDatabaseExistsExternal();
        //boolean databaseExists = DBUtil.checkDatabaseExists();
        
        if (databaseExists) {
            request.setAttribute("db2exists", "vorhanden! Supi!");
            List<Project> Projects = new ArrayList<Project>();
            try {
            	Projects = DBSource.queryProject();
            } catch (SQLException e) {
				Projects = null;
			}
            if(Projects!=null)
            {
            	List<Project> openProjects = new ArrayList<Project>();
            	List<Project> closedProjects = new ArrayList<Project>();

            	for(int i = 0; i< Projects.size();i++)
            	{
            		if(Projects.get(i).getStatus().equals("offen"))
            			openProjects.add(Projects.get(i));
            		else
            			closedProjects.add(Projects.get(i));
            	}
            	
            	if(!openProjects.isEmpty())
            	{
            		request.setAttribute("openProjects", openProjects);
            	}
            	else
            	{
            		request.setAttribute("EmptyOpenProjects", "There is currently no open projects in the database.");

            	}
            	if(!closedProjects.isEmpty())
            	{
                    request.setAttribute("closedProjects", closedProjects);
            	}
            	else
            	{
            		request.setAttribute("EmptyClosedProjects", "There is currently no closed projects in the database.");

            	}

            }
            else
            {
            	request.setAttribute("message", "There is currently no projects in the database.");
            }
        }
        else {
            request.setAttribute("db2exists", "nicht vorhanden :-(");
        }

        request.getRequestDispatcher("projectFunder_start.ftl").forward(request, response);
    }

}
