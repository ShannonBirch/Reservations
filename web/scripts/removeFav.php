<?php

	
		
	$uid = $_POST["userID"];$token= $_POST["token"]; $bid = $_POST["bid"];
	
	include('checkAuth.php');
	if(loggedIn($uid, $token)==="True"){

		$ordNum=0;
		include('getConn.php');
		$conn = getConn();

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

			$deleteSQL =  "DELETE FROM Favourites
							WHERE U_Google_ID='".$uid."' AND B_Google_ID='".$bid."';";

			$conn->query($deleteSQL);
			echo "Success";

			
		}else{
			echo "Doesn't Exist";
		}
		$conn->close();
	}else{
		echo "Not Logged In";
	}



?>