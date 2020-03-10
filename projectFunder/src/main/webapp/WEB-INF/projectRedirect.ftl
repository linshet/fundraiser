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
		<h1>Message</h1>
		</div> <!--header-->
		<br><br>
		<div id="content">
		${(message)!}
		</div> <!-- content --> 
		<br><br>
		<div id="foot"> 
			<form action="projectFunder" method="GET">
			<input type="submit" name="submit" value="Homepage">   
			</form>
			<br><br>
			<#if projectid??><a href="/ViewProjectServlet?projectid=${(projectid)!}">Go Back To Project</a></#if>
		</div> <!-- foot --> 
		
		
	   </div>  <!-- site -->  
	</div> <!-- wrapper--> 
</body>
</html>