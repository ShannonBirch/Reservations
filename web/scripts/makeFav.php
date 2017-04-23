<?php

	//Connection credentials
	



	
		
	$uid = $_POST["userID"];$token= $_POST["token"]; $bid = $_POST["bid"];
	
	include('checkAuth.php');
	if(loggedIn($uid, $token)==="True"){

		$ordNum=0;

		$conn = new mysqli($servername, $username, $password, $dbname);
				// Check connection
				if ($conn->connect_error) {
				    die("Connection failed: " . $conn->connect_error);
				}
		$checkExistingSQL = "SELECT B_Google_ID 
							FROM Favourites
							WHERE U_Google_ID='".$uid."' AND B_Google_ID='".$bid."';";

		$getOrderCount = "SELECT Order_num
						   FROM Favourites
						   WHERE U_Google_ID='".$uid."'
						   ORDER BY Order_num DESC;";

		$ordCountR = $conn->query($getOrderCount);
		if($ordCountR->num_rows>0){
			$ordNumRow = $ordCountR->fetch_assoc();
			$ordNum = $ordNumRow["Order_num"]+1;
		}




		$existsResult = $conn->query($checkExistingSQL);
		if($existsResult->num_rows>0){
			echo "Exists";
		}else{
			$insertSql =  "INSERT INTO Favourites(U_Google_ID, B_Google_ID, Order_num, Date_created) 
										VALUES ('".$uid."', '".$bid."', ".$ordNum.", '".date("Y-m-d h:i:s")."');";

			$conn->query($insertSql);
			echo "Success";

		}
		$conn->close();
	}else{
		echo "Not Logged In";
	}



?>