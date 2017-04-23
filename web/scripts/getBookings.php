<?php

	$uID = $_GET["uid"];
	$uToken = $_GET["token"];

	$output = "<root>";

	include('checkAuth.php');
	if(loggedIn($uID, $uToken)==="True"){

		header('Content-Type: text/xml');
		include('getConn.php');

		$conn = getConn();


		$bookingSQL="SELECT re.Reservation_ID, re.Time, re.Priority, re.Bumped, 
							bu.B_Google_ID, bu.Name, bu.Description, bu.Rating, bu.Picture,
							a.Number, a.Line_1 
					FROM  Reservations re 
					INNER JOIN Business bu ON re.B_Google_ID = bu.B_Google_ID 
					INNER JOIN Address a ON bu.B_Google_ID = a.B_Google_ID 
					WHERE re.U_Google_ID='".$uID."' AND re.Dismissed=0
					ORDER BY re.Time ASC;";

		$bookingResult=$conn->query($bookingSQL);

		if($bookingResult->num_rows>0){
			while($bookingRow=$bookingResult->fetch_assoc()){

				$prioritiessql = "SELECT P_Name, Cost 
			 						FROM Priorities 
			 						WHERE B_Google_ID ='".$bookingRow["B_Google_ID"]."' 
			 						ORDER BY Priority ASC;";

			 	$favouritedCheckSQL = "SELECT B_Google_ID 
			 							FROM Favourites
			 							WHERE U_Google_ID ='".$uid."' 
			 							AND B_Google_ID='".$row["B_Google_ID"]."';";

			 	$bumped = "false";
			 	if($bookingRow["Bumped"]=="1"){
			 		$bumped = "true";
			 	}
			 							

			 	$output .= "<Booking>
			 				<ReservationID>".$bookingRow["Reservation_ID"]."</ReservationID>
			 				<Time>".$bookingRow["Time"]."</Time>
			 				<BookedPriority>".$bookingRow["Priority"]."</BookedPriority>
			 				<Bumped>".$bumped."</Bumped>
			 				<business>
			 				<ID>".$bookingRow["B_Google_ID"]."</ID>
			 				<Name>".$bookingRow["Name"]."</Name>
			 				<Description>".$bookingRow["Description"]."</Description>
			 				<Rating>".$bookingRow["Rating"]."</Rating>";

			 	$prioritiesResult = $conn->query($prioritiessql );
			 	if ($prioritiesResult ->num_rows > 0) {
					while($pRow = $prioritiesResult ->fetch_assoc()) {
					$output .="<Priority>
								<PName>".$pRow['P_Name']."</PName>
								<Cost>".$pRow['Cost']."</Cost>
								</Priority>";
					}
				}
			 	
			 	$output .="<Number>".$bookingRow["Number"]."</Number>
						 	<Line_1>".$bookingRow["Line_1"]."</Line_1>
						 	<Picture>".$bookingRow["Picture"]."</Picture>";

			 	$favouritedResult = $conn->query($favouritedCheckSQL);
			 	if($favouritedResult->num_rows > 0) {
			 		$output.="<Favourited>true</Favourited>";
			 	}else{
			 		$output.="<Favourited>false</Favourited>";
			 	}	


				$output .= "</business>
						 	</Booking>";


			}
		}


		$output .="</root>";
	}else{
		echo "Not Logged In";
	}


		
	echo $output;

?>