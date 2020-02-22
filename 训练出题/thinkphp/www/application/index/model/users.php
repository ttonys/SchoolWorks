<?php

/**
 * Created by PhpStorm.
 * User: Tonys
 * Date: 2019/2/25
 * Time: 8:24
 */
namespace app\index\model;
use think\Model;
use think\Db;
class users extends model
{
    public function login($data){
        $user = DB::name('users')->where('name','=',$data['name'])->find();
        if($user){
            if($user['pass'] === md5($data['pass'])){
                session('user',$user['name']);
                session('level',0);
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }
    public function register($data){
        $user = DB::name('users')->where('name','=',$data['name'])->find();
        if($user){
            return false;
        }else{
            $save = ['name' => $data['name'], 'pass' => md5($data['pass'])];
            Db::table('users')->insert($save);
            return true;
        }
    }
}