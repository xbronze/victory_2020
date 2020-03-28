package com.victory2020.controller.backend;

import com.victory2020.common.Const;
import com.victory2020.common.ServerResponse;
import com.victory2020.pojo.User;
import com.victory2020.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/manage/user")
public class UserManageController {

    @Autowired
    private IUserService iUserService;

    public ServerResponse<User> login(String username, String password, HttpSession session){
        ServerResponse<User> response = iUserService.login(username, password);
        if(response.isSuccess()){
            User user = response.getData();
            if(Const.Role.ROLE_ADMIN == user.getRole()){
                session.setAttribute(Const.CURRENT_USER, user);
                return response;
            } else {
                return ServerResponse.createByErrorMessage("用户不是管理员，登陆失败");
            }
        }
        return response;
    }
}
