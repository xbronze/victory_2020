package com.victory2020.controller.portal;

import com.victory2020.common.Const;
import com.victory2020.common.ServerResponse;
import com.victory2020.pojo.User;
import com.victory2020.service.IUserService;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService iUserService;


    @RequestMapping(value = "test.do", method = RequestMethod.POST)
    @ResponseBody
    public String test(){
        return "hello world";
    }

    /**
     * 用户登陆
     * @param username
     * @param password
     * @param session
     * @return
     */
    @RequestMapping(value = "login.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> login(String username, String password, HttpSession session){
        ServerResponse<User> response = iUserService.login(username, password);
        if(response.isSuccess()){
            session.setAttribute(Const.CURRENT_USER, response);
        }
        return response;
    }

}
