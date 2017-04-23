<?php


		
	$uid = $_POST["userID"];$token= $_POST["token"]; $resID = $_POST["resID"];
	
	include('checkAuth.php');
	if(loggedIn($uid, $token)==="True"){

		$ordNum=0;

		include('getConn.php');
		$conn = getConn();


		$dismissSQL = "UPDATE Reservations
						SET Dismissed=1
						WHERE U_Google_ID='".$uid."' AND Reservation_ID=".$resID.";";

		$conn->query($dismissSQL);



		$conn->close();
		echo "Success";
	}else{
		echo "Not Logged In";
	}



?>