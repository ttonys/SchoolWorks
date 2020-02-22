<?php
/**
 * Created by PhpStorm.
 * User: Tonys
 * Date: 2019/2/25
 * Time: 18:46
 */

namespace app\index\model;
use think\Model;
use think\Db;
use app\index\controller\Base;
class info extends model
{
    public function add_tag($name, $tag){
        $base = new Base();
        $tag = $tag;    
        if(DB::name('info')->where('name','=', $name)->find()){
            Db::name('info')->where('name', '=', $name)
                ->update(['tag' => $tag, 'name'=>$name]);
        }else{
            Db::name('info')->where('name', '=', $name)
                ->insert(['tag' => $tag, 'name'=>$name]);
        }
    }
    public function get_tag($name){
        $res = DB::name('info')->where('name','=',$name)->find();
//        echo serialize(session('user'));
        return $res['tag'];
    }
}