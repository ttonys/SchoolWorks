/**
 * Created by sys71m
 * Date: 2019/10/16
 * Time: 19:16
 **/
package com.sys71m.tonys.controller;

import com.sys71m.tonys.entity.BaseUsers;
import com.sys71m.tonys.entity.WuLiu;
import com.sys71m.tonys.model.AjaxResponse;
import com.sys71m.tonys.service.WuLiuService;
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
@RequestMapping("/wuliu")
public class WuLiuController {
    private final WuLiuService wuLiuService;

    @Autowired
    public WuLiuController(WuLiuService wuLiuService){
        this.wuLiuService = wuLiuService;
    }

    @RequestMapping(value = "/add", method = POST, produces = "application/json")
    public @ResponseBody AjaxResponse add(@RequestBody WuLiu wuLiu, HttpServletRequest request){
        if (request.getSession().getAttribute("user") == null) {
            return AjaxResponse.error("未授权");
        } else {
            for(int i = 0; i < wuLiu.toString().length(); i++){
                char c = wuLiu.toString().charAt(i);


            }
            wuLiuService.add(wuLiu);
            log.info("wuliu_add work");
            return AjaxResponse.success("成功");
        }
    }


    @RequestMapping(value = "/delete")
    public @ResponseBody AjaxResponse delete(HttpServletRequest request) {
        if (request.getSession().getAttribute("user") == null) {
            return AjaxResponse.error("未授权");
        } else {
            System.out.println(request.getParameter("id"));
            wuLiuService.delete(Integer.parseInt(request.getParameter("id")));
            log.info("wuliu_delete work");
            return AjaxResponse.success("成功");
        }
    }

}
