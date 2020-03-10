package de.unidue.inf.is.utils;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import de.unidue.inf.is.domain.Category;
import de.unidue.inf.is.domain.Comment;
import de.unidue.inf.is.domain.Project;
import de.unidue.inf.is.domain.Spenden;
import de.unidue.inf.is.domain.User;

public final class DBSource {
	
	public static List<Project> queryProject() throws SQLException {
		String sql =  "select p.kennung, p.titel, p.beschreibung, p.status, p.finanzierungslimit, "
				+ "k.icon, b.email, b.name, b.beschreibung,  s.actualfund, vorg.titel as vorgaenger, vorg.kennung as vorID, k.name, k.id "
				+ "from DBP076.PROJEKT as p "
				+ "inner join DBP076.BENUTZER as b on b.email = p.ersteller "
				+ "inner join DBP076.KATEGORIE as k on k.id = p.kategorie "
				+ "left join (select projekt, SUM(spendenbetrag) as actualfund from DBP076.SPENDEN group by projekt) as s on s.projekt = p.kennung "
				+ "left join DBP076.PROJEKT as vorg on vorg.kennung = p.vorgaenger " ;
			
    try{
		//Connection con = DBUtil.getConnection();
		Connection con = DBUtil.getExternalConnection();
		PreparedStatement ps =  con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		List<Project> projects = new ArrayList<Project>();

		while (rs.next()) 
		{
			Project project = new Project();
			User user = new User();
			
			String name = rs.getString(2); 
			String id = String.valueOf(rs.getInt(1));
			String comment = rs.getString(3);
			String status = rs.getString(4);
			Double fundlimit = rs.getDouble(5);
			String categoryIcon = rs.getString(6);
			String useremail = rs.getString(7);
			String username = rs.getString(8);
			String userprofile = rs.getString(9);
			Double actualfund = rs.getDouble(10);
			String vorgaenger = rs.getString(11);
			String vorID = rs.getString(12);
			String kategorieName = rs.getString(13);
			String kategorieID = rs.getString(14);

			Category category = new Category();
			category.setId(kategorieID);
			category.setIcon(categoryIcon);
			category.setName(kategorieName);
			
			user.setEmail(useremail);
			user.setName(username);
			user.setProfile(userprofile);
			
			project.setName(name);
			project.setId(id);
			project.setDescription(comment);
			project.setStatus(status);
			project.setFund_limit(fundlimit);
			project.setCreated_by(user);
			project.setCategory(category);
			project.setActual_fund(actualfund);
			
			Project vorg = new Project();
			vorg.setId(vorID);
			vorg.setName(vorgaenger);
			project.setVorganger(vorg);
			projects.add(project);
		}
		rs.close();
		ps.close();
		con.close();

		return projects;
    } 
    catch (SQLException e)
    { e.printStackTrace();
    return null;} 
    catch (Exception e) {return null;}

	}
	
	public static  User get_LoginUser() throws SQLException
	{
		User loginUser = get_User("DummyUser");
		return loginUser;
	}
	

