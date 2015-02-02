<?php
	require 'header.php';
	// 水压	1->W 2,3->N
	// 水温	6-60->N 4-6->W Other->R
	// 水位	1->R 2->W 3,4->N
	
	
	// 创建连接
	$conn = new mysqli("localhost","root","123456", "tanks_db");
	// 检测连接
	if ($conn->connect_error) {
		$data['error'] = "Connection failed: " . $conn->connect_error;
		$result["error"]=("数据库读取出错:". $conn->connect_error);
	}
	
	$conn->query("set names utf8");
	
	
	$sql = "select * from equipment";
	$mysqli_stmt=$conn->prepare($sql);
	
	
	//绑定结果集
	$mysqli_stmt->bind_result($eid,$ename,$serialnum,$describe);
	//执行
	$mysqli_stmt->execute();
	
	$equipemnt_list = array();
	//取出绑定的值
	while($mysqli_stmt->fetch()){
		$equipemnt_list[count($equipemnt_list)]=array("eid"=>$eid,"ename"=>$ename,"serialnum"=>$serialnum,"describe"=>$describe);
	}
	
	$data = array();
	
	foreach ($equipemnt_list as $equip){
		$eid_1_1 = $equip["eid"];
		//获取水压
		$equip["water_gage"]=2;
		$sql = "select record from water_gage where eid=? order by date desc limit 1";
		$mysqli_stmt=$conn->prepare($sql) or trigger_error(mysqli_error($conn)."[$sql]");
		//参数绑定
		$mysqli_stmt->bind_param('i',$eid_1_1) ;
		//绑定结果集
		$mysqli_stmt->bind_result($record_1_1);
		//执行
		$mysqli_stmt->execute();
	
		while($mysqli_stmt->fetch()){
			$equip["water_gage"]=$record_1_1;
		}
		//获取水温
		$equip["water_temp"]=1;
		$sql = "select record from water_temp where eid=? order by date desc limit 1";
		$mysqli_stmt=$conn->prepare($sql) or trigger_error(mysqli_error($conn)."[$sql]");
		//参数绑定
		$mysqli_stmt->bind_param('i',$eid_1_1);
		//绑定结果集
		$mysqli_stmt->bind_result($record_1_1);
		//执行
		$mysqli_stmt->execute();
	
		while($mysqli_stmt->fetch()){
			$equip["water_temp"]=$record_1_1;
		}
		//获取水位
		$equip["water_level"]=1;
		$sql = "select record from water_level where eid=? order by date desc limit 1";
		$mysqli_stmt=$conn->prepare($sql) or trigger_error(mysqli_error($conn)."[$sql]");
		//参数绑定
		$mysqli_stmt->bind_param('i',$eid_1_1);
		//绑定结果集
		$mysqli_stmt->bind_result($record_1_1);
		//执行
		$mysqli_stmt->execute();
	
		while($mysqli_stmt->fetch()){
			$equip["water_level"]=$record_1_1;
		}
		
		$data[count($data)] = $equip;
	
	}
	
	$conn->close();
	
	$result["data"] = $data ;
	echo json_encode($result);
	