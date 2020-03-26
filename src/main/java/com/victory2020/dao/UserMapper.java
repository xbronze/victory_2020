package com.victory2020.dao;

import com.victory2020.pojo.User;

public interface UserMapper {

    int checkUsername(String username);

    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}