	public static User get_User(String name)
	{
		String sql =  "select email, beschreibung from DBP076.BENUTZER where name = ? ";
			
    try{
		//Connection con = DBUtil.getConnection();
		Connection con = DBUtil.getExternalConnection();
		PreparedStatement ps =  con.prepareStatement(sql);
		ps.setString(1, name); //prevent sql injection
		ResultSet rs = ps.executeQuery();
		User user = new User();

		while (rs.next()) 
		{
	
			String email = rs.getString(1);
			String profile = rs.getString(2);
			
			user.setEmail(email);
			user.setName(name);
			user.setProfile(profile);

		}
		rs.close();
		ps.close();
		con.close();

		return user;
    } 
    catch (SQLException e)
    { e.printStackTrace();
    return null;} 
    catch (Exception e) {return null;}
	}

/*	public static void set_User_ProjectsCount(User user)
	{
		String sql =  "select b.name, count(p.titel) as created, count(s.projekt) as donated from DBP076.BENUTZER as b "
				+ "left join DBP076.PROJEKT as p on p.ersteller = b.email "
				+ "left join DBP076.SPENDEN as s on s.spender = b.email "
				+ "where b.name =  '"+ user.getName() +"' " 
				+ "group by b.name ";
		
	    try{
			//Connection con = DBUtil.getConnection();
			Connection con = DBUtil.getExternalConnection();
			PreparedStatement ps =  con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) 
			{
				int created = rs.getInt(2);
				int donated = rs.getInt(3);
				
				user.setProjectsCreated(created);
				user.setProjectsFunded(donated);

			}
			rs.close();
			ps.close();
			con.close();
	    } 
	    catch (SQLException e)
	    { e.printStackTrace();} 
	    catch (Exception e) {};
	}*/

	public static List<Project> get_ProjectsCreated(User user) throws SQLException {
		String sql =  "select p.titel, p.status, "
				+ "k.icon, s.actualfund, p.kennung, k.id, k.name "
				+ "from DBP076.PROJEKT as p "
				+ "inner join DBP076.BENUTZER as b on b.email = p.ersteller "
				+ "inner join DBP076.KATEGORIE as k on k.id = p.kategorie "
				+ "left join (select projekt, SUM(spendenbetrag) as actualfund from DBP076.SPENDEN group by projekt) as s on s.projekt = p.kennung "
				+ "where b.name = ? ";
			
    try{
		//Connection con = DBUtil.getConnection();
		Connection con = DBUtil.getExternalConnection();
		PreparedStatement ps =  con.prepareStatement(sql);
		ps.setString(1, user.getName()); //prevent sql injection
		ResultSet rs = ps.executeQuery();
		List<Project> projects = new ArrayList<Project>();

		while (rs.next()) 
		{
			Project project = new Project();
			
			String name = rs.getString(1); 
			String status = rs.getString(2);
			String cateicon = rs.getString(3);
			Double actualfund = rs.getDouble(4);
			String id = rs.getString(5);
			String cateid = rs.getString(6);
			String catename = rs.getString(7);
			
			Category category = new Category();
			category.setId(cateid);
			category.setIcon(cateicon);
			category.setName(catename);
	
			project.setId(id);
			project.setName(name);
			project.setStatus(status);
			project.setCategory(category);
			project.setActual_fund(actualfund);
			projects.add(project);
		}
		rs.close();
		ps.close();
		con.close();

		return projects;
    } 
    catch (SQLException e)
    { e.printStackTrace();
    return null;} 
    catch (Exception e) {return null;}

	}

	public static List<Project> get_ProjectsFunded(User user) throws SQLException {
		String sql =  "select p.titel, p.status, p.finanzierungslimit, "
				+ "k.icon, s.spendenbetrag, p.kennung, k.id, k.name "
				+ "from DBP076.SPENDEN as s "
				+ "left join DBP076.PROJEKT as p on p.kennung = s.projekt "
				+ "left join DBP076.KATEGORIE as k on k.id = p.kategorie "
				+ "where s.spender = ? ";
			
    try{
		//Connection con = DBUtil.getConnection();
		Connection con = DBUtil.getExternalConnection();
		PreparedStatement ps =  con.prepareStatement(sql);
		ps.setString(1, user.getEmail()); //prevent sql injection
		ResultSet rs = ps.executeQuery();
		List<Project> projects = new ArrayList<Project>();

		while (rs.next()) 
		{
			Project project = new Project();
			
			String name = rs.getString(1); 
			String status = rs.getString(2);
			String cateicon = rs.getString(4);
			Double fundlimit = rs.getDouble(3);
			Double donated = rs.getDouble(5);
			String id = rs.getString(6);			
			String cateid = rs.getString(6);
			String catename = rs.getString(7);
			
			Category category = new Category();
			category.setId(cateid);
			category.setIcon(cateicon);
			category.setName(catename);
			
			project.setId(id);
			project.setName(name);
			project.setStatus(status);
			project.setCategory(category);
			project.setFund_limit(fundlimit);
			project.setActual_fund(donated);
			projects.add(project);
		}
		rs.close();
		ps.close();
		con.close();

		return projects;
    } 
    catch (SQLException e)
    { e.printStackTrace();
    return null;} 
    catch (Exception e) {return null;}

	}



