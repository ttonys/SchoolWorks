<?php
/**
 * Created by PhpStorm.
 * User: Tonys
 * Date: 2019/2/28
 * Time: 21:53
 */

namespace app\index\controller;


class Vip
{
    var $salt = "tonys";
    var $code = false;
    public function level_up($code){
        if($code === true){
            session('level', 1);
        }else{
            session('level', 0);
        }
    }
    public function check($key, $res){
        if(md5($key.$this->salt) === $res){
            $this->code = true;
        }
        $this->level_up($this->code);
    }
}