$(document).ready(function(){

	$('#registerBttn').click(function (e){
		var $inEmail 	= document.getElementById("emailText").value;
		var $inPass1 	= document.getElementById("passText").value;
		var $inPass2  	= document.getElementById("pass2Text").value;
		var $inName  	= document.getElementById("nameText").value;


		if($inEmail!==""
			&&$inPass1!==""
			&&$inPass2!==""
			&&$inName!==""){

			if($inPass1===$inPass2){
				$.post('/auth/busRegister.php', {email: $inEmail,
	        										pass: $inPass1,
	        										name: $inName})
				.done(function(data){
					if(data==="Success"){
						login($inEmail, $inPass1);

					}else if(data==="Email Already Exists"){

					}else{
						document.getElementById("errorDiv").innerHTML=data ;
					}
				});


			}else{
				document.getElementById("errorDiv").innerHTML=$inEmail;
				//document.getElementById("errorDiv").innerHTML=("Passwords Must Match"+inPass1+" "+inPass2);
			}

		

		}else{

			document.getElementById("errorDiv").innerHTML="Missing details";
		}


	});

	$('#loginButton').click(function(e){
		var $inEmail 	= document.getElementById("emailText").value;
		var $inPass 	= document.getElementById("passText").value;

		if($inEmail!==""
			&&$inPass!==""){

			login($inEmail, $inPass);
		}else{
			document.getElementById("errorDiv").innerHTML="Missing details";
		}

	});

});



function login($email, $pass){
	document.getElementById("errorDiv").innerHTML="logging in";
	$.post('/auth/busLogin.php', {email: $email,
									pass: $pass})
				.done(function(data){
					if(data==="Success"){
						document.getElementById("errorDiv").innerHTML="logged In";
						window.location.reload();
					}else if(data==="Not Registered"){

					}else{
						document.getElementById("errorDiv").innerHTML=data;
					}
				});
}

function signOut(){
	$.post('/auth/busLogout.php', {})
				.done(function(data){
					if(data==="Success"){
						window.location.reload();
					}else{
						console.log(data);
					}
				
				});

}


function saveAddress($exists){
		var $inNumber 	= document.getElementById("numberText").value;
		var $inLine1 	= document.getElementById("line1Text").value;
		var $inLine2 	= document.getElementById("line2Text").value;
		var $inLine3 	= document.getElementById("line3Text").value;
		var $inLine4 	= document.getElementById("line4Text").value;
		var $inTown	 	= document.getElementById("townText").value;
		var $inCounty 	= document.getElementById("countyText").value;
		var $inEircode	= document.getElementById("eircodeText").value;

	$.post('/scripts/saveAddress.php',{
				exists: 	$exists,
				number: 	$inNumber,
				line1: 		$inLine1,
				line2: 		$inLine2,
				line3: 		$inLine3,
				line4: 		$inLine4,
				town: 		$inTown,
				county: 	$inCounty,
				eircode: 	$inEircode
				}).done(function(data){
					if(data==="Success"){
						console.log(data);

					}else{
						console.log("Error");
						console.log(data);
					}
				
				});


}