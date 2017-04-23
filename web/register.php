<html>
	<head>
		
		<title> Project</title>
		

		<?php require 'php_partials/navbar.php';?>
		
		<Div id="login" style="text-align: center; margin-top: 150">
		<input type="text" id="emailText" placeholder="email" /><br /><br />
		<input type="password" id="passText" placeholder="Password" /><br /><br />
		<input type="password" id="pass2Text" placeholder="Repeat Password" /><br /><br />
		<input type="text" id="nameText" placeholder="Business Name" /><br /><br />


		<input type="button" id="registerBttn" value="Register"  /> <br /><br /><br />
		<a href="signin.php"><button>I already have an account</button></a><br /><br />
		<Div id="errorDiv"></Div>
		</Div>
		
		<?php include 'php_partials/footer.php';?>
	</body>




</html>