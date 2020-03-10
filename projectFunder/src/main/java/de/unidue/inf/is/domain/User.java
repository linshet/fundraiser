package de.unidue.inf.is.domain;

import java.util.List;

public final class User {

    public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public String getProfile() {
		return profile;
	}



	public void setProfile(String profile) {
		this.profile = profile;
	}


	private String name;
    private String email;
    private String profile;

    public List<Project> getProjectsCreated() {
		return projectsCreated;
	}



	public void setProjectsCreated(List<Project> projectsCreated) {
		this.projectsCreated = projectsCreated;
	}



	public List<Project> getProjectsFunded() {
		return projectsFunded;
	}



	public void setProjectsFunded(List<Project> projectsFunded) {
		this.projectsFunded = projectsFunded;
	}


	private List<Project> projectsCreated = null;
    private List<Project> projectsFunded = null;
    public int getProjectsCreated_Count() {
    	if(!projectsCreated.equals(null))
    		return projectsCreated.size();
    	else
    		return 0;
	}



	public int getProjectsFunded_Count() {
		if(!projectsFunded.equals(null))
			return projectsFunded.size();
		else
			return 0;
	}


	private int projectsCreated_Count;
    private int projectsFunded_Count;
    
    public boolean isNoCreatedProject() {
    	if(!projectsCreated.equals(null))
    	{
    		if(projectsCreated.isEmpty())
    			return true;
    		else
    			return false;
    	}
    	else
    		return false;
	}



	public boolean isNoFundedProject() {
    	if(!projectsFunded.equals(null))
    	{
    		if(projectsFunded.isEmpty())
    			return true;
    		else
    			return false;
    	}
    	else
    		return false;
	}


	private boolean noCreatedProject;
    private boolean noFundedProject;



    public User() {
    }


}