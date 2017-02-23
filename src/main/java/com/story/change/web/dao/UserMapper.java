package com.story.change.web.dao;

import com.story.change.web.model.User;

public interface UserMapper {
    int insert(User record);

    int insertSelective(User record);

    User selectByPhone(String phone);
}