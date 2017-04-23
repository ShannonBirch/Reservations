<?php
		if(!isset($_COOKIE["busID"])){
			echo "<script>window.location.replace(\"signin.php\");</script>";//Browser redirect
			exit();
		}

	?>



<html>


	<head>
		<title>Your Settings</title>


		<?php require 'php_partials/navbar.php';?>
		
		<?php require 'scripts/getSettings.php';?>

		<?php include 'php_partials/footer.php';?>
	</body>




</html>