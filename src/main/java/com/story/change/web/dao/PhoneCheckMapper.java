package com.story.change.web.dao;

import com.story.change.web.model.PhoneCheck;

public interface PhoneCheckMapper {

    int countByPhone(String phone);

    int insert(PhoneCheck record);

    int update(PhoneCheck record);

    PhoneCheck selectByPhone(String phone);
}