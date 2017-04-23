<?php


		
	$uid = $_POST["userID"];$token= $_POST["token"]; $resID = $_POST["resID"];
	
	include('checkAuth.php');
	if(loggedIn($uid, $token)==="True"){

		$ordNum=0;

		include('getConn.php');
		$conn = getConn();


		$deleteSQL =  "DELETE FROM Reservations
							WHERE U_Google_ID='".$uid."' AND Reservation_ID='".$resID."';";

		$conn->query($deleteSQL);
			

		$conn->close();
		echo "Success";
	}else{
		echo "Not Logged In";
	}



?>