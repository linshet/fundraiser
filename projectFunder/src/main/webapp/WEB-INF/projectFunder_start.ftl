<html>
<head><title>ProjectFunder</title></head>
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

.center {
  display: block;
  margin-left: auto;
  margin-right: auto;
  width: 50%;
}

.grid-container {
   display: grid;
   grid-template-columns:repeat(3, 300px);
   grid-gap: 20px;

}

.card {
  border: 1px solid #d3d3d3;
  border-radius: .25rem;
}

.bg-img {
  background-size: cover;
  min-height: 180px;
  background-position: center;
  background-color: #FFFFFF ;
}

.content {
  padding: 15px;
}


body{
   text-align:center;
   background: #efe4bf none repeat scroll 0 0;
     font-family: Arial, Helvetica, sans-serif;
}

#wrapper{
   width:960px;
   margin:0 auto;
   text-align:left;
   background-color: #fff;
   border-radius: 0 0 10px 10px;
   padding: 20px;
   box-shadow: 1px -2px 14px rgba(0, 0, 0, 0.4);
}

#header{
 color: #fff;
 background-color: #2c5b9c;
 height: 3.5em;
 padding: 1em 0em 1em 1em;
 
}

#site{
    background-color: #fff;
    padding: 20px 0px 0px 0px;
}
.centerBlock{
	margin:0 auto;
}
</style>

<body>
	<div id="wrapper">
		<div id="header">
		<h1> ProjectFunder Website </h1>
		</div>
	   
		<div id="site">
		<p>
			Die Datenbank funder ist ${db2exists}
		</p>
		
		<br><br>
		
		<form action="ViewProfileServlet" method="POST">
		<input type="submit" name="submit" value="Mein Profil">
		</form>
		
		<p align="right"><a href="/SearchProjectServlet">Search Project</a></p>
		
		${(message)!} <!--Show message if there is no projects in database-->
		
		<br><br>
		
		<h1>Open Projects</h1>
		
		${(EmptyOpenProjects)!}
		
		<#if openProjects?has_content>
		<div class="grid-container">
		<#list openProjects as project1>
  		<div class="card">
  			<div class="bg-img">
  				<img src="${project1.category.icon}" alt="${project1.category.name}"  class="center">
  			</div>
 			 <div class="content">
				<h3><b><a href="/ViewProjectServlet?projectid=${project1.id}">${project1.name}</a></b></h3>
				von: <a class="active" href="/ViewProfileServlet?username=${project1.created_by.name}">${project1.created_by.name}</a><br>
				Aktuelle: ${project1.actual_fund} € 
    		</div>
  		</div>
		</#list>
		</div>
		</#if>

		<br><br>

		<h1>Closed Projects</h1>
		
		${(EmptyClosedProjects)!}
		
		<#if closedProjects?has_content>
		<div class="grid-container">
		<#list closedProjects as project2>
  		<div class="card">
  			<div class="bg-img">
  				<img src="${project2.category.icon}" alt="${project2.category.name}"  class="center">
  			</div>
 			 <div class="content">
				<h3><b><a href="/ViewProjectServlet?projectid=${project2.id}">${project2.name}</a></b></h3>
				von: <a class="active" href="/ViewProfileServlet?username=${project2.created_by.name}">${project2.created_by.name}</a><br>
				Aktuelle: ${project2.actual_fund} € 
    		</div>
  		</div>
		</#list>
		</div>
		</#if>
		
		<br><br>
		
		<form action="NewProjectServlet" method="POST">
		<input type="submit" name="submit" value="Projekt erstellen">
		</form>
		
		</div>
	</div>
</body>
</html>
