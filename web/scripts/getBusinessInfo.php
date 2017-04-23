<?php
	header('Content-Type: text/xml');

	//ToDo: 
	//		Point android app at this page

	//Create Connection
	

	$BID = $_GET["bid"];
			
	$output = "<root>";
	
	if(!empty($BID)){

		//Beginning of search
		
		$conn = new mysqli($servername, $username, $password, $dbname);
				// Check connection
				if ($conn->connect_error) {
				    die("Connection failed: " . $conn->connect_error);
				}
		
		$sql = "SELECT b.B_Google_ID, b.Name, b.Description, b.Rating, b.Picture, a.Number, a.Line_1  FROM Business b INNER JOIN Address a ON  b.B_Google_ID = a.B_Google_ID WHERE b.B_Google_ID = '$BID'";
		//echo $sql;
		$result = $conn->query($sql);
		if ($result->num_rows > 0) {
			while($row = $result->fetch_assoc()) {
			 	//Do things here
			 	$prioritiessql = "SELECT P_Name, Cost FROM Priorities WHERE B_Google_ID ='".$row["B_Google_ID"]."' ORDER BY Priority ASC;";
			 	
			 	$output .= "<business><ID>".$row["B_Google_ID"]."</ID><Name>".$row["Name"]."</Name>";
			 	$prioritiesResult = $conn->query($prioritiessql );
			 	if ($prioritiesResult ->num_rows > 0) {
					while($pRow = $prioritiesResult ->fetch_assoc()) {
					$output .="<Priority><PName>".$pRow['P_Name']."</PName><Cost>".$pRow['Cost']."</Cost></Priority>";
					}
				}
			 	
			 	$output .="<Number>".$row["Number"]."</Number><Line_1>".$row["Line_1"]."</Line_1><Picture>".$row["Picture"]."</Picture></business>";
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