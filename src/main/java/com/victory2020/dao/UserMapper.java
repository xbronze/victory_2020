package com.victory2020.dao;

import com.victory2020.pojo.User;
import org.apache.ibatis.annotations.Param;


public interface UserMapper {

    int checkUsername(String username);

    User selectLogin (@Param("username") String username, @Param("password") String password);

    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

}