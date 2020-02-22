<?php
namespace app\index\controller;

use \think\Controller;
use app\index\model\users;
class Index extends Controller
{
    public function index()
    {
        if(request()->isPost()){
            $data=input('post.');
            $users = new users;
            $res = $users->login($data);
            if($res){
                return $this->success('Login successful', 'index/admin/admin');
            }else{
                $this->error("username or password error");
            }
        }
        return view();
    }
}
