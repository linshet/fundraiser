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
		<#if project.id?? && project.name??>
		<div align="center" id="header">
		<h1>${project.name}</h1>
		</div> <!--header-->
		
		<div align="center" id="content">
		<form action="NewCommentServlet" method="POST">
		<input type="hidden" id="projectid" name="projectid" value=${project.id}>
		<textarea name="comment" placeholder="Schreibe Kommentar..." cols="40" rows="5" minlength="1" required></textarea><br>
		<input type="checkbox" name="anonym" value="anonym">Anonym kommentieren?<br><br>
		<input type="submit" name="submit" value="Kommentar hinzufuegen">    		
		</form>
		
		<br><br>
		<a href="/ViewProjectServlet?projectid=${project.id}">Back To Project</a>
		
		</div> <!-- content --> 
		<#else>
		<p>No project found!</p>
		</#if>

		<div id="foot"> 

		</div> <!-- foot --> 
		

	   </div>  <!-- site -->  
	</div> <!-- wrapper--> 
</body>
</html>