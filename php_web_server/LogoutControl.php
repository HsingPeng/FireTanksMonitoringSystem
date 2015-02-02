<?php
	session_start();
	if(isset($_SESSION['UID'])){
		session_destroy();
	}

	setcookie("PHPSESSID","", time()-3600,"/");
	
	header("Location:Login.php");
	exit();
	