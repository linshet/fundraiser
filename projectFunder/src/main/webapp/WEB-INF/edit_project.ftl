<html>
<head>
<title>ProjectFunder</title>
</head>

<style type="text/css">
* {
   margin:0;
   padding:0;
}


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
		<h1> Projekte editieren</h1>
		</div> <!--header-->
		<div id="content">
		<#if project??>
		<form action="EditProjectServlet" method="POST">
		<input type="hidden" id="projectid" name="projectid" value="${project.id}">
		<table>
		<tr><td>Titel</td><td><input type="text" name="titel" maxlength="30" size="50" value="${project.name}" required></td></tr>
		<tr><td>Finanzierungslimit</td><td><input type="number" name="fundlimit" placeholder="1000.0" step="0.01" min="${project.fund_limit}" max="99999999.99" value="${project.fund_limit}" required> €</td></tr>
			<tr></tr>
		<tr><td>Kategorie</td><td><input type="radio" name="category" value="3" <#if project.category.id=="3">checked</#if> required> Education  <br>
<input type="radio" name="category" value="1" <#if project.category.id=="1">checked</#if>> Health & Wellness<br>  
<input type="radio" name="category" value="2" <#if project.category.id=="2">checked</#if>> Art & Creative Works <br>   
<input type="radio" name="category" value="4" <#if project.category.id=="4">checked</#if>> Tech & Innovation</td></tr>
		<tr></tr>
		

		<tr>
		<td>Vorgaenger</td>
		<td>
		<#if user.projectsCreated?has_content>
		<#list user.projectsCreated as vorg>
		<input type="radio" name="vorgaenger" value="${vorg.id}" <#if project.vorganger.id??><#if project.vorganger.id == vorg.id>checked</#if></#if>> ${vorg.name} <br>
		</#list>
		</#if>
		<input type="radio" name="vorgaenger" value="keinVorgaenger"  <#if project.vorganger.id??><#else>checked</#if> required> kein vorgaenger <br>
		</td>
		</tr>

		
		<tr><td>Beschreibung</td><td><textarea name="comment" cols="40" rows="5"><#if project.description??>${project.description}</#if></textarea></td></tr>
		</table>
		<input type="submit" name="submit" value="Aktualisieren"><br><br>	
		<a href="/ViewProjectServlet?projectid=${project.id}">Back To Project</a>
		</form>
		</#if>
		</div> <!-- content --> 
		
		<div id="foot"> 

		</div> <!-- foot --> 
		
		
	   </div>  <!-- site -->  
	</div> <!-- wrapper--> 
</body>
</html>