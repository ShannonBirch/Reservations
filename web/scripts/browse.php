<?php
	

	//ToDo: 
	//		
	//		Login tokens
	//		Check for business hours

	//Connection credentials
	

	$uid = $_GET["uid"];
	$token = $_GET["token"];
			
	
	

	include('checkAuth.php');
	if(loggedIn($uid, $token)==="True"){

		$output = "<root>";
		header('Content-Type: text/xml');

		//Beginning of search
		

		$conn = new mysqli($servername, $username, $password, $dbname);
				// Check connection
				if ($conn->connect_error) {
				    die("Connection failed: " . $conn->connect_error);
				}

		$sql = "SELECT  b.Name, b.Description, b.Rating, b.Picture, b.B_Google_ID, a.Number, a.Line_1
				FROM Business b 
				INNER JOIN Address a on b.B_Google_ID = a.B_Google_ID";

		$result = $conn->query($sql);
		if ($result->num_rows > 0) {
			while($row = $result->fetch_assoc()) {
			 	//Do things here
			 	$prioritiessql = "SELECT P_Name, Cost 
			 						FROM Priorities 
			 						WHERE B_Google_ID ='".$row["B_Google_ID"]."' 
			 						ORDER BY Priority ASC;";

			 	$favouritedCheckSQL = "SELECT B_Google_ID 
			 							FROM Favourites
			 							WHERE U_Google_ID ='".$uid."' 
			 							AND B_Google_ID='".$row["B_Google_ID"]."';";
			 	
			 	$output .= "<business>
			 				<ID>".$row["B_Google_ID"]."</ID>
			 				<Name>".$row["Name"]."</Name>
			 				<Description>".$row["Description"]."</Description>
			 				<Rating>".$row["Rating"]."</Rating>";

			 	$prioritiesResult = $conn->query($prioritiessql);
			 	if ($prioritiesResult ->num_rows > 0) {
					while($pRow = $prioritiesResult ->fetch_assoc()) {
					$output .="<Priority><PName>".$pRow['P_Name']."</PName>
								<Cost>".$pRow['Cost']."</Cost>
								</Priority>";
					}
				}
			 	
			 	$output .="<Number>".$row["Number"]."</Number>
			 				<Line_1>".$row["Line_1"]."</Line_1>
			 				<Picture>".$row["Picture"]."</Picture>";
 				
			 	$favouritedResult = $conn->query($favouritedCheckSQL);
			 	if($favouritedResult->num_rows > 0) {
			 		$output.="<Favourited>true</Favourited>";
			 	}else{
			 		$output.="<Favourited>false</Favourited>";
			 	}	

			 	$output	.=	"</business>";
		    }				    
		}	



		 $conn->close();		
		 //End of Search for Favourites	
		
		 $output .= "</root>";
		print($output);
	}else{
		echo "Not logged in";
		echo loggedIn($uid, $token);
	}

	
?>