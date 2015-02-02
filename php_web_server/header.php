<?php
	session_start();
	if (!isset($_SESSION['UID'])||$_SESSION['UID']==""){
		header("Location:Login.php");
		exit();
	}
?>
