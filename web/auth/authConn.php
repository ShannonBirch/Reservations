<?php

	
	function getConn(){

		//Connection credentials
		 
		

		$conn = new mysqli($servername, $username, $password, $dbname);
					// Check connection
					if ($conn->connect_error) {
					    die("Connection failed: " . $conn->connect_error);
					}

		

		 return $conn;
	 }	

	 function getBusAuthConn(){
	 	//Connection credentials
		 
		

		$conn = new mysqli($servername, $username, $password, $dbname);
					// Check connection
					if ($conn->connect_error) {
					    die("Connection failed: " . $conn->connect_error);
					}

		return $conn;

	 }

?>