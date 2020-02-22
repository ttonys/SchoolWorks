/**
 * Created by sys71m
 * Date: 2019/10/10
 * Time: 16:46
 **/
package com.sys71m.tonys.controller;

import com.sys71m.tonys.service.RoleService;
import com.sys71m.tonys.service.UserService;
import com.sys71m.tonys.service.WuLiuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;


@Slf4j
@Controller
@RestController
public class AdminController {

    public final RoleService roleService;
    public final UserService userService;
    public final WuLiuService wuLiuService;

    @Autowired
    public AdminController(RoleService roleService, UserService userService, WuLiuService wuLiuService){
        this.roleService = roleService;
        this.wuLiuService = wuLiuService;
        this.userService = userService;
    }
    @RequestMapping("/show")
    public ModelAndView show(ModelAndView model, HttpServletRequest request){
        if(request.getSession().getAttribute("user") == null){
            model.setViewName("error");
        }else{
            model.setViewName("show");
        }
        log.info("show work");
        return model;
    }

    @RequestMapping("/admin")
    public ModelAndView admin(ModelAndView model, HttpServletRequest request){
        if(request.getSession().getAttribute("user") == null){
            model.setViewName("error");
        }else{
            model.addObject("name", request.getSession().getAttribute("user"));
            model.setViewName("admin/index");

        }
        log.info("admin work");
        return model;
    }

    @RequestMapping("/welcome")
    public ModelAndView welcome(ModelAndView model, HttpServletRequest request){
        if(request.getSession().getAttribute("user") == null){
            model.setViewName("error");
        }else{
            SimpleDateFormat sdf = new SimpleDateFormat();
            sdf.applyPattern("yyyy-MM-dd HH:mm:ss a");
            Date date = new Date();
            model.addObject("data", sdf.format(date).toString());
            model.setViewName("admin/welcome");
        }
        log.info("welcome work");
        return model;
    }


    @RequestMapping("/admin_role")
    public ModelAndView admin_role(ModelAndView model, HttpServletRequest request){
        if(request.getSession().getAttribute("user") == null){
            model.setViewName("error");
        }else{
            String shell = (String) request.getSession().getAttribute("shell");
            if(shell.equals("超级管理员") || roleService.getRole(shell).getUserController().equals("角色管理")){
                model.addObject("roles", roleService.getAllRoles());
                model.setViewName("admin/admin-role");
            }else{
                model.setViewName("error");
            }

        }
        log.info("admin_role work");
        return model;
    }

    @RequestMapping("/admin_add")
    public ModelAndView admin_add(ModelAndView model, HttpServletRequest request){
        if(request.getSession().getAttribute("user") == null){
            model.setViewName("error");
        }else{
            model.addObject("roles", roleService.getAllRoles());
            model.setViewName("admin/admin-add");
        }
        log.info("admin_add work");
        return model;
    }


    @RequestMapping("/admin_list")
    public ModelAndView admin_list(ModelAndView model, HttpServletRequest request){
        if(request.getSession().getAttribute("user") == null){
            model.setViewName("error");
        }else{
            String shell = (String) request.getSession().getAttribute("shell");
            if(shell.equals("超级管理员") || roleService.getRole(shell).getUserController().equals("用户管理")){
                model.addObject("users", userService.selectList());
                model.setViewName("admin/admin-list");
            }else{
                model.setViewName("error");
            }

        }
        log.info("admin_list work");
        return model;
    }

    @RequestMapping("/order_list")
    public ModelAndView order_list(ModelAndView model, HttpServletRequest request){
        if(request.getSession().getAttribute("user") == null){
            model.setViewName("error");
        }else{
            String shell = (String) request.getSession().getAttribute("shell");
            if(shell.equals("超级管理员") || roleService.getRole(shell).getWuliuController().equals("物流管理")){
                model.addObject("wulius", wuLiuService.getAllOrder());
                model.setViewName("admin/order-list");
            }else{
                model.setViewName("error");
            }

        }
        log.info("order_list work");
        return model;
    }



    @RequestMapping("/order_add")
    public ModelAndView order_add(ModelAndView model, HttpServletRequest request){
        if(request.getSession().getAttribute("user") == null){
            model.setViewName("error");
        }else{
            model.setViewName("admin/order-add");
        }
        log.info("order_add work");
        return model;
    }




    @RequestMapping("/role_add")
    public ModelAndView role_add(ModelAndView model, HttpServletRequest request){
        if(request.getSession().getAttribute("user") == null){
            model.setViewName("error");
        }else{
            model.setViewName("admin/role-add");
        }
        log.info("role_add work");
        return model;
    }

}
