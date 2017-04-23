<?php



	//Todo
		/*
			Convert Variables
			SQL Code to insert a row of booking
			SQL Code to retrieve that bookings id
			Return Booking ID
			Stop users double booking
			Make certain that it returns the correct reservation_ID

		*/
	$uID = $_POST["uid"];$uToken= $_POST["userToken"]; $bID = $_POST["bid"];
	$year = $_POST["year"]; $month = $_POST["month"]; $day= $_POST["day"];

	$hour= $_POST["hour"];$minute= $_POST["minute"];$priority= $_POST["priority"];

	$month++; //Quick Hacks should probably change if showing to possible employers
	if($month <10){$month = "0".$month;}
	if($minute<30){$minute = "00";}else{$minute = 30;}

	if($hour<10){$hour = "0".$hour;}
	

	$time = $year."-".$month."-".$day." ".$hour.":".$minute.":00";
	


	$insertSql =  "INSERT INTO Reservations(B_Google_ID, U_Google_ID, Time, Priority) 
						VALUES ('".$bID."', '".$uID."', '".$time."', ".$priority.")";


	$getResIDSql = "SELECT Reservation_ID 
					FROM Reservations 
					WHERE B_Google_ID='".$bID."' AND U_Google_ID='".$uID."' AND Time = '".$time."'";

	

	include('checkAuth.php');
	if(loggedIn($uID, $uToken)==="True"){

		include('getConn.php');
		$conn = getConn();

		$getMaxReservations = "SELECT Max_Reservations 
								FROM Business 
								WHERE B_Google_ID='".$bID."';";

		$maxResResult = $conn->query($getMaxReservations);

		date_default_timezone_set('UTC');
		if($maxResResult->num_rows>0){
			$row=$maxResResult->fetch_assoc();
			$maxResNum = $row["Max_Reservations"];


			$countSQL = "SELECT COUNT(*) as resCount 
						FROM Reservations 
						WHERE TIME = '".$time."' AND B_Google_ID='".$bID."' AND bumped=0;";

			$countResult = $conn->query($countSQL);
			$rowCount=$countResult->fetch_assoc();
			$count = $rowCount["resCount"];
			if(!$count){$count=0;}//Initialise if there's no reservations

			if($count < $maxResNum){

					$conn->query($insertSql);

		$result = $conn->query($getResIDSql);
			if ($result->num_rows > 0) {
				$row = $result->fetch_assoc();

					

					echo "Success\n";
					echo $row["Reservation_ID"];
				}else{
					echo "Booking not made";
				}

			}else if(strtotime($time)>(time()+21600)){//Fully booked but possible that there's lower priorities

				$getLowerSql = "SELECT Reservation_ID, U_Google_ID 
								FROM Reservations 
								WHERE TIME = '".$time."' AND B_Google_ID='".$bID."' 
								AND Priority <".$priority." AND Bumped=0
								ORDER BY date_made ASC;";

				$lowerResult= $conn->query($getLowerSql);
				if($lowerResult->num_rows>0){
					$lowerResultRow=$lowerResult->fetch_assoc();

					$bumpSql = "UPDATE Reservations
								SET Bumped=1
								WHERE Reservation_ID='".$lowerResultRow["Reservation_ID"]."';";

					$getFbaseTokenSql = "SELECT Firebase_Token 
										FROM Auth
										WHERE User_ID ='".$lowerResultRow["U_Google_ID"]."';";

					$conn->query($bumpSql);

					$authConn = getAuthConn();

					$fbaseResult = $authConn->query($getFbaseTokenSql);
					
					$fbaseRow=$fbaseResult->fetch_assoc();

					$messageBody = "Your booking at \n".$time."\n has been taken by a higher priority booking\n You should make another one or rebook with a higher priority";

					sendMsg($fbaseRow["Firebase_Token"], $messageBody);

		
					$conn->query($insertSql);

					$result = $conn->query($getResIDSql);
						if ($result->num_rows > 0) {
							$row = $result->fetch_assoc();

								

								echo "Success\n";
								echo $row["Reservation_ID"];
							}else{
								echo "Booking not made";
							}

						


				}else{
					echo "Fully Booked";
				}


			}else{
				echo "Fully Booked";
			}
			
			
		}else{//No Business of this type exists
			echo "No Business";
		}
		$conn->close();
	}else{
		echo "Not logged in";
	}






	function sendMsg($uFbaseToken, $bookingInfo){

						#API access key from Google API's Console
		    define( 'API_ACCESS_KEY', ' ' );
		    $registrationIds = $uFbaseToken;
		#prep the bundle
		     $msg = array
		          (
				'body' 	=> $bookingInfo,
				'title'	=> 'Sorry, your booking has been bumped',
		             	'icon'	=> 'myicon',/*Default Icon*/
		              	'sound' => 'mySound'/*Default sound*/
		          );
			$fields = array
					(
						'to'		=> $registrationIds,
						'notification'	=> $msg
					);
			
			
			$headers = array
					(
						'Authorization: key=' . API_ACCESS_KEY,
						'Content-Type: application/json'
					);
		#Send Reponse To FireBase Server	
				$ch = curl_init();
				curl_setopt( $ch,CURLOPT_URL, 'https://fcm.googleapis.com/fcm/send' );
				curl_setopt( $ch,CURLOPT_POST, true );
				curl_setopt( $ch,CURLOPT_HTTPHEADER, $headers );
				curl_setopt( $ch,CURLOPT_RETURNTRANSFER, true );
				curl_setopt( $ch,CURLOPT_SSL_VERIFYPEER, false );
				curl_setopt( $ch,CURLOPT_POSTFIELDS, json_encode( $fields ) );
				$result = curl_exec($ch );
				curl_close( $ch );
		
	}


		

		
	








?>