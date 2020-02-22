<?php
session_start();
ob_start();
?>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Login Form</title>
  <link rel="stylesheet" type="text/css" href="css/login.css" />
</head>
<body>
  <div class="login">
    <h1>后台登录</h1>
    <form method="post" action="#">
        <input type="text" name="username" placeholder="Username" required="required" />
        <input type="password" name="password" placeholder="Password" required="required" />
        <button type="submit" class="btn btn-primary btn-block btn-large">Login</button>
    </form>
</div>
</body>
</html>
<?php
$md5 = 'fac6d830e795e0ccbd36da173ddc3ac0';
if(isset($_POST['username'])&&isset($_POST['password'])){
    extract($_POST);
    if($username === 'admin' && md5($password) === $md5){
        $_SESSION['isadmin'] = 1;
        header('Location: ./admin.php');
        ob_end_flush();
    }else{
        die('Login failed.');
    }
}
?>
