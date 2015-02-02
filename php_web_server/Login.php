<?php
	session_start();
	if (isset($_SESSION['UID'])&&$_SESSION['UID']!=""){
		header("Location:Manage.php");
		exit();
	}else{
		require 'LoginView.php';
	}

