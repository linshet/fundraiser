<html>
<head><title>ProjectFunder</title>
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
</style>

<body>
	<div id="wrapper">
		
		   
		<div id="site">
			

		<#if user??>
		<div id="header">
		<h1> Profil von ${(user.email)!}</h1>
		</div> <!--header-->
		

		<div id="content">

		Benutzername: ${(user.name)!} <br>
		Anzahl erstellter Projekte: ${(user.projectsCreated_Count)!} <br>
		Anzahl unterstützer Projekte: ${(user.projectsFunded_Count)!}<br>

		</div> <!-- content --> 
		
		<hr>
		
		<h1>Ersteller Projekte</h1>
		
		<#if user.projectsCreated?has_content>
		<div class="grid-container">
		<#list user.projectsCreated as project1>
  		<div class="card">
  			<div class="bg-img">
  				<img src="${project1.category.icon}" alt="${project1.category.name}"  class="center">
  			</div>
 			 <div class="content">
				<h3><b><a href="/ViewProjectServlet?projectid=${project1.id}">${project1.name}</a></b></h3>
				Status: ${project1.status}<br>
				Aktuelle: ${project1.actual_fund} € 
    		</div>
  		</div>
		</#list>
		</div>
		<#else>
		This user has not created any projects.
		</#if>

		<br><br>
		
		<hr>
		
		<h1>Unterstützer Projekte</h1>
		
		<#if user.projectsFunded?has_content>
		<div class="grid-container">
		<#list user.projectsFunded as project2>
  		<div class="card">
  			<div class="bg-img">
  				<img src="${project2.category.icon}" alt="${project2.category.name}"  class="center">
  			</div>
 			 <div class="content">
				<h3><b><a href="/ViewProjectServlet?projectid=${project2.id}">${project2.name}</a></b></h3>
				Limit: ${project2.fund_limit} €<br>
				Status: ${project2.status}<br>
				Aktuelle: ${project2.actual_fund} € 
    		</div>
  		</div>
		</#list>
		</div>
		<#else>
		This user has not funded any projects.
		</#if>

		<br><br>
		<#else>
		no User found
		</#if>
		
		<br><br>
		<div id="foot"> 
		<a href="/projectFunder">Back To Homepage</a>
		</div> <!-- foot --> 
		
		
	   </div>  <!-- site -->  
	</div> <!-- wrapper--> 
</body>
</html>