package com.story.change.web.controller;

import com.alibaba.fastjson.JSON;
import com.story.change.web.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by story on 下午 2:40.
 * 用户相关请求处理
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @RequestMapping(value = "/getPhoneCheck", produces = {"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public String getPhoneCheck(String phone) {
        return JSON.toJSONString(userService.getPhoneCheck(phone));
    }

    @RequestMapping(value = "/register", produces={"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public String doRegister(String phone, String check) {
        return JSON.toJSONString(userService.register(phone, check));
    }

    @RequestMapping(value = "/loginWithCheck", produces = {"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public String loginWithCheck(String phone, String check) {
        return JSON.toJSONString(userService.loginByCheck(phone, check));
    }

    @RequestMapping(value = "/loginWithPwd", produces = {"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public String loginWithPwd(String phone, String pwd) {
        return JSON.toJSONString(userService.loginByPwd(phone, pwd));
    }

    @RequestMapping(value = "/updateUserData", produces = {"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public String updateUserData(int type, String phone, String data, String pwd) {
        return JSON.toJSONString(userService.setUserData(phone, data, pwd, type));
    }

}
