package com.story.change.web.service.impl;

import com.story.change.web.dao.PhoneCheckMapper;
import com.story.change.web.dao.UserMapper;
import com.story.change.web.encrypt.MD5Util;
import com.story.change.web.model.PhoneCheck;
import com.story.change.web.model.ResponseBase;
import com.story.change.web.model.User;
import com.story.change.web.service.IUserService;
import com.story.change.web.util.StringCheckUtil;
import com.story.change.web.util.StringFormatUtil;
import com.story.change.web.util.StringGenerateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        try {
            if (StringCheckUtil.isPhone(phone)) {
                //TODO 对接短信网关，暂时自动生成验证码
                PhoneCheck phoneCheck = new PhoneCheck();
                phoneCheck.setPhone(phone);
                phoneCheck.setCreateTime(String.valueOf(System.currentTimeMillis()));
                phoneCheck.setCheck(StringGenerateUtil.getPhoneCheck());
                int number = phoneCheckMapper.countByPhone(phone);
                int i;
                if (number > 0) {
                    i = phoneCheckMapper.update(phoneCheck);
                } else {
                    i = phoneCheckMapper.insert(phoneCheck);
                }
                if (i > 0) {
                    response.setRspType("N");
                    response.setRspMsg("发送成功");
                } else {
                    response.setRspType("E");
                    response.setRspMsg("生成验证码失败");
                }
            } else {
                response.setRspType("E");
                response.setRspMsg("无效手机号");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setRspType("E");
            response.setRspMsg(e.getMessage());
        }
        return response;
    }

    /**
     * 注册
     */
    public ResponseBase register(String phone, String check) {
        ResponseBase response = new ResponseBase();
        try {
            if (StringCheckUtil.isPhone(phone)) {
                PhoneCheck phoneCheck = phoneCheckMapper.selectByPhone(phone);
                if (phoneCheck != null && check != null && check.equals(phoneCheck.getCheck())) {
                    if (System.currentTimeMillis() - Long.parseLong(phoneCheck.getCreateTime()) < 5*60*1000) {
                        User user = userMapper.selectByPhone(phone);
                        if (user == null) {
                            user = new User();
                            user.setPhone(phone);
                            user.setPwd(MD5Util.getMD5("123456"));
                            int i = userMapper.insert(user);
                            if (i > 0) {
                                response.setRspType("N");
                                response.setRspMsg("注册成功");
                                response.setRspData(user);
                            } else {
                                response.setRspType("E");
                                response.setRspMsg("保存用户信息失败");
                            }
                        } else {
                            response.setRspType("E");
                            response.setRspMsg("手机号已注册");
                        }
                    } else {
                        response.setRspType("E");
                        response.setRspMsg("验证码已过期");
                    }
                } else {
                    response.setRspType("E");
                    response.setRspMsg("验证码错误");
                }
            } else {
                response.setRspType("E");
                response.setRspMsg("无效手机号");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setRspType("E");
            response.setRspMsg(e.getMessage());
        }
        return response;
    }

    /**
     * 手机号+密码登录
     */
    public ResponseBase loginByPwd(String phone, String pwd) {
        ResponseBase response = new ResponseBase();
        try {
            if (StringCheckUtil.isPhone(phone)) {
                User user = userMapper.selectByPhone(phone);
                if (user != null) {
                    if (user.getPwd().equals(pwd)) {
                        response.setRspType("N");
                        response.setRspMsg("登录成功");
                        response.setRspData(user);
                    } else {
                        response.setRspType("E");
                        response.setRspMsg("密码错误");
                    }
                } else {
                    response.setRspType("E");
                    response.setRspMsg("用户不存在");
                }
            } else {
                response.setRspType("E");
                response.setRspMsg("无效手机号");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setRspType("E");
            response.setRspMsg(e.getMessage());
        }
        return response;
    }

    /**
     * 手机号+验证码登录
     */
    public ResponseBase loginByCheck(String phone, String check) {
        ResponseBase response = new ResponseBase();
        try {
            if (StringCheckUtil.isPhone(phone)) {
                PhoneCheck phoneCheck = phoneCheckMapper.selectByPhone(phone);
                if (phoneCheck!= null && phoneCheck.getCheck().equals(check) &&
                        System.currentTimeMillis() - Long.parseLong(phoneCheck.getCreateTime()) < 5*60*1000) {
                    User user = userMapper.selectByPhone(phone);
                    if (user != null) {
                        response.setRspType("N");
                        response.setRspMsg("登录成功");
                        response.setRspData(user);
                    } else {
                        response.setRspType("E");
                        response.setRspMsg("用户不存在");
                    }
                } else {
                    response.setRspType("E");
                    response.setRspMsg("验证码错误");
                }
            } else {
                response.setRspType("E");
                response.setRspMsg("无效手机号");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setRspType("E");
            response.setRspMsg(e.getMessage());
        }
        return response;
    }

    /**
     * 设置用户信息：type=0,用户名；type=1,密码；type=2,头像
     */
    public ResponseBase setUserData(String phone, String data, String pwd, int type) {
        ResponseBase response = new ResponseBase();
        try {
            if (StringCheckUtil.isPhone(phone)) {
                User user = userMapper.selectByPhone(phone);
                if (user != null) {
                    if (data != null && !data.equals("")) {
                        response.setRspType("E");
                        response.setRspMsg("修改数据不能为空");
                    } else {
                        if(type == 1) {
                            if(!user.getPwd().equals(pwd)) {
                                response.setRspType("E");
                                response.setRspMsg("密码错误");
                                return response;
                            }
                            user.setPwd(data);
                        } else if(type == 0) {
                            user.setName(data);
                        } else if(type == 2) {
                            user.setAvatar(data);
                        }
                        userMapper.updateByPhone(user);
                        response.setRspType("N");
                        response.setRspMsg("修改成功");
                    }
                } else {
                    response.setRspType("E");
                    response.setRspMsg("用户不存在");
                }
            } else {
                response.setRspType("E");
                response.setRspMsg("无效手机号");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setRspType("E");
            response.setRspMsg(e.getMessage());
        }
        return response;
    }
}
