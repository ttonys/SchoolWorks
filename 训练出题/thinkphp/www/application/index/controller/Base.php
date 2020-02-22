<?php
/**
 * Created by PhpStorm.
 * User: Tonys
 * Date: 2019/2/24
 * Time: 22:09
 */

namespace app\index\controller;

use think\Controller;
class Base extends Controller
{
    public function _initialize(){
        if(!session('user')){
            $this->error('请先登录系统！','Index/index');
        }
    }

    public function get_name(){
        return session('user');
    }

}