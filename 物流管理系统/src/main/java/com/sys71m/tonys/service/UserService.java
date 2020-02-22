/**
 * Created by sys71m
 * Date: 2019/9/18
 * Time: 21:20
 **/
package com.sys71m.tonys.service;

import com.sys71m.tonys.entity.BaseUsers;

import java.util.List;

public interface UserService {

    //增加用户
    BaseUsers add(BaseUsers user);

    //删除用户
    void deleteById(Integer id);

    //更新用户
    BaseUsers update(BaseUsers user);

    //获取用户列表
    List<BaseUsers> selectList();

    //根据ID查询
    BaseUsers selectById(Integer id);

    //根据用户名查找
    BaseUsers selectByName(String name);

    //判断用户是否存在
    boolean existsByName(String name);


}
