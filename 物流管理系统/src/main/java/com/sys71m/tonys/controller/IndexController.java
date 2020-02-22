package com.sys71m.tonys.controller;
import com.sys71m.tonys.entity.BaseUsers;
import com.sys71m.tonys.entity.Roles;
import com.sys71m.tonys.entity.WuLiu;
import com.sys71m.tonys.service.RoleService;
import com.sys71m.tonys.service.UserService;
import com.sys71m.tonys.service.WuLiuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.Date;


@Slf4j
@Controller
@RestController
public class IndexController {

    private final UserService userService;
    private final RoleService roleService;
    private final WuLiuService wuLiuService;

    @Autowired
    public IndexController(UserService userService, RoleService roleService, WuLiuService wuLiuService) {

        //超级管理员
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("yyyy-MM-dd HH:mm:ss a");
        Date date = new Date();

        BaseUsers super_admin = new BaseUsers();
        super_admin.setPassword("21232f297a57a5a743894a0e4a801fc3");
        super_admin.setUsername("admin");
        super_admin.setPhone("17852151021");
        super_admin.setEmail("admin@qq.com");
        super_admin.setCreateTime(sdf.format(date).toString());
        super_admin.setRoleID("超级管理员");
        this.userService = userService;
        this.userService.add(super_admin);

        //超级权限
        Roles role = new Roles();
        role.setRoleController("用户管理");
        role.setUserController("角色管理");
        role.setWuliuController("物流管理");
        role.setRoleDescribe("SUPER");
        role.setRoleName("超级管理员");
        this.roleService = roleService;
        this.roleService.add(role);

        //物流样例
        WuLiu wuliu = new WuLiu();
        wuliu.setMoney("0");
        wuliu.setId(1);
        wuliu.setOrderID("test");
        wuliu.setOrderState("test");
        wuliu.setPayMethod("test");
        wuliu.setPhone("123456789");
        wuliu.setRevAddress("test");
        wuliu.setRevName("test");
        wuliu.setSendMode("test");
        this.wuLiuService = wuLiuService;
        wuLiuService.add(wuliu);

    }


    @RequestMapping("/")
    public ModelAndView index(ModelAndView model){
        log.info("index work");
        model.addObject("msg1", "登录页面");
        model.addObject("msg2", "会员登录");
        model.addObject("msg3", "登录");
//        model.addObject("msg4", "还没有账号？立即注册");
//        model.addObject("url", "register");
        model.addObject("img", "img-01.png");
        model.addObject("controller", "login");
        model.setViewName("index");
        return model;
    }

    @RequestMapping("/register")
    public ModelAndView register(ModelAndView model){
        log.info("register work");
        model.addObject("msg1", "注册页面");
        model.addObject("msg2", "用户注册");
        model.addObject("msg3", "注册");
        model.addObject("msg4", "立即登录");
        model.addObject("url", "");
        model.addObject("img", "img-02.png");
        model.addObject("controller", "register");
        model.setViewName("index");
        return model;
    }

}