	public static String post_newProject(String titel, String finanzierungLimit, int category, int vorgaenger,
			String comment, String ersteller) {
		// must insert kennung otherwise sql error will be thrown
			
    try{
		//Connection con = DBUtil.getConnection();
		Connection con = DBUtil.getExternalConnection();
		
		String sql = "";
		PreparedStatement ps;
		
		if(vorgaenger != -1 && comment!=null) //successful
		{
			sql = "INSERT INTO DBP076.PROJEKT(kennung, titel, beschreibung, status, finanzierungslimit , kategorie, ersteller, vorgaenger) "
					+ "VALUES((SELECT MAX(kennung) + 1 FROM DBP076.PROJEKT), ? , ? , 'offen', ? , ? , ? , ? )";
			ps =  con.prepareStatement(sql);
			ps.setString(1, titel);
			ps.setString(2, comment);
			ps.setBigDecimal(3, new BigDecimal(finanzierungLimit));
			ps.setInt(4, category);
			ps.setString(5, ersteller);
			ps.setInt(6, vorgaenger);
		}
		else if(comment !=null) //successful
		{
			sql = "INSERT INTO DBP076.PROJEKT(kennung, titel, beschreibung, status, finanzierungslimit , kategorie, ersteller) "
					+ "VALUES((SELECT MAX(kennung) + 1 FROM DBP076.PROJEKT), ? , ? , 'offen', ? , ? , ? )";
			ps =  con.prepareStatement(sql);
			ps.setString(1, titel);
			ps.setString(2, comment);
			ps.setBigDecimal(3, new BigDecimal(finanzierungLimit));
			ps.setInt(4, category);
			ps.setString(5, ersteller);
			//ps.setInt(6, vorgaenger);
		}
		else if(vorgaenger!= -1) //successful
		{
			sql = "INSERT INTO DBP076.PROJEKT(kennung, titel, status, finanzierungslimit , kategorie, ersteller, vorgaenger) "
					+ " VALUES((SELECT MAX(kennung) + 1 FROM DBP076.PROJEKT), ? , 'offen', ? , ? , ? , ? )";
			ps =  con.prepareStatement(sql);
			ps.setString(1, titel);
			//ps.setString(2, comment);
			ps.setBigDecimal(2, new BigDecimal(finanzierungLimit));
			ps.setInt(3, category);
			ps.setString(4, ersteller);
			ps.setInt(5, vorgaenger);
		}
		else //successful
		{
			sql = "INSERT INTO DBP076.PROJEKT(kennung, titel, status, finanzierungslimit , kategorie, ersteller) "
					+ "VALUES((SELECT MAX(kennung) + 1 FROM DBP076.PROJEKT), ? , 'offen', ? , ? , ? )";
			ps =  con.prepareStatement(sql);
			ps.setString(1, titel);
			//ps.setString(2, comment);
			ps.setBigDecimal(2, new BigDecimal(finanzierungLimit));
			ps.setInt(3, category);
			ps.setString(4, ersteller);
			//ps.setInt(6, vorgaenger);
		}

		ps.executeUpdate();
		
		ps.close();
		con.close();

		return "Project successfully created.";
    } 
    catch (SQLException e)
    { e.printStackTrace();
    return e.getMessage();} 
    catch (Exception e) {return e.getMessage();}

	}

