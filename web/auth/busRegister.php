<?php

	

	function generateHash($password) {
	    if (defined("CRYPT_BLOWFISH") && CRYPT_BLOWFISH) {
	        $salt = '$2y$11$' . substr(md5(uniqid(rand(), true)), 0, 22);
	        return crypt($password, $salt);
	    }
	}


	
	

	

	$email=$_POST["email"]; $pass=$_POST["pass"]; $name=$_POST["name"];





	$bid = substr(md5(rand()), 0, 18);
	$hPass = generateHash($pass);
	$pass = null;

	include('authConn.php');
	$conn = getBusAuthConn();

	$checkEmail ="SELECT Email 
					FROM Auth
					WHERE Email='".$email."';";

	$emailCheckResult = $conn->query($checkEmail);
	if($emailCheckResult->num_rows>0){

		echo "Email Already Exists";

	}else{


		$registerAuthSQL = "INSERT INTO Auth(Email, Pass, Business_ID) 
							VALUES ('".$email."', '".$hPass."', '".$bid."');";




		$conn->query($registerAuthSQL);



		 $conn->close();	


		$conn = getConn();

		$registerMainDBSQL = "INSERT INTO Business (B_Google_ID, Name, Type, Picture, Description, Max_reservations) 
								VALUES ('".$bid."', '".$name."', 'NOT SET', 'http://appointments.shannonbirch.com/busImages/noimagefound.jpg', 'NOT SET', 1)";

		$initalPrioritySQL = "INSERT INTO Priorities
								(P_Name, Priority, Cost, B_Google_ID)
								VALUES
								('Free', 0, 0,'".$bid."');";



		$conn->query($registerMainDBSQL);
		$conn->query($initalPrioritySQL);

		echo "Success";

	}

	 $conn->close();	

?>