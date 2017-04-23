<?php

	include('checkAuth.php');

	if(busLoggedIn($_COOKIE["busID"], $_COOKIE["token"])){
		include('getConn.php');

		$conn = getConn();

		$getAddressSql = "SELECT Line_1, Line_2, Line_3, Line_4, Town, County, EirCode, Number
							FROM Address 
							WHERE B_Google_ID='".$_COOKIE["busID"]."';";

		$adressExists=0;

		$addressResult=$conn->query($getAddressSql);
		if($addressResult->num_rows>0){
			$addressRow=$addressResult->fetch_assoc();

			$adressExists = 1;

			$number = $addressRow["Number"];
			$line1 = $addressRow["Line_1"];
			$line2 = $addressRow["Line_2"];
			$line3 = $addressRow["Line_3"];
			$line4 = $addressRow["Line_4"];
			$town = $addressRow["Town"];
			$county = $addressRow["County"];
			$eircode = $addressRow["EirCode"];


		}			

		echo "<Div id=\"address\" style=\"text-align: center; margin-top: 50; width:500; float: left;\">
				<input type=\"text\" id=\"numberText\" value=\"".$number."\" placeholder=\"#\" style=\"width:40\"/> 
				<input type=\"text\" id=\"line1Text\" value=\"".$line1."\" placeholder=\"Line1\" style=\"width:160\" /><br/ ><br />
				<input type=\"text\" id=\"line2Text\" value=\"".$line2."\" placeholder=\"Line2\" style=\"width:200\" /><br/ ><br />
				<input type=\"text\" id=\"line3Text\" value=\"".$line3."\" placeholder=\"Line3\" style=\"width:200\" /><br/ ><br />
				<input type=\"text\" id=\"line4Text\" value=\"".$line4."\" placeholder=\"Line4\" style=\"width:200\"/><br/ ><br />
				<input type=\"text\" id=\"townText\" value=\"".$town."\" placeholder=\"Town\" style=\"width:200\"/><br/ ><br />
				<input type=\"text\" id=\"countyText\" value=\"".$county."\"placeholder=\"County\" style=\"width:100\"/>
				<input type=\"text\" id=\"eircodeText\" value=\"".$eircode."\" placeholder=\"Eircode\" style=\"width:100\"/><br/ ><br />


				<input type=\"button\" id=\"addressBttn\" value=\"Save Address\" onclick=\"saveAddress(".$adressExists.")\"  /> <br /><br /><br />
				<Div id=\"errorDiv\"></Div>
			</Div>";
		


			//echo


		$conn->close();
	}else{//Token deleted
		echo "<script>window.location.replace(\"signin.php\");</script>";//Browser redirect
		exit();

	}

?>