	public static Project get_ProjectByID(String projectid) {
		String sql =  "select p.kennung, p.titel, p.beschreibung, p.status, p.finanzierungslimit, "
				+ "k.icon, b.email, b.name, b.beschreibung,  s.actualfund, vorg.titel as vorgName, vorg.kennung as vorID, k.name, k.id "
				+ "from DBP076.PROJEKT as p "
				+ "inner join DBP076.BENUTZER as b on b.email = p.ersteller "
				+ "inner join DBP076.KATEGORIE as k on k.id = p.kategorie "
				+ "left join (select projekt, SUM(spendenbetrag) as actualfund from DBP076.SPENDEN group by projekt) as s on s.projekt = p.kennung "
				+ "left join DBP076.PROJEKT as vorg on vorg.kennung = p.vorgaenger "
				+ "where p.kennung = ? ";
			
    try{
		//Connection con = DBUtil.getConnection();
		Connection con = DBUtil.getExternalConnection();
		PreparedStatement ps =  con.prepareStatement(sql);
		ps.setInt(1, Integer.parseInt(projectid));
		ResultSet rs = ps.executeQuery();
		Project project = new Project();
		
		while (rs.next()) 
		{

			User user = new User();
			
			String name = rs.getString(2); 
			String id = String.valueOf(rs.getInt(1));
			String comment = rs.getString(3);
			String status = rs.getString(4);
			Double fundlimit = rs.getDouble(5);
			String categoryIcon = rs.getString(6);
			String useremail = rs.getString(7);
			String username = rs.getString(8);
			String userprofile = rs.getString(9);
			Double actualfund = rs.getDouble(10);
			String vorgName = rs.getString(11);
			String vorID = rs.getString(12);
			String kategorieName = rs.getString(13);
			String kategorieID = rs.getString(14);

			Category category = new Category();
			category.setId(kategorieID);
			category.setIcon(categoryIcon);
			category.setName(kategorieName);
			
			user.setEmail(useremail);
			user.setName(username);
			user.setProfile(userprofile);
			
			project.setName(name);
			project.setId(id);
			project.setDescription(comment);
			project.setStatus(status);
			project.setFund_limit(fundlimit);
			project.setCreated_by(user);
			project.setCategory(category);
			project.setActual_fund(actualfund);
			
			Project vorg = new Project();
			vorg.setId(vorID);
			vorg.setName(vorgName);
			project.setVorganger(vorg);
		}
		rs.close();
		ps.close();
		con.close();

		return project;
    } 
    catch (SQLException e)
    { e.printStackTrace();
    return null;} 
    catch (Exception e) {return null;}

	}

