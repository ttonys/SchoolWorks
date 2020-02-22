<?php
/**
 * Created by PhpStorm.
 * User: Tonys
 * Date: 2019/2/24
 * Time: 22:33
 */

namespace app\index\controller;

use \think\Controller;
use app\index\model\users;
class Register extends Controller
{
    public function register()
    {

        if(request()->isPost()){
            $data=input('post.');
            if($data['pass'] !== $data['confirmpass']){
                $this->error("Inconsistent passwords");
            }else{
                $users = new users;
                $res = $users->register($data);
                if($res){
                    $this->success("Register successful", 'index/admin/admin');
                }else{
                    $this->error("User name exists");
                }
            }
        }
        return $this->fetch('register');
    }
}