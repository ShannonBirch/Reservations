<?php

	

	function verify($password, $hashedPassword) {
    return crypt($password, $hashedPassword) == $hashedPassword;
}
	
	

	

	$email = $_POST["email"];
	$pass = $_POST["pass"];
		

	include('authConn.php');
	$conn = getBusAuthConn();

	$getHashedPassSQL = "SELECT  Business_ID, Pass 
							FROM Auth 
							WHERE Email ='".$email."';";



	$result = $conn->query($getHashedPassSQL);

	if($result->num_rows>0){
		$row = $result->fetch_assoc();
		if(verify($pass, $row["Pass"])){
			//Correct details
			$token = substr(md5(rand()), 0, 18);
			$makeTokenSql = "INSERT INTO Business_Tokens (Business_ID, Token) 
								VALUES ('".$row["Business_ID"]."', '".$token."');";			

	

			$conn->query($makeTokenSql);
			
			
			$busID = $row["Business_ID"];
			
			setcookie("test", "test", time()+(86400*30),"/");
			setcookie("busID", $busID, time()+(86400*30),"/");
			setcookie("token", $token, time()+(86400*30),"/");

			if($result->num_rows>0){
				$row = $result->fetch_assoc();		
				echo "Success";

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