	public static String update_Project(String projectid, String titel, String finanzierungLimit, String category,
			String vorgaenger, String comment) {
	
		
		try {
			Connection con = DBUtil.getExternalConnection();
			String sql = ""; //check how to set column value from not null to null
			PreparedStatement ps;
			if(!vorgaenger.equals("keinVorgaenger") && comment!=null) //successful
			{
				sql = "UPDATE DBP076.PROJEKT SET titel = ? , beschreibung = ? , finanzierungslimit = ? , kategorie = ? , vorgaenger = ?  "
						+ "WHERE kennung = ? " ;
				ps =  con.prepareStatement(sql);
				ps.setString(1, titel);
				ps.setString(2, comment);
				ps.setBigDecimal(3, new BigDecimal(finanzierungLimit));
				ps.setInt(4, Integer.parseInt(category));
				ps.setInt(5, Integer.parseInt(vorgaenger));
				ps.setInt(6, Integer.parseInt(projectid));
			}
			else if(comment !=null) //successful
			{
				sql = "UPDATE DBP076.PROJEKT SET titel = ? , beschreibung = ? , finanzierungslimit = ? , kategorie = ? , vorgaenger = NULL "
						+ "WHERE kennung = ? ";
				ps =  con.prepareStatement(sql);
				ps.setString(1, titel);
				ps.setString(2, comment);
				ps.setBigDecimal(3, new BigDecimal(finanzierungLimit));
				ps.setInt(4, Integer.parseInt(category));
				ps.setInt(5, Integer.parseInt(projectid));
				//ps.setInt(5, Integer.parseInt(vorgaenger));
			}
			else if(!vorgaenger.equals("keinVorgaenger")) //successful
			{
				sql = "UPDATE DBP076.PROJEKT SET titel = ? , beschreibung = NULL, finanzierungslimit = ? , kategorie = ? , vorgaenger = ? "
						+ " WHERE kennung = ? " ;
				ps =  con.prepareStatement(sql);
				ps.setString(1, titel);
				//ps.setString(2, comment);
				ps.setBigDecimal(2, new BigDecimal(finanzierungLimit));
				ps.setInt(3, Integer.parseInt(category));
				ps.setInt(4, Integer.parseInt(vorgaenger));
				ps.setInt(5, Integer.parseInt(projectid));

			}
			else //successful
			{
				sql = "UPDATE DBP076.PROJEKT SET titel = ? , beschreibung = NULL, finanzierungslimit = ? , kategorie = ? , vorgaenger = NULL "
						+ "WHERE kennung = ? ";
				ps =  con.prepareStatement(sql);
				ps.setString(1, titel);
				//ps.setString(2, comment);
				ps.setBigDecimal(2, new BigDecimal(finanzierungLimit));
				ps.setInt(3, Integer.parseInt(category));
				//ps.setInt(4, Integer.parseInt(vorgaenger));
				ps.setInt(4, Integer.parseInt(projectid));
			}
			
			ps.executeUpdate();
			ps.close();
			con.close();

			return "0";
	    } 
	    catch (SQLException e)
	    { e.printStackTrace();
	    return e.getMessage();} 
	    catch (Exception e) {return e.getMessage();}
	}

	public static String post_newComment(String email, String projectid, String comment, String sichtbarkeit) {
		String insertKommentar = "SELECT id FROM FINAL TABLE(INSERT INTO DBP076.KOMMENTAR(id, text, sichtbarkeit) "
						+ "VALUES((SELECT Count(id) + 1 FROM DBP076.KOMMENTAR), ? , ? ))";
		String id = null;
		
		try {
			Connection con = DBUtil.getExternalConnection();
			PreparedStatement ps =  con.prepareStatement(insertKommentar);
			ps.setString(1, comment);
			ps.setString(2,  sichtbarkeit);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
			{
				id = String.valueOf(rs.getInt(1));
			}
			rs.close();
			ps.close();
			
			if(id!=null)
			{
				try {
					String insertSchreibt = "INSERT INTO DBP076.SCHREIBT(benutzer, projekt, kommentar) VALUES( ? , ? , ? )";
					PreparedStatement s =  con.prepareStatement(insertSchreibt);
					s.setString(1, email);
					s.setInt(2, Integer.parseInt(projectid));
					s.setInt(3, Integer.parseInt(id));
					s.executeUpdate();
					
					s.close();
					return "0";
				}	    
				catch (SQLException e)
			    { e.printStackTrace();
			    return e.getMessage();} 
			    catch (Exception e) {return e.getMessage();}
			}
			else
			{
				con.close();
				return "id from kommentar is null";
			}

	    } 
	    catch (SQLException e)
	    { e.printStackTrace();
	    return e.getMessage();} 
	    catch (Exception e) {return e.getMessage();}
	}
	
