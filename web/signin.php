<html>
	<head>
		
		<title> Project</title>
		
		
		
	
		<?php require 'php_partials/navbar.php';?>
		
		<Div id="login" style="text-align: center; margin-top: 150">
		<input type="text" id="emailText" placeholder="email" /><br /><br />
		<input type="password" id="passText" placeholder="Password" /><br /><br />
		<input type="button" id="loginButton" value="Login" /> <br /><br /><br />
		<a href="register.php"><button>Register</button></a>
		<Div id="errorDiv"></Div>
		</Div>
		
		<?php include 'php_partials/footer.php';?>
	</body>




</html>