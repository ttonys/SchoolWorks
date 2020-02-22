<?php if (!defined('THINK_PATH')) exit(); /*a:1:{s:58:"/var/www/public/../application/index/view/admin/admin.html";i:1551616437;}*/ ?>
<!DOCTYPE html>
<html >
<head>
  <meta charset="UTF-8">
  <title>SVG clip-path Hover Effect</title>
  <link href='https://fonts.googleapis.com/css?family=Source+Sans+Pro:400,600' rel='stylesheet' type='text/css'>

  
  
      <link rel="stylesheet" href="/static/admin/css/style.css">

  
</head>

<body>
  <div class='container'>
  <header>
    <h1>Hello <?php echo $name; ?></h1>
    <h2>My Invitation code <?php echo $sign; ?></h2>
    <p class='small'>
      <b>Note:</b> this is an experiment, it does not seem to work on Firefox 43.0.4
      neither have touch support.
      <br/>Tested on Chrome 47.0.2526.106, Opera 34.0 and Safari 8.0.6.
    </p>
  </header>
  <main>
    <div class='items'>
      <div class='item'>
        <svg preserveAspectRatio='xMidYMid slice' viewBox='0 0 300 200'>
          <defs>
            <clipPath id='clip-0'>
              <circle cx='0' cy='0' fill='#000' r='150px'></circle>
            </clipPath>
          </defs>
          <text class='svg-text' dy='.3em' x='50%' y='50%'>
            change code
          </text>
          <g clip-path='url(#clip-0)'>
            <a href="<?php echo url('admin/ch_code'); ?>">
            <image height='100%' preserveAspectRatio='xMinYMin slice' width='100%' xlink:href='/static/admin/img/i-v2.png'></image>
            </a>
            <text class='svg-masked-text' dy='.3em' x='50%' y='50%'>
              change code
            </text>
          </g>
        </svg>
      </div>
      <div class='item'>
        <svg preserveAspectRatio='xMidYMid slice' viewBox='0 0 300 200'>
          <defs>
            <clipPath id='clip-1'>
              <circle cx='0' cy='0' fill='#000' r='150px'></circle>
            </clipPath>
          </defs>
          <text class='svg-text' dy='.3em' x='50%' y='50%'>
            secret
          </text>
          <g clip-path='url(#clip-1)'>
            <a href="<?php echo url('admin/secret'); ?>">
            <image height='100%' preserveAspectRatio='xMinYMin slice' width='100%' xlink:href='/static/admin/img/i-worms.png'></image>
            </a>
            <text class='svg-masked-text' dy='.3em' x='50%' y='50%'>
              secret
            </text>
          </g>
        </svg>
      </div>
      <div class='item'>
        <svg preserveAspectRatio='xMidYMid slice' viewBox='0 0 300 200'>
          <defs>
            <clipPath id='clip-2'>
              <circle cx='0' cy='0' fill='#000' r='150px'></circle>
            </clipPath>
          </defs>
          <text class='svg-text' dy='.3em' x='50%' y='50%'>
            image
          </text>
          <g clip-path='url(#clip-2)'>
            <a href="<?php echo url('admin/image'); ?>">
            <image height='100%' preserveAspectRatio='xMinYMin slice' width='100%' xlink:href='/static/admin/img/i-aurora.png'></image>
            </a>
            <text class='svg-masked-text' dy='.3em' x='50%' y='50%'>
              image
            </text>
          </g>
        </svg>
      </div>
      <div class='item'>
        <svg preserveAspectRatio='xMidYMid slice' viewBox='0 0 300 200'>
          <defs>
            <clipPath id='clip-3'>
              <circle cx='0' cy='0' fill='#000' r='150px'></circle>
            </clipPath>
          </defs>
          <text class='svg-text' dy='.3em' x='50%' y='50%'>
            Angus
          </text>
          <g clip-path='url(#clip-3)'>
            <image height='100%' preserveAspectRatio='xMinYMin slice' width='100%' xlink:href='/static/admin/img/i-angus.png'></image>
            <text class='svg-masked-text' dy='.3em' x='50%' y='50%'>
              Angus
            </text>
          </g>
        </svg>
      </div>
      <div class='item'>
        <svg preserveAspectRatio='xMidYMid slice' viewBox='0 0 300 200'>
          <defs>
            <clipPath id='clip-4'>
              <circle cx='0' cy='0' fill='#000' r='150px'></circle>
            </clipPath>
          </defs>
          <text class='svg-text' dy='.3em' x='50%' y='50%'>
            Logout
          </text>
          <g clip-path='url(#clip-4)'>
            <a href="<?php echo url('admin/logout'); ?>">
            <image height='100%' preserveAspectRatio='xMinYMin slice' width='100%' xlink:href='/static/admin/img/i-huitzi.png'></image>
            </a>
            <text class='svg-masked-text' dy='.3em' x='50%' y='50%'>
              logout
            </text>
          </g>
        </svg>
      </div>
    </div>
  </main>
</div>
<div class='options'>
  <button class='dark'></button>
  <button class='light'></button>
</div>
  
    <script src="/static/admin/js/index.js"></script>

</body>
</html>
