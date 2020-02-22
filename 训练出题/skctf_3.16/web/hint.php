<?php
error_reporting(0);
if(isset($_GET['value'])){
   if(substr(md5($_GET['value']) , 0 , 5) === '6a35b'){
      echo <<<src
   <!DOCTYPE html>
   <html>
   <head>
      <meta charset="utf-8"> 
      <title>My Blog</title>
      <link href="css/bootstrap.min.css" rel="stylesheet">
      <script src="js/jquery.min.js"></script>
      <script src="js/bootstrap.min.js"></script>
   </head>
   <body>
      <br>
      <div class="panel panel-success">
      <div class="panel-heading">
         <h3 class="panel-title">???进来了。。</h3>
      </div>
      <div class="panel-body">
         刚才写登录页面的时候断电了。。。还要重新写<br>
         其实，其实我想说flag是flag{....<br>
         <br>
         <img src='img/xiao.jpg' class="img-responsive" alt='晓'/>
         <br>
      </div>
      </div>
   </body>
   </html>
src;
   }
   else{
      echo '<script>alert("value_error")</script>';
   }
}
else{
      echo <<<src
   <!DOCTYPE html>
   <html>
   <head>
      <meta charset="utf-8"> 
      <title>My Blog</title>
      <link href="css/bootstrap.min.css" rel="stylesheet">
      <script src="js/jquery.min.js"></script>
      <script src="js/bootstrap.min.js"></script>
   </head>
   <body>
      <br>
      <div class="panel panel-success">
      <div class="panel-heading">
         <h3 class="panel-title">substr(md5(\$_GET['value']) , 0 , 5) === '6a35b'</h3>
      </div>
      </div>
   </body>
   </html>
src;
}

?>
