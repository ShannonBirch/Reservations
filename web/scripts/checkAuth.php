<?php

	
	function loggedIn($userID, $token){

		//Connection credentials
		 
		

		$conn = new mysqli($servername, $username, $password, $dbname);
					// Check connection
					if ($conn->connect_error) {
					    die("Connection failed: " . $conn->connect_error);
					}

		$checkTokenSql = "Select User_ID FROM User_Tokens 
							WHERE User_ID='".$userID."' AND Token='".$token."';";

		$result=$conn->query($checkTokenSql);

		if($result->num_rows>0){
			 $conn->close();	
			return "True";
		}


		 $conn->close();	
		 return "False";
	 }	


	 function busLoggedIn($businessID, $token){

		 
		

		$conn = new mysqli($servername, $username, $password, $dbname);
					// Check connection
					if ($conn->connect_error) {
					    die("Connection failed: " . $conn->connect_error);
					}


		$checkTokenSql = "Select Business_ID FROM Business_Tokens 
							WHERE Business_ID='".$businessID."' AND Token='".$token."';";

		$result=$conn->query($checkTokenSql);

		if($result->num_rows>0){
			 $conn->close();	
			return "True";
		}


		 $conn->close();	
		setcookie("busID", $busID, time()-3600,"/");
		setcookie("token", $token, time()-3600,"/");

		 return "False";
	 }	

?>