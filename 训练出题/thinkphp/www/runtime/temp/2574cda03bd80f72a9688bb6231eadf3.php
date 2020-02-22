<?php if (!defined('THINK_PATH')) exit(); /*a:1:{s:64:"/var/www/public/../application/index/view/register/register.html";i:1551446182;}*/ ?>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<title>后台登录</title>
<meta name="author" content="DeathGhost" />
<link rel="stylesheet" type="text/css" href="/static/index/style.css" tppabs="style.css" />
<style>
body{height:100%;background:#16a085;overflow:hidden;}
canvas{z-index:-1;position:absolute;}
</style>
<script src="/static/index/jquery.js"></script>
<script src="/static/index/verificationNumbers.js" tppabs="verificationNumbers.js"></script>
<script src="/static/index/Particleground.js" tppabs="Particleground.js"></script>
<script>
$(document).ready(function() {
  //粒子背景特效
  $('body').particleground({
    dotColor: '#5cbdaa',
    lineColor: '#5cbdaa'
  });
  //验证码
  createCode();
  //测试提交，对接程序删除即可
  $(".submit_btn").click(function(){
	  location.href="javascrpt:;"/*tpa=http://***admin.html*/;
	  });
});
</script>
</head>
<body>
<form action="" method="post">
 <dl class="admin_login">
  <dt>
   <strong>注册</strong>
   <em>Management System</em>
  </dt>
  <dd class="user_icon">
   <input type="text" placeholder="账号" class="login_txtbx" name="name"/>
  </dd>
  <dd class="pwd_icon">
   <input type="password" placeholder="密码" class="login_txtbx" name="pass"/>
  </dd>
  <dd class="pwd_icon">
   <input type="password" placeholder="确认密码" class="login_txtbx" name="confirmpass"/>
  </dd>
  <dd>
   <input type="submit" value="立即注册" class="submit_btn"/>
  </dd>

 </dl>
</form>
</body>
</html>
