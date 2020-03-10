<html>
<head>
<title>ProjectFunder</title>
</head>
<link rel="stylesheet" type="text/css" href="styles.css" />
<style type="text/css">
input[type=submit] {
  background-color: #2c5b9c;
  border: none;
  color: white;
  padding: 15px 32px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 16px;
}
</style>

<body>
	<div id="wrapper">
		
		   
		<div id="site">
			
		<div id="header">
		<h1>Informationen</h1>
		</div> <!--header-->
		
		<div id="content">
		
		<div align="center">

		<img src="${project.category.icon}" alt="${project.category.name}"  width="100" height="100"> <br>
		<h3><b>${project.name}</b></h3>
		von <a class="active" href="/ViewProfileServlet?username=${project.created_by.name}">${project.created_by.name}</a><br>
		${(project.description)!} <br><br>
		</div>
		
		Finanzierungslimit: ${project.fund_limit} €<br>
		Aktuelle Spendensumme: ${project.actual_fund} € <br>
		Status: ${project.status} <br>
		
		<#if project.vorganger.id?? && project.vorganger.name??>
		Vorgaenger-Projekt: <a href="/ViewProjectServlet?projectid=${project.vorganger.id}">${project.vorganger.name} </a><br>
		<#else>
		Vorgaenger-Projekt: keine <br>
		</#if>
		
		</div> <!-- content --> 
		
		<hr>
		
		<form action="ViewProjectServlet" method="POST">
		
		<div id="action"> 
		<h1>Aktionsleiste</h1>
		<input type="hidden" id="projectid" name="projectid" value="${project.id}">
		<input type="hidden" id="projectowner" name="projectowner" value="${project.created_by.email}">
		<table>
		<tr>
		<td><input type="submit" name="submit" value="Spenden"></td>
		<td><input type="submit" name="submit" value="Projekt Loeschen"></td>	
		<td><input type="submit" name="submit" value="Projekt Editieren"></td>
		</tr>
		</table>
		</div> <!-- action --> 
		
		<hr>
		
		<div id="Spenden">
		<h1>Liste der Spender</h1>
		<#if project.spendens?has_content>
		<#list project.spendens as spenden>
		${(spenden.username)!}:    ${(spenden.spendenbetrag)!} € <br>
		</#list>
		<#else>
		Keine Spender
		</#if>
		</div> <!-- Spenden-->
		
		<hr>
		
		<div id="kommentare">
		<h1>Kommentare</h1>
		<#if project.comments?has_content>
		<#list project.comments as comment>
		${(comment.username)!}:    ${(comment.comment)!} <br>
		</#list>
		<#else>
		Keine Kommentare
		</#if>
		<br><br>
		<input type="submit" name="submit" value="Comment">
		</div> <!-- kommentare-->

		<br><br>
		<div id="foot"> 
		<a href="/projectFunder">Back To Homepage</a>
		</div> <!-- foot --> 
		
		</form>
		
	   </div>  <!-- site -->  
	</div> <!-- wrapper--> 
</body>
</html>