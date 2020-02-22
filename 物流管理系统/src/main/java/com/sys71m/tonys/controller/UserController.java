/**
 * Created by sys71m
 * Date: 2019/9/16
 * Time: 21:39
 **/
package com.sys71m.tonys.controller;

import com.sys71m.tonys.entity.BaseUsers;
import com.sys71m.tonys.model.AjaxResponse;
import com.sys71m.tonys.service.RoleService;
import com.sys71m.tonys.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import javax.servlet.http.HttpServletRequest;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public UserController(UserService userService, RoleService roleService) {
        this.roleService = roleService;
        this.userService = userService;
    }


    @RequestMapping(value = "/register", method = POST, produces = "application/json")
    public @ResponseBody AjaxResponse register(@RequestBody BaseUsers user){
        log.info("get data = {}", user);
        if(user.getUsername().trim() == null  || user.getPassword().trim() == null || user.getUsername().trim().length() == 0 || user.getPassword().trim().length() == 0){
            return AjaxResponse.error("用户名或密码不能为空");
        }
        else if(userService.existsByName(user.getUsername())){
            return AjaxResponse.error("用户已存在");
        }else{
            String md5Password = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
            user.setPassword(md5Password);
            SimpleDateFormat sdf = new SimpleDateFormat();
            sdf.applyPattern("yyyy-MM-dd HH:mm:ss a");
            Date date = new Date();
            user.setCreateTime(sdf.format(date).toString());
            user.setRoleID(roleService.getName(Integer.parseInt(user.getRoleID())));
            userService.add(user);
            return AjaxResponse.success("注册成功");
        }

    }

    @RequestMapping(value = "/login", method = POST, produces = "application/json")
    public @ResponseBody AjaxResponse login(@RequestBody BaseUsers user, HttpServletRequest request){
        log.info("get data = {}", user);
        if(userService.existsByName(user.getUsername())){
            String md5Password = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
            BaseUsers real = userService.selectByName(user.getUsername());
            if(real.getPassword().equals(md5Password) && real.getUsername().equals(user.getUsername())){
                request.getSession().setAttribute("user", user.getUsername());
                request.getSession().setAttribute("shell", userService.selectByName(user.getUsername()).getRoleID());
                return AjaxResponse.success("登录成功");
            }else{
                return AjaxResponse.error("用户名或密码错误");
            }

        }else{
            return AjaxResponse.error("用户名或密码错误");
        }

    }

    @RequestMapping(value = "/delete")
    public @ResponseBody AjaxResponse delete(HttpServletRequest request) {
        if (request.getSession().getAttribute("user") == null) {
            return AjaxResponse.error("未授权");
        } else {
            userService.deleteById(Integer.parseInt(request.getParameter("id")));
            log.info("userDelete work");
            return AjaxResponse.success("成功");
        }
    }

    @RequestMapping(value = "/logout")
    public ModelAndView logout(ModelAndView model, HttpServletRequest request){
        ModelAndView mv = new ModelAndView("redirect:/");
        request.getSession().removeAttribute("user");
        return mv;
    }

}

