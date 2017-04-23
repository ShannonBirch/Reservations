<?php

	


	//Connection credentials
	

	//Todo 
	//		register in other db too
	//		Give auth token on first register
	//		Check not already registered
	//      Do actual registration things
	

	

	$userID = $_POST["userID"];
	$token = $_POST["token"];
	

	$conn = new mysqli($servername, $username, $password, $dbname);
				// Check connection
				if ($conn->connect_error) {
				    die("Connection failed: " . $conn->connect_error);
				}

	$deleteTokenSql = "DELETE FROM User_Tokens WHERE User_ID='".$userID."' AND Token='".$token."';";

	$conn->query($deleteTokenSql);

	echo "Success\n";

	echo $deleteTokenSql;

	

	 $conn->close();		

?>