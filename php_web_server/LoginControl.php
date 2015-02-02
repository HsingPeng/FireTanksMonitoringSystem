<?php

$uid=$_POST["username"];
$password=$_POST["password"];
$checkcode=$_POST["checkcode"];

$remember = 0;
if(isset($_POST['remember'])){
	$remember = $_POST['remember'];
}

// 创建连接
$conn = new mysqli("localhost","root","123456", "tanks_db");
// 检测连接
if ($conn->connect_error) {
	$data['error'] = "Connection failed: " . $conn->connect_error;
    die(json_encode($data));
}

$sql = "select * from admin where uid= ? ";
$mysqli_stmt=$conn->prepare($sql);
//参数绑定
$mysqli_stmt->bind_param('i',$uid);
//绑定结果集
$mysqli_stmt->bind_result($uid,$uname,$password_1,$remark);
//执行
$mysqli_stmt->execute();
//取出绑定的值
while($mysqli_stmt->fetch()){
	$password_1 = md5($password_1.$checkcode) ;
}

if($password == $password_1){
	$admin = array ('uid'=>$uid,'uname'=>$uname,'remark'=>$remark);
	$data = array('success' => $admin );
	
	session_start();
	if($remember==1){
		setcookie(session_name(),session_id(),time()+60*60*24*7,"/");
	}
	$_SESSION['UID']=$uid;
	$_SESSION['uname']=$uname;
	$_SESSION['remark']=$remark;
	
	}else{
		$data['error'] = "用户名或密码错误";
	}
	
echo json_encode($data);

$conn->close();

