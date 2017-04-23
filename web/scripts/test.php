<?php

//Connection credentials
	

	//Todo 
	//		register in other db too
	//		Give auth token on first register
	//		Check not already registered
	//      Do actual registration things
	

	

	$user = $_POST["username"];
	$pass = $_POST["pass"];
	$fbaseToken = $_POST["fbaseToken"];
	

	$conn = new mysqli($servername, $username, $password, $dbname);
				// Check connection
				if ($conn->connect_error) {
				    die("Connection failed: " . $conn->connect_error);
				}

	$findSql = "SELECT Firebase_token FROM User_Tokens WHERE User_ID='1589357732177d6595';";

	$result = $conn->query($findSql);

	if($result->num_rows>0){
		$row = $result->fetch_assoc();
		


		



				#API access key from Google API's Console
		    define( 'API_ACCESS_KEY', ' ' );
		    $registrationIds = $row["Firebase_token"];
		#prep the bundle
		     $msg = array
		          (
				'body' 	=> 'I got this shit to work',
				'title'	=> 'I\'m so glad',
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
		#Echo Result Of FireBase Server
		echo $result;

	}




?>