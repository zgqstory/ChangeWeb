package com.story.change.web.service;

import com.story.change.web.model.ResponseBase;

/**
 * Created by story on 2017/2/23 0023.
 * 用户Service接口
 */
public interface IUserService {
    ResponseBase getPhoneCheck(String phone);
    ResponseBase register(String phone, String check);
    ResponseBase loginByPwd(String phone, String pwd);
    ResponseBase loginByCheck(String phone, String check);
    ResponseBase setUserData(String phone, String data, String pwd, int type);
}
