/**
 * Created by sys71m
 * Date: 2019/9/16
 * Time: 21:39
 **/
package com.sys71m.tonys.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class AjaxResponse {

    private boolean isok;
    private int code;
    private String message;
    private Object data;

    public static AjaxResponse success(Object data){
        AjaxResponse ajaxResult = new AjaxResponse();
        ajaxResult.setIsok(true);
        ajaxResult.setCode(200);
        ajaxResult.setMessage("success");
        ajaxResult.setData(data);
        return ajaxResult;
    }

    public static AjaxResponse error(Object data){
        AjaxResponse ajaxResult = new AjaxResponse();
        ajaxResult.setIsok(false);
        ajaxResult.setCode(400);
        ajaxResult.setMessage("error");
        ajaxResult.setData(data);
        return ajaxResult;
    }
}
