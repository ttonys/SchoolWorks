/**
 * Created by sys71m
 * Date: 2019/10/10
 * Time: 20:45
 **/
package com.sys71m.tonys.service;


import com.sys71m.tonys.entity.Roles;

import java.util.List;

public interface RoleService {


    //增加角色
    void add(Roles role);

    //增加角色
    void deleteByID(Integer id);

    //更新角色
    void update(Roles role);

    //获取角色名
    String getName(Integer id);

    Roles getRole(String name);

    //获取所有角色
    List<Roles> getAllRoles();


}
