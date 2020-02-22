<?php
error_reporting(0);
session_start();
if(!$_SESSION['isadmin']){
	die('You are not admin');
}
if(isset($_GET['action'])){
	if($_GET['action'] === 'cat'){
		echo "flag{gei_da_lao_di_flag_23333}";
	}
}
?>

<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title>adminpage</title>
	<link href="css/bootstrap.min.css" rel="stylesheet">
    <script src="js/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
</head>
<body>
	<nav class="navbar navbar-default" role="navigation">
   <div class="navbar-header">
      <a class="navbar-brand" href="#">后台</a>
   </div>
   <div>
      <ul class="nav navbar-nav">
         <li class="active"><a href="#">编辑文章</a></li>
         <li><a href="#">设置</a></li>
      </ul>
   </div></nav>
   <div class="panel panel-success">
   <div class="panel-heading">
      <h1 class="panel-title">文章列表</h1>
   </div>
   <div class="panel-body">
      <li><a href='#'>博主你的博客不行啊</a><br></li>
      <li><a href='#'>Hello,world!</a><br></li>
      <li><a href='?action=cat'>This is admin page</a><br></li>
   </div>
   </div>
</body>
</html>