	public static List<Comment> get_ProjectComments(String projectid)
	{
		String sql =  "select s.projekt, k.text, k.sichtbarkeit, case when k.sichtbarkeit = 'privat' then 'Anonym' else b.name end as benutzername from DBP076.KOMMENTAR as k "
				+ "left join DBP076.SCHREIBT as s on s.kommentar = k.id "
				+ "left join DBP076.BENUTZER as b on b.email = s.benutzer "
				+ "where s.projekt = ? ";
			
    try{
		//Connection con = DBUtil.getConnection();
		Connection con = DBUtil.getExternalConnection();
		PreparedStatement ps =  con.prepareStatement(sql);
		ps.setInt(1, Integer.parseInt(projectid));
		ResultSet rs = ps.executeQuery();
		List<Comment> comments = new ArrayList<Comment>();
		
		while (rs.next()) 
		{
			String username = rs.getString(4);
			String id = String.valueOf(rs.getInt(1));
			String comment = rs.getString(2);
			String sichtbarkeit = rs.getString(3);
			
			Comment com = new Comment();
			com.setComment(comment);
			com.setProjectid(id);
			com.setSichtbarkeit(sichtbarkeit);
			com.setUsername(username);
			
			comments.add(com);
		}
		rs.close();
		ps.close();
		con.close();

		return comments;
    } 
    catch (SQLException e)
    { e.printStackTrace();
    return null;} 
    catch (Exception e) {return null;}
	}
	
	public static List<Spenden> get_ProjectSpendens(String projectid)
	{
		String sql =  "select s.spendenbetrag, s.sichtbarkeit, case when s.sichtbarkeit = 'privat' then 'Anonym' else b.name end as spendername "
				+ "from DBP076.SPENDEN as s "
				+ "left join DBP076.BENUTZER as b on b.email = s.spender "
				+ "where s.projekt = ? ";
			
    try{
		//Connection con = DBUtil.getConnection();
		Connection con = DBUtil.getExternalConnection();
		PreparedStatement ps =  con.prepareStatement(sql);
		ps.setInt(1, Integer.parseInt(projectid));
		ResultSet rs = ps.executeQuery();
		List<Spenden> spendens = new ArrayList<Spenden>();
		
		while (rs.next()) 
		{
			String username = rs.getString(3);
			Double spendenbetrag = rs.getDouble(1);
			String sichtbarkeit = rs.getString(2);
			
			Spenden spenden = new Spenden();
			spenden.setProjectid(projectid);
			spenden.setSichtbarkeit(sichtbarkeit);
			spenden.setSpendenbetrag(spendenbetrag);
			spenden.setUsername(username);
			
			spendens.add(spenden);
		}
		rs.close();
		ps.close();
		con.close();

		return spendens;
    } 
    catch (SQLException e)
    { e.printStackTrace();
    return null;} 
    catch (Exception e) {return null;}
	}
	
	public static List<String> get_tobedeletedKommentar(String projectid) {
	
		String sql = "Select kommentar from DBP076.SCHREIBT where projekt = ? ";
		
		try {
			Connection con = DBUtil.getExternalConnection();
			PreparedStatement ps =  con.prepareStatement(sql);
			ps.setInt(1, Integer.parseInt(projectid));
			ResultSet rs = ps.executeQuery();
			List<String> idList = new ArrayList<String>();
			
			while(rs.next())
			{
				String id = rs.getString(1);
				idList.add(id);	
			}
			
			ps.close();
			rs.close();
			con.close();

			return idList;
	    } 
	    catch (SQLException e)
	    { e.printStackTrace();
	    return null;} 
	    catch (Exception e) {return null;}
	}

