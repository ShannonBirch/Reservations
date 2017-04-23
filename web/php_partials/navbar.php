<?php
	echo "

		<script src=\"/js/jquery/jquery-3.2.1.min.js\"></script>
		<!--Bootstrap-->
		<link href = \"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\" rel = \"stylesheet\">
		<script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js\"></script>

		<!--My Javascript-->
		<script src=\"/js/javascript.js\"></script>


		<!--My CSS-->
		<link href = \"css/site.css\" rel = \"stylesheet\">

		</head>	

		<!-- START OF NAVBAR -->
		<div class = \"navbar navbar-inverse navbar-static-top\">

			
			<div class = \"container\">
				<div class=\"navbar-header\"><a href=\"index.php\" class=\"navbar-brand\">Appointments</a>
						<button class=\"navbar-toggle\" data-toggle=\"collapse\" data-target=\".navHeaderCollapse\">
							<span class=\"icon-bar\"></span>
							<span class=\"icon-bar\"></span>
							<span class=\"icon-bar\"></span>
						</button>
					</div>
					
					<div class = \"collapse navbar-collapse navHeaderCollapse\">
						<ul class = \"nav navbar-nav navbar-right\">
							<li><a href = \"index.php\"> Home </a></li>
							<li><a href = \"settings.php\">Settings</a><li>
							<li><a href = \"reservations.php\"> Reservations </a><li>";

					if(!isset($_COOKIE["busID"])){
						echo "<li><a href = \"signin.php\"> Sign in </a><li>";
					}else{
						
						echo "<li><a href=\"#\" onclick=\"signOut()\">Sign out</a></li>";

					}


	echo					"	
						</ul>
					</div>	
			</div>
		</div>	
		<!-- END OF NAVBAR -->";

		
?>