package com.story.change.web.service.impl;

import com.alibaba.fastjson.JSON;
import com.story.change.web.dao.PhoneCheckMapper;
import com.story.change.web.dao.UserMapper;
import com.story.change.web.encrypt.MD5Util;
import com.story.change.web.model.PhoneCheck;
import com.story.change.web.model.ResponseBase;
import com.story.change.web.model.User;
import com.story.change.web.service.IUserService;
import com.story.change.web.util.StringCheckUtil;
import com.story.change.web.util.StringGenerateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by story on 下午 7:52.
 * 用户服务实现类
 */
@Service("userService")
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PhoneCheckMapper phoneCheckMapper;

    /**
     * 生成手机验证码
     */
    public ResponseBase getPhoneCheck(String phone) {
        ResponseBase response = new ResponseBase();
        if (StringCheckUtil.isPhone(phone)) {
            //TODO 对接短信网关，暂时自动生成验证码
            PhoneCheck phoneCheck = new PhoneCheck();
            phoneCheck.setPhone(phone);
            phoneCheck.setCreateTime(new Date());
            phoneCheck.setCheck(StringGenerateUtil.getPhoneCheck());
            int i = phoneCheckMapper.saveOrUpdate(phoneCheck);
            if (i > 0) {
                response.setSuccess(true);
                response.setMessage("发送成功");
            } else {
                response.setSuccess(false);
                response.setMessage("生成验证码失败");
            }
        } else {
            response.setSuccess(false);
            response.setMessage("无效手机号");
        }
        return response;
    }

    /**
     * 注册
     */
    public ResponseBase register(String phone, String check) {
        ResponseBase response = new ResponseBase();
        if (StringCheckUtil.isPhone(phone)) {
            PhoneCheck phoneCheck = phoneCheckMapper.selectByPhone(phone);
            if (phoneCheck != null && check != null && check.equals(phoneCheck.getCheck())) {
                Date date = new Date();
                if (date.getTime() - phoneCheck.getCreateTime().getTime() < 5*60*1000) {
                    User user = userMapper.selectByPhone(phone);
                    if (user == null) {
                        user = new User();
                        user.setPhone(phone);
                        user.setPwd(MD5Util.getMD5("123456"));
                        int i = userMapper.insert(user);
                        if (i > 0) {
                            response.setSuccess(true);
                            response.setMessage("注册成功");
                            response.setBody(JSON.toJSONString(user));
                        } else {
                            response.setSuccess(false);
                            response.setMessage("保存用户信息失败");
                        }
                    } else {
                        response.setSuccess(false);
                        response.setMessage("手机号已注册");
                    }
                } else {
                    response.setSuccess(false);
                    response.setMessage("验证码已过期");
                }
            } else {
                response.setSuccess(false);
                response.setMessage("验证码错误");
            }
        } else {
            response.setSuccess(false);
            response.setMessage("无效手机号");
        }
        return response;
    }

    public ResponseBase loginByPwd(String phone, String pwd) {
        return null;
    }

    public ResponseBase loginByCheck(String phone, String check) {
        return null;
    }

    public ResponseBase setUserData(User user) {
        return null;
    }
}
