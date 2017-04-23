<?php
	
	// Create connection
				
		echo"<table id = \"display\" class=\"table table-striped\">
						<h2>Reservations</h2>

					<thead>
						<tr>
							<th>Time</th>
							<th>First Name</th>
				     			<th>Last Name</th>
				     			
						</tr>
					</thead>
				<tbody>";
		
		include('getConn.php');
		$conn = getConn();

		date_default_timezone_set('GMT-1');

		$currTime = date("y-m-d H:i:s");

		//ToDO clean up
		$sql = "Select u.F_Name, u.L_Name, r.Time, r.Priority 
				FROM Reservations r INNER JOIN Users u ON r.U_Google_ID = u.U_Google_ID
				WHERE r.B_Google_ID='".$_COOKIE["busID"]."' 
				AND r.time >='".$currTime."'
				AND r.bumped=0
				ORDER BY r.Time ASC;";
		$result = $conn->query($sql);
		if ($result->num_rows > 0) {
			while($r = $result->fetch_assoc()) {
			echo "<tr><td>".$r["Time"],"   "."</td>
				<td>".$r["F_Name"],"   "."</td>
				<td>".$r["L_Name"],"   "."</td>";
			}
		}
		        
	
		$conn->close();
	
?>