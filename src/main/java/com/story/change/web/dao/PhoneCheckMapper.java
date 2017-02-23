package com.story.change.web.dao;

import com.story.change.web.model.PhoneCheck;

public interface PhoneCheckMapper {
    int insert(PhoneCheck record);

    int insertSelective(PhoneCheck record);

    int saveOrUpdate(PhoneCheck record);

    PhoneCheck selectByPhone(String phone);
}