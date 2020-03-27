package com.victory2020.service;

import com.victory2020.common.ServerResponse;
import com.victory2020.pojo.User;

public interface IUserService {

    ServerResponse<User> login(String username, String password);

}
