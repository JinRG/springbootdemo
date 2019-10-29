package com.jrg.demo.dao;

import com.jrg.demo.entity.User;

public interface UserMapper {
    int insert(User record);

    int insertSelective(User record);
}