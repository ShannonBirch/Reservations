<?php
	header('Content-Type: text/xml');

	//ToDo: 
	//		
	//		Login tokens
	//		Check for business hours

	//Connection credentials
	

	$UID = $_GET["uid"];
			
	$output = "<root>";
	
	if(!empty($UID)){

		//Beginning of search
		
		$conn = new mysqli($servername, $username, $password, $dbname);
				// Check connection
				if ($conn->connect_error) {
				    die("Connection failed: " . $conn->connect_error);
				}

		$sql = "SELECT f.U_Google_ID, f.Order_num, b.Name, b.Description, b.Rating, b.Picture, b.B_Google_ID, a.Number, a.Line_1, a.B_Google_ID 
				FROM Favourites f 
				INNER JOIN Business b on f.B_Google_ID = b.B_Google_ID 
				INNER JOIN Address a on b.B_Google_ID = a.B_Google_ID 
				WHERE f.U_Google_ID = '".$UID."' 
				ORDER BY f.Order_num ASC;";
		$result = $conn->query($sql);
		if ($result->num_rows > 0) {
			while($row = $result->fetch_assoc()) {
			 	//Do things here
			 	$prioritiessql = "SELECT P_Name, Cost 
			 						FROM Priorities 
			 						WHERE B_Google_ID ='".$row["B_Google_ID"]."' 
			 						ORDER BY Priority ASC;";
			 	
			 	$output .= "<business>
			 				<ID>".$row["B_Google_ID"]."</ID>
			 				<Name>".$row["Name"]."</Name>
			 				<Description>".$row["Description"]."</Description>
			 				<Rating>".$row["Rating"]."</Rating>";
			 	$prioritiesResult = $conn->query($prioritiessql );
			 	if ($prioritiesResult ->num_rows > 0) {
					while($pRow = $prioritiesResult ->fetch_assoc()) {
					$output .="<Priority><PName>".$pRow['P_Name']."</PName><Cost>".$pRow['Cost']."</Cost></Priority>";
					}
				}
			 	
			 	$output .="<Number>".$row["Number"]."</Number>
			 				<Line_1>".$row["Line_1"]."</Line_1>
			 				<Picture>".$row["Picture"]."</Picture>
			 				<Favourited>true</Favourited>
			 				</business>";
		    }				    
		}	





		 $conn->close();		
		 //End of Search for Favourites

	}else{
		//Throw errors for no user ID
		//Tell them to log in 
	}
	$output .= "</root>";

	
	print($output);
?>