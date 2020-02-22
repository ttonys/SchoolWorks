/**
 * Created by sys71m
 * Date: 2019/10/10
 * Time: 21:22
 **/
package com.sys71m.tonys.controller;

import com.sys71m.tonys.entity.Roles;
import com.sys71m.tonys.model.AjaxResponse;
import com.sys71m.tonys.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Slf4j
@RestController
@RequestMapping("/role")
public class RoleController {

    public final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService){
        this.roleService = roleService;
    }

    @RequestMapping(value = "/add", method = POST, produces = "application/json")
    public @ResponseBody AjaxResponse add(@RequestBody Roles role, HttpServletRequest request){
        if(request.getSession().getAttribute("user") == null){
            return AjaxResponse.error("未授权");
        }else{



            //数值检测
            if(role.getWuliuController() != null){
                if(role.getWuliuController().equals("true")){
                    role.setWuliuController("物流管理");
                }else{
                    role.setWuliuController("");
                }
            }else{
                role.setWuliuController("");
            }

            //用户
            if(role.getUserController() != null){
                if(role.getUserController().equals("true")){
                    role.setUserController("用户管理");
                }else{
                    role.setUserController("");
                }
            }else{
                role.setUserController("");
            }

            //角色
            if(role.getRoleController() != null){
                if(role.getRoleController().equals("true")){
                    role.setRoleController("角色管理");
                }else{
                    role.setRoleController("");
                }
            }else{
                role.setRoleController("");
            }
            roleService.add(role);
        }
        log.info("role add work");
        return AjaxResponse.success("成功");
    }



    @RequestMapping(value = "/delete")
    public @ResponseBody AjaxResponse delete(HttpServletRequest request) {
        if (request.getSession().getAttribute("user") == null) {
            return AjaxResponse.error("未授权");
        } else {
            roleService.deleteByID(Integer.parseInt(request.getParameter("id")));
            log.info("status work");
            return AjaxResponse.success("成功");
        }
    }
}