	public static List<String> get_tobeUpdatedProjekt(String projectid) {
	
		String sql = "Select kennung from DBP076.PROJEKT where vorgaenger = ? ";
		
		try {
			Connection con = DBUtil.getExternalConnection();
			PreparedStatement ps =  con.prepareStatement(sql);
			ps.setInt(1, Integer.parseInt(projectid));
			ResultSet rs = ps.executeQuery();
			List<String> idList = new ArrayList<String>();
			
			while(rs.next())
			{
				String id = rs.getString(1);
				idList.add(id);	
			}
			
			ps.close();
			rs.close();
			con.close();

			return idList;
	    } 
	    catch (SQLException e)
	    { e.printStackTrace();
	    return null;} 
	    catch (Exception e) {return null;}
	}

	
	public static String delete_Project(String projectid) {
		
		//due to not on delete cascade in create table, so have to delete child table first then only master table
		//child table is the table where referencing master table primary as foreign key
		
		String refund = "BEGIN DECLARE gutschrift DECIMAL(10,2);" 
				+ "FOR r as SELECT * FROM DBP076.SPENDEN WHERE projekt = ? "
				+ " DO SET gutschrift = r.spendenbetrag; "
				+ "UPDATE DBP076.KONTO SET guthaben = guthaben + gutschrift WHERE inhaber = r.spender;"
				+ "END FOR;"
				+ "END"; //successful
		
		String deleteSchreibt = "DELETE FROM DBP076.SCHREIBT WHERE projekt = ? ";
		
		String deleteSpenden = "DELETE FROM DBP076.SPENDEN WHERE projekt = ? ";
		
		String deleteProject = "DELETE FROM DBP076.PROJEKT WHERE kennung = ? ";
		
		List<String> deleteKommentarId = get_tobedeletedKommentar(projectid);
		List<String> updateVorgNull = get_tobeUpdatedProjekt(projectid);

		
		try {
			Connection con = DBUtil.getExternalConnection();
			PreparedStatement s1 = con.prepareStatement(deleteSchreibt);
			s1.setInt(1, Integer.parseInt(projectid));
			s1.executeUpdate();		
			s1.close();
	
			if(deleteKommentarId!=null)
			{
				for(int i = 0; i<deleteKommentarId.size();i++)
				{
					String sql = "DELETE FROM DBP076.KOMMENTAR WHERE id = ? ";
					PreparedStatement s2 = con.prepareStatement(sql);
					s2 = con.prepareStatement(sql);
					s2.setInt(1, Integer.parseInt(deleteKommentarId.get(i)));
					s2.executeUpdate();
					s2.close();
				}
			}

			if(updateVorgNull!=null)
			{
				for(int i = 0; i<updateVorgNull.size();i++)
				{
					String sql = "UPDATE DBP076.PROJEKT SET vorgaenger = NULL WHERE kennung = ? ";
					PreparedStatement s3 = con.prepareStatement(sql);
					s3 = con.prepareStatement(sql);
					s3.setInt(1, Integer.parseInt(updateVorgNull.get(i)));
					s3.executeUpdate();
					s3.close();
				}
			}

			PreparedStatement s4 = con.prepareStatement(refund);
			PreparedStatement s5 = con.prepareStatement(deleteSpenden);
			PreparedStatement s6 = con.prepareStatement(deleteProject);
			s4.setInt(1, Integer.parseInt(projectid));
			s5.setInt(1, Integer.parseInt(projectid));
			s6.setInt(1, Integer.parseInt(projectid));
			s4.executeUpdate();
			s5.executeUpdate();
			s6.executeUpdate();


			s4.close();
			s5.close();
			s6.close();

			con.close();

			return "0";
	    } 
	    catch (SQLException e)
	    { e.printStackTrace();
	    return e.getMessage();} 
	    catch (Exception e) {return e.getMessage();}
	}
	
	public static String get_UserGuthaben(String useremail)
	{
		String sql = "Select guthaben from DBP076.KONTO where inhaber = ? ";
				
		try {
			Connection con = DBUtil.getExternalConnection();
			PreparedStatement ps =  con.prepareStatement(sql);
			ps.setString(1, useremail);
			ResultSet rs = ps.executeQuery();
			String guthaben = null;
			
			while(rs.next())
			{
				guthaben = rs.getString(1);
			}
			return guthaben;
		}
	    catch (SQLException e)
	    { e.printStackTrace();
	    return e.getMessage();} 
	    catch (Exception e) {return e.getMessage();}
	}

