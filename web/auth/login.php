<?php

	

	function verify($password, $hashedPassword) {
    return crypt($password, $hashedPassword) == $hashedPassword;
}


	//Connection credentials
	

	//Todo 
	//		register in other db too
	//		Give auth token on first register
	//		Check not already registered
	//      Do actual registration things
	

	

	$user = $_POST["username"];
	$pass = $_POST["pass"];
	$fbaseToken = $_POST["fbaseToken"];
	

	$conn = new mysqli($servername, $username, $password, $dbname);
				// Check connection
				if ($conn->connect_error) {
				    die("Connection failed: " . $conn->connect_error);
				}

	$getHashedPassSQL = "SELECT  Pass, User_ID 
							FROM Auth 
							WHERE Username ='".$user."';";



	$result = $conn->query($getHashedPassSQL);

	if($result->num_rows>0){
		$row = $result->fetch_assoc();
		if(verify($pass, $row["Pass"])){
			//Correct details
			$token = substr(md5(rand()), 0, 18);
			$makeTokenSql = "INSERT INTO User_Tokens (User_ID, Token) 
								VALUES ('".$row["User_ID"]."', '".$token."');";

			$updateFirebaseSQL = "UPDATE Auth 
									SET Firebase_Token ='".$fbaseToken."' 
									WHERE User_ID='".$row["User_ID"]."';";
			

	

			$conn->query($makeTokenSql);
			$conn->query($updateFirebaseSQL);
			
			$userID = $row["User_ID"];

			$conn->close();
			//Connection credentials
			 
			

			$conn = new mysqli($servername, $username, $password, $dbname);
						// Check connection
						if ($conn->connect_error) {
						    die("Connection failed: " . $conn->connect_error);
						}			

			 
			 $getInfoSql = "SELECT F_Name, L_Name, email
			 				FROM Users
			 				WHERE U_Google_ID ='".$userID."';";

			 $result = $conn->query($getInfoSql);

			if($result->num_rows>0){
				$row = $result->fetch_assoc();		
				echo "Success\n".$userID."\n".$token."\n".$row["F_Name"]."\n".$row["L_Name"]."\n".$row["email"];

			}else{//Not Registered
				echo "Not Registered";
			}

		}else{
			//Incorrect details
			echo "Incorrect details";
		}

	}else{
		//No user found
		echo "No User Found";

	}


	 $conn->close();		

?>