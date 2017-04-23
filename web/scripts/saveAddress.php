<?php

	include('checkAuth.php');

	if(busLoggedIn($_COOKIE["busID"], $_COOKIE["token"])){

		$number = $_POST["number"]; $line1 = $_POST["line1"]; 
		$line2 = $_POST["line2"]; $line3 = $_POST["line3"];
		$line4 = $_POST["line4"]; $town =$_POST["town"]; 
		$county = $_POST["county"]; $eircode = $_POST["eircode"];
		$exists = $_POST["exists"];

		include('getConn.php');
		$conn = getConn();

		$insertSql = "INSERT INTO Address
						(B_Google_ID, Line_1, Line_2, Line_3, Line_4, Town, County, EirCode, Number)
						VALUES 
						('".$_COOKIE["busID"]."','".$line1."','".$line2."', '".$line3."','".$line4."',
						'".$town."','".$county."','".$eircode."','".$number."');";

		$updateSql = "UPDATE Address
						SET Line_1='".$line1."'
							Line_2='".$line2."'
							Line_3='".$line3."'
							Line_4='".$line4."'
							Town 	='".$town."'
							County 	='".$county."'
							EirCode ='".$eircode."'
							Number ='".$number."'
						WHERE B_Google_ID='".$_COOKIE["busID"]."';";


		if($exists==1){
			$conn->query($updateSql);

		}else{
			$conn->query($insertSql);
			echo "here\n";
		}

		$conn->close();
		echo $insertSql;

		exit();
	}else{
		echo "Not Logged in";
	}

?>