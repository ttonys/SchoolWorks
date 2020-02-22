<?php
/**
 * Created by PhpStorm.
 * User: Tonys
 * Date: 2019/2/24
 * Time: 22:15
 */

namespace app\index\controller;

use app\index\controller\Base;
use app\index\controller\Vip;
use app\index\model\info;
class Admin extends Base
{
    public function admin(){
        $name = $this->get_name();
        $info = new info();
        if($info->get_tag($name)){
            $tag = $info->get_tag($name);
            $this->assign('sign', $tag);
        }else{
            $this->assign('sign', "skctf");
        }
        $this->assign('name', $name);
        return view();
    }

    public function ch_code(){
        $name = $this->get_name();
        $this->assign('name', $name);
        $tag = input('get.tag');
        $info = new info();
        if(request()->isGet() && !empty($tag)){
            $info->add_tag($name, $tag);
            $this->redirect('admin',302);
        }
        return $this->fetch('change');

    }

    public function logout(){
        session(null);
        $this->success('退出成功！','index');
    }

    public function image(){
        return $this->fetch('image');
    }

    public function secret(){
        $info = new info;
        if(request()->isGet() && !empty(request()->isGet())){
            $key = input('get.key');
            $res = input('get.res');
            $v = new Vip();
            $v->check($key, $res);
        }
        if(session('user') !== 'admin' && session('level') === 0){
            return "非管理员或邀请用户不可查看flag...";
        }else{
            $secret = $info->get_tag('admin');
            return "secret:".$secret;
        }
    }
}