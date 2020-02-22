/**
 * Created by sys71m
 * Date: 2019/10/15
 * Time: 20:45
 **/
package com.sys71m.tonys.service;

import com.sys71m.tonys.entity.WuLiu;

import java.util.List;

public interface WuLiuService {

    //添加订单
    void add(WuLiu wuliu);

    //删除订单
    void delete(Integer id);

    //获取所有订单
    List<WuLiu> getAllOrder();
}
