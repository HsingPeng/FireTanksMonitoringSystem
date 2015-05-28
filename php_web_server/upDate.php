<?php
/*
    设备终端发送最新的状态 设备编号eid 水压gd 水温td 水位ld 
    水压	1->W 2,3->N
    水温	6-60->N 4-6->W Other->R
    水位	1->R 2->W 3,4->N
*/

$eid=$_POST["eid"];
$gd=$_POST["gd"];
$td=$_POST["td"];
$ld=$_POST["ld"];

$result="update error";

// 创建连接
$conn = new mysqli("localhost","root","123456", "tanks_db");

if ($conn->connect_error) {
    $result="update error";
}

$sql = "select count(*) from equipment where eid=?";
$mysqli_stmt=$conn->prepare($sql);
//参数绑定
$mysqli_stmt->bind_param('i',$eid);
//绑定结果集
$mysqli_stmt->bind_result($rowCount);
//执行
$mysqli_stmt->execute();
$flag=0;
while($mysqli_stmt->fetch()){
    $flag=$rowCount;
}
if($flag>0){
    
    //插入水压数据
    $sql = "insert into water_gage values(NULL, ?, ?, NULL)";
    $mysqli_stmt=$conn->prepare($sql);
	//参数绑定
	$mysqli_stmt->bind_param('ii',$eid,$gd);
	//执行
	$mysqli_stmt->execute();
    
    //插入水温数据
    $sql = "insert into water_temp values(NULL, ?, ?, NULL)";
    $mysqli_stmt=$conn->prepare($sql);
	//参数绑定
	$mysqli_stmt->bind_param('ii',$eid,$td);
	//执行
	$mysqli_stmt->execute();
    
    //插入水位数据
    $sql = "insert into water_level values(NULL, ?, ?, NULL)";
    $mysqli_stmt=$conn->prepare($sql);
	//参数绑定
	$mysqli_stmt->bind_param('ii',$eid,$ld);
	//执行
	$mysqli_stmt->execute();

    //准备插入日志
    $level=0;
    if($gd==1){
        $level=1;
    }
    if(3<$td&&$td<7){
        $level=1;
    }else if($td<4||$td>60){
        $level=2;
    }
    if($ld==2){
        $level=($level<2)?1:$level;
    }else if($ld==1){
        $level=2;
    }
    
    //(`log_id`, `eid`, `level`, `g_record`, `t_record`, `l_record`)
    $sql = "insert into log values(NULL, ?, ?, ?, ?, ?, NULL)";
    $mysqli_stmt=$conn->prepare($sql);
	//参数绑定
	$mysqli_stmt->bind_param('iiiii',$eid,$level,$gd,$td,$ld);
	//执行
	$mysqli_stmt->execute();
    
    $result="update success";
    
}else{
    $result="update error";
}

$conn->close();
echo $result;