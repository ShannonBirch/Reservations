<?php

	
	
	

	include('authConn.php');
	$conn = getBusAuthConn();

	$busID = $_COOKIE["busID"]; $token=$_COOKIE["token"]; 
	$deleteTokenSql = "DELETE FROM Business_Tokens WHERE Business_ID='".$busID."' AND Token='".$token."';";

	$conn->query($deleteTokenSql);



	setcookie("busID", $busID, time()-3600,"/");
	setcookie("token", $token, time()-3600,"/");

	echo "Success";

	 $conn->close();		

?>