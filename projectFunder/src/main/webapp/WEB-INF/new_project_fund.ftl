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
			
		<div align="center" id="header">
		<h1>${project.name}</h1>
		</div> <!--header-->
		
		<div align="center" id="content">
		<form action="NewFundServlet" method="POST">
		<input type="hidden" id="projectid" name="projectid" value=${project.id}>
		Spendenbetrag: <input type="number" name="spendenbetrag" placeholder="100.00" step="0.01" min="0.01" max="999999999.99" required> €<br>
		<input type="checkbox" name="anonym" value="anonym">Anonym spenden?<br><br>
		<input type="submit" name="submit" value="confirm spenden">    	
		</form>
		<br><br>
		<a href="/ViewProjectServlet?projectid=${project.id}">Back To Project</a>
		
		<br><br>
		${(message)!}
		</div> <!-- content --> 
		

		<div id="foot"> 
		</div> <!-- foot --> 
		
		
	   </div>  <!-- site -->  
	</div> <!-- wrapper--> 
</body>
</html>