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
		<h1> Projekte erstellen</h1>
		</div> <!--header-->
		<div id="content">
		<form action="NewProjectServlet" method="POST">
		<table>
		<tr><td>Titel</td><td><input type="text" name="titel" maxlength="30" size="50" required></td></tr>
		<tr><td>Finanzierungslimit</td><td><input type="number" name="fundlimit" placeholder="1000.00" step="0.01" min="100" max="99999999.99" required> €</td></tr>
			<tr></tr>
		<tr><td>Kategorie</td><td><input type="radio" name="category" value="3" required> Education  <br>
<input type="radio" name="category" value="1"> Health & Wellness<br>  
<input type="radio" name="category" value="2"> Art & Creative Works <br>   
<input type="radio" name="category" value="4"> Tech & Innovation</td></tr>
		<tr></tr>
		

		<tr>
		<td>Vorgaenger</td>
		<td>
		<#if user.projectsCreated?has_content>
		<#list user.projectsCreated as project>
		<input type="radio" name="vorgaenger" value="${project.id}"> ${project.name} <br>
		</#list>
		</#if>
		<input type="radio" name="vorgaenger" value="-1" required> kein vorgaenger <br>
		</td>
		</tr>

		
		<tr><td>Beschreibung</td><td><textarea name="comment" cols="40" rows="5"></textarea></td></tr>
		</table>
		<input type="submit" name="submit" value="Erstellen">   		
		</form>
		<br><br>
		<a href="/projectFunder">Back To Homepage</a>
		</div> <!-- content --> 
		
		<div id="foot"> 
	
		</div> <!-- foot --> 
		
		
	   </div>  <!-- site -->  
	</div> <!-- wrapper--> 
</body>
</html>