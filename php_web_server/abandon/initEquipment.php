<?php
// 水压	1->W 2,3->N
// 水温	6-60->N 4-6->W Other->R
// 水位	1->R 2->W 3,4->N


// 创建连接
$conn = new mysqli("localhost","root","123456", "tanks_db");
// 检测连接
if ($conn->connect_error) {
	$data['error'] = "Connection failed: " . $conn->connect_error;
	die("数据库读取出错:". $conn->connect_error);
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
	
	addEquipment($equip["eid"],$equip["ename"],$equip["serialnum"],$equip["describe"],$equip["water_gage"],$equip["water_temp"],$equip["water_level"]);
	
}

$conn->close();

function addEquipment($eid_1,$ename_1,$serialnum_1,$describe_1,$water_gage_1,$water_temp_1,$water_level_1){

	//	E/W/N=>2/1/0
	$flag=0;
	
	//判断水压
	if($water_gage_1==1){
		$water_gage_result = "过低";
		if($flag<1){
			$flag=1;
		}
	}else{
		$water_gage_result = "正常";
	}
	
	//判断水温
	if (4<=$water_temp_1&&$water_temp_1<6){
		$water_temp_result = "较低";
		if($flag<1){
			$flag=1;
		}
	}else if($water_temp_1<4){
		$water_temp_result = "过低";
		if($flag<2){
			$flag=2;
		}
	}else{
		$water_temp_result = "正常";
	}
	
	//判断水位
	if($water_level_1==1){
		$water_level_result = "过低";
		if($flag<2){
			$flag=2;
		}
	}elseif ($water_level_1==2){
		$water_level_result = "较低";
		if($flag<1){
			$flag=1;
		}
	}else{
		$water_level_result = "正常";
	}
	
	if($flag==2){
		$color_bg="red";
	}elseif($flag==1){
		$color_bg="yellow";
	}else{
		$color_bg="green";
	}
	
	echo '<div class="responsive span3" data-tablet="span6" data-desktop="span3">' ;
	echo '<div class="dashboard-stat '.$color_bg.'">';
	echo '<div class="visual">';
	echo '<i class="icon-bar-chart"></i>';
	echo '</div>';
	echo '<div class="details">';
	echo '<div class="number">';
	echo $water_temp_1.' ℃';
	echo '</div>';
	echo '<div class="desc">';
	echo '水压'.$water_gage_result;
	echo '</div>';
	echo '<div class="desc">';
	echo '水位'.$water_level_result;
	echo '</div>';
	echo '</div>';
	echo '<div class="ename">';
	echo '<div class="desc">';
	echo $ename_1.'水箱';
	echo '</div>';
	echo '</div>';
	echo '<a class="more" href="ManageDetail.php?eid='.$eid_1.'&ename='.$ename_1.'">';
	echo '详细信息 ';
	echo '<i class="m-icon-swapright m-icon-white"></i>';
	echo '</a>';
	echo '</div>';
	echo '</div>';
}

echo <<<END
<div class="responsive span4" data-tablet="span6" data-desktop="span3">
<div class="dashboard-stat yellow">
<div class="visual">
<i class="icon-bar-chart"></i>
</div>
<div class="details">
<div class="number">16℃</div>
<div class="desc">水压较低</div>
<div class="desc">水位正常</div>
</div>
<div class="ename">
<div class="desc">NO.3水箱</div>
</div>
<a class="more" href="#">详细信息 <i class="m-icon-swapright m-icon-white"></i></a>
</div>
</div>
END;
	