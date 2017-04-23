<?php

	

	function generateHash($password) {
	    if (defined("CRYPT_BLOWFISH") && CRYPT_BLOWFISH) {
	        $salt = '$2y$11$' . substr(md5(uniqid(rand(), true)), 0, 22);
	        return crypt($password, $salt);
	    }
	}


	//Connection credentials
	

	//Todo 
	//		Check that user id is unique
	

	

	$user = $_POST["username"]; $pass1 = $_POST["pass1"]; $pass2 = $_POST["pass2"];
	$FName = $_POST["fname"]; $LName = $_POST["lname"]; $email = $_POST["email"];



	if($pass1==$pass2){
		$pass2=null;
		$hPass = generateHash($pass1);
		$pass1=null;
	}


	$uid = substr(md5(rand()), 0, 18);

	$conn = new mysqli($servername, $username, $password, $dbname);
				// Check connection
				if ($conn->connect_error) {
				    die("Connection failed: " . $conn->connect_error);
				}

	$checkUsername ="SELECT Username 
					FROM Auth
					WHERE Username='".$user."';";

	$userCheckResult = $conn->query($checkUsername);
	if($userCheckResult->num_rows>0){

		echo "Username already exists";

	}else{


		$registerAuthSQL = "INSERT INTO Auth(Username, Pass, User_ID) 
							VALUES ('".$user."', '".$hPass."', '".$uid."');";



		$conn->query($registerAuthSQL);



		 $conn->close();	

 



		 $conn = new mysqli($servername, $username, $password, $dbname);
					// Check connection
					if ($conn->connect_error) {
					    die("Connection failed: " . $conn->connect_error);
					}	

		$registerMainDBSQL = "INSERT INTO Users (U_Google_ID, F_Name, L_Name, email) 
								VALUES ('".$uid."', '".$FName."', '".$LName."', '".$email."')";


		$conn->query($registerMainDBSQL);

		echo "Success";

	}

	 $conn->close();	

?>