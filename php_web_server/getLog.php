<?php

require 'header.php';

$sEcho = $_REQUEST["sEcho"];						// 记录操作的次数  每次加1
$iDisplayStart = $_REQUEST["iDisplayStart"];
$iDisplayLength = $_REQUEST["iDisplayLength"];


$result;

// 创建连接
$conn = new mysqli("localhost","root","123456", "tanks_db");
// 检测连接
if ($conn->connect_error) {
	$result['error'] = "Connection failed: " . $conn->connect_error;
	die(json_encode($result));
}
$sql = "select count(*) from log";
$mysqli_stmt=$conn->prepare($sql);
//绑定结果集
$mysqli_stmt->bind_result($rowCount);
//执行
$mysqli_stmt->execute();

while($mysqli_stmt->fetch()){
	$result["iTotalRecords"]=$rowCount;
	$result["iTotalDisplayRecords"]=$rowCount;
}

$data = array();
$sql = "select * from log order by log_id desc limit ?,?";
$mysqli_stmt=$conn->prepare($sql);
//参数绑定
$mysqli_stmt->bind_param('ii',$iDisplayStart,$iDisplayLength);
//绑定结果集
$mysqli_stmt->bind_result($log_id,$eid,$level,$g_record,$t_record,$l_record,$date);
//执行
$mysqli_stmt->execute();

while($mysqli_stmt->fetch()){
	$data[count($data)]=array($log_id,$eid,$level,$g_record,$t_record,$l_record,$date);
}

$result["aaData"] = $data ;
$result["sEcho"] = $sEcho+1;
echo json_encode($result);
$conn->close();

