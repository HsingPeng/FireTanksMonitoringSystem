<?php

require 'header.php';

$eid=$_GET["eid"];
$type=$_GET["type"];
$pageSize=$_GET["pageSize"];
$rowNow=$_GET["rowNow"];

switch ($type){
	
	case 1:
		type_gage($eid,$pageSize,$rowNow);
		break;
	case 2:
		type_temp($eid,$pageSize,$rowNow);
		break;
	case 3:
		type_level($eid,$pageSize,$rowNow);
		break;
	default:
		$result['error'] = "参数错误!";
		echo(json_encode($result));
		break;
}


function type_gage($eid,$pageSize,$rowNow){
	
	$result;
	
	// 创建连接
	$conn = new mysqli("localhost","root","123456", "tanks_db");
	// 检测连接
	if ($conn->connect_error) {
		$result['error'] = "Connection failed: " . $conn->connect_error;
		die(json_encode($result));
	}
	$sql = "select count(*) from water_gage where eid=?";
	$mysqli_stmt=$conn->prepare($sql);
	//参数绑定
	$mysqli_stmt->bind_param('i',$eid);
	//绑定结果集
	$mysqli_stmt->bind_result($rowCount);
	//执行
	$mysqli_stmt->execute();
	
	while($mysqli_stmt->fetch()){
		$result["rowCount"]=$rowCount;
	}
	
	$data = array();
	$sql = "select record,date from water_gage where eid=? order by date desc limit ?,?";
	$mysqli_stmt=$conn->prepare($sql);
	//参数绑定
	$mysqli_stmt->bind_param('iii',$eid,$rowNow,$pageSize);
	//绑定结果集
	$mysqli_stmt->bind_result($record_1,$date_1);
	//执行
	$mysqli_stmt->execute();
	
	while($mysqli_stmt->fetch()){
		$data[count($data)]=array($date_1,$record_1);
	}

	$currentCount = count($data) ;
	$result["type"] = 1 ;
	$result["currentCount"] = $currentCount ;
	$result["data"] = $data ;
	$result["rowNow"] = (int)$rowNow;
	echo json_encode($result);
	$conn->close();
}

function type_temp($eid,$pageSize,$rowNow){
	
	$result;
	
	// 创建连接
	$conn = new mysqli("localhost","root","123456", "tanks_db");
	// 检测连接
	if ($conn->connect_error) {
		$result['error'] = "Connection failed: " . $conn->connect_error;
		die(json_encode($result));
	}
	$sql = "select count(*) from water_temp where eid=?";
	$mysqli_stmt=$conn->prepare($sql);
	//参数绑定
	$mysqli_stmt->bind_param('i',$eid);
	//绑定结果集
	$mysqli_stmt->bind_result($rowCount);
	//执行
	$mysqli_stmt->execute();
	
	while($mysqli_stmt->fetch()){
		$result["rowCount"]=$rowCount;
	}
	
	$data = array();
	$sql = "select record,date from water_temp where eid=? order by date desc limit ?,?";
	$mysqli_stmt=$conn->prepare($sql);
	//参数绑定
	$mysqli_stmt->bind_param('iii',$eid,$rowNow,$pageSize);
	//绑定结果集
	$mysqli_stmt->bind_result($record_2,$date_2);
	//执行
	$mysqli_stmt->execute();
	
	while($mysqli_stmt->fetch()){
		$data[count($data)]=array($date_2,$record_2);
	}

	$currentCount = count($data) ;
	$result["type"] = 2 ;
	$result["currentCount"] = $currentCount ;
	$result["data"] = $data ;
	$result["rowNow"] = (int)$rowNow;
	echo json_encode($result);
	$conn->close();
}

function type_level($eid,$pageSize,$rowNow){
	$result;
	
	// 创建连接
	$conn = new mysqli("localhost","root","123456", "tanks_db");
	// 检测连接
	if ($conn->connect_error) {
		$result['error'] = "Connection failed: " . $conn->connect_error;
		die(json_encode($result));
	}
	$sql = "select count(*) from water_level where eid=?";
	$mysqli_stmt=$conn->prepare($sql);
	//参数绑定
	$mysqli_stmt->bind_param('i',$eid);
	//绑定结果集
	$mysqli_stmt->bind_result($rowCount);
	//执行
	$mysqli_stmt->execute();
	
	while($mysqli_stmt->fetch()){
		$result["rowCount"]=$rowCount;
	}
	
	$data = array();
	$sql = "select record,date from water_level where eid=? order by date desc limit ?,?";
	$mysqli_stmt=$conn->prepare($sql);
	//参数绑定
	$mysqli_stmt->bind_param('iii',$eid,$rowNow,$pageSize);
	//绑定结果集
	$mysqli_stmt->bind_result($record_3,$date_3);
	//执行
	$mysqli_stmt->execute();
	
	while($mysqli_stmt->fetch()){
		$data[count($data)]=array($date_3,$record_3);
	}
	
	$currentCount = count($data) ;
	$result["type"] = 3 ;
	$result["currentCount"] = $currentCount ;
	$result["data"] = $data ;
	$result["rowNow"] = (int)$rowNow;
	echo json_encode($result);
	$conn->close();
	
}