	public static String fund_Project(String email, String fund, String projectid, String anonym) {
		
		String spenden = "INSERT INTO DBP076.SPENDEN(spender, projekt, spendenbetrag, sichtbarkeit) VALUES( ? , ? , ? , ? )";
		String konto = "UPDATE DBP076.KONTO SET guthaben = guthaben - ? WHERE inhaber = ? "; //try to user transaction
	
		try {
			Connection con = DBUtil.getExternalConnection();
			PreparedStatement s1 =  con.prepareStatement(spenden);
			s1.setString(1, email);
			s1.setInt(2, Integer.parseInt(projectid));
			s1.setBigDecimal(3, new BigDecimal(fund));
			s1.setString(4, anonym);
			
			PreparedStatement s2 =  con.prepareStatement(konto);
			s2.setBigDecimal(1, new BigDecimal(fund));
			s2.setString(2, email);
			
			s1.executeUpdate();
			s2.executeUpdate();
			s1.close();
			s2.close();
			con.close();

			return "0";
	    } 
	    catch (SQLException e)
	    { e.printStackTrace();
	    return e.getMessage();} 
	    catch (Exception e) {return e.getMessage();}
	}
	
	public static List<Project> search_Project(String searchWord) throws SQLException {
		String sql =  "select p.kennung, p.titel, p.beschreibung, p.status, p.finanzierungslimit, "
				+ "k.icon, b.email, b.name, b.beschreibung,  s.actualfund, vorg.titel as vorgaenger, vorg.kennung as vorID, k.name, k.id "
				+ "from DBP076.PROJEKT as p "
				+ "inner join DBP076.BENUTZER as b on b.email = p.ersteller "
				+ "inner join DBP076.KATEGORIE as k on k.id = p.kategorie "
				+ "left join (select projekt, SUM(spendenbetrag) as actualfund from DBP076.SPENDEN group by projekt) as s on s.projekt = p.kennung "
				+ "left join DBP076.PROJEKT as vorg on vorg.kennung = p.vorgaenger "
				+ "where lower(p.titel) like ? " ;
			
    try{
		//Connection con = DBUtil.getConnection();
		Connection con = DBUtil.getExternalConnection();
		PreparedStatement ps =  con.prepareStatement(sql);
		ps.setString(1, searchWord + "%");
		ResultSet rs = ps.executeQuery();
		List<Project> projects = new ArrayList<Project>();

		while (rs.next()) 
		{
			Project project = new Project();
			User user = new User();
			
			String name = rs.getString(2); 
			String id = String.valueOf(rs.getInt(1));
			String comment = rs.getString(3);
			String status = rs.getString(4);
			Double fundlimit = rs.getDouble(5);
			String categoryIcon = rs.getString(6);
			String useremail = rs.getString(7);
			String username = rs.getString(8);
			String userprofile = rs.getString(9);
			Double actualfund = rs.getDouble(10);
			String vorgaenger = rs.getString(11);
			String vorID = rs.getString(12);
			String kategorieName = rs.getString(13);
			String kategorieID = rs.getString(14);

			Category category = new Category();
			category.setId(kategorieID);
			category.setIcon(categoryIcon);
			category.setName(kategorieName);
			
			user.setEmail(useremail);
			user.setName(username);
			user.setProfile(userprofile);
			
			project.setName(name);
			project.setId(id);
			project.setDescription(comment);
			project.setStatus(status);
			project.setFund_limit(fundlimit);
			project.setCreated_by(user);
			project.setCategory(category);
			project.setActual_fund(actualfund);
			
			Project vorg = new Project();
			vorg.setId(vorID);
			vorg.setName(vorgaenger);
			project.setVorganger(vorg);
			projects.add(project);
		}
		rs.close();
		ps.close();
		con.close();

		return projects;
    } 
    catch (SQLException e)
    { e.printStackTrace();
    return null;} 
    catch (Exception e) {return null;}

	}

}
