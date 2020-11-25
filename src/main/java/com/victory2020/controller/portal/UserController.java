package com.victory2020.controller.portal;

import com.victory2020.common.Const;
import com.victory2020.common.ResponseCode;
import com.victory2020.common.ServerResponse;
import com.victory2020.pojo.User;
import com.victory2020.service.IUserService;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService iUserService;

    /**
     * 用户登陆
     * @param username 用户名
     * @param password 密码
     * @param session HttpSession
     * @return
     */
    @RequestMapping(value = "login.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> login(@RequestParam(value = "username") String username,
                                      @RequestParam(value = "password") String password,
                                      HttpSession session){
        ServerResponse<User> response = iUserService.login(username, password);
        if(response.isSuccess()){
            session.setAttribute(Const.CURRENT_USER, response);
        }
        return response;
    }

    /**
     * 用户登出
     * @param session HttpSession
     * @return
     */
    @RequestMapping(value = "logout.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> logout(HttpSession session){
        session.removeAttribute(Const.CURRENT_USER);
        return ServerResponse.createBySuccess();
    }

    /**
     * 用户注册
     * @param user
     * @return
     */
    @RequestMapping(value = "register.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> register(@RequestBody User user){
        return iUserService.register(user);
    }

    /**
     * 检验指定的输入参数是否存在 <p>
     * 主要针对两个参数，一个是用户名，一个是邮箱，保证用户注册时数据不会重复
     * @param str 要检验的值
     * @param type 要检验值的类型，用户名还是邮箱
     * @return
     */
    @RequestMapping(value = "check_valid.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> checkValid(@RequestParam(value = "str") String str, @RequestParam(value = "type") String type){
        return iUserService.checkValid(str, type);
    }

    /**
     * 获取登陆用户的信息
     * @param session HttpSession
     * @return
     */
    @RequestMapping(value = "get_user_info.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> getUserInfo(HttpSession session){
        ServerResponse<User> response = (ServerResponse<User>) session.getAttribute(Const.CURRENT_USER);
        if(response != null){
            User user = response.getData();
            if(user != null){
                return response;
            }
        }
        return ServerResponse.createByErrorMessage("用户未登陆，无法获取当前用户的信息");
    }

    /**
     * 忘记密码的安全问题
     * @param username 用户名
     * @return
     */
    @RequestMapping(value = "forget_get_question.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> forgetGetQuestion(@RequestParam(value = "username") String username){
        return iUserService.selectQuestion(username);
    }

    /**
     * 校验忘记密码的安全问题以及问题答案
     * @param username 用户名
     * @param question 问题
     * @param answer 答案
     * @return
     */
    @RequestMapping(value = "forget_check_answer.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> forgetCheckAnswer(@RequestParam(value = "username") String username,
                                                    @RequestParam(value = "question") String question,
                                                    @RequestParam(value = "answer") String answer){
        return iUserService.checkAnswer(username,question,answer);
    }

    /**
     * 用户在忘记密码情况下，重新设置密码
     * @param username 用户名
     * @param passwordNew 新密码
     * @param forgetToken 登陆token
     * @return
     */
    @RequestMapping(value = "forget_reset_password.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> forgetRestPassword(@RequestParam(value = "username") String username,
                                                     @RequestParam(value = "passwordNew") String passwordNew,
                                                     @RequestParam(value = "forgetToken") String forgetToken){
        return iUserService.forgetResetPassword(username, passwordNew, forgetToken);
    }

    /**
     * 用户在未忘记密码情况下，重新设置密码
     * @param session HttpSession
     * @param passwordOld 旧密码
     * @param passwordNew 新密码
     * @return
     */
    @RequestMapping(value = "reset_password.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> resetPassword(HttpSession session, @RequestParam(value = "passwordOld") String passwordOld,
                                                @RequestParam(value = "passwordNew") String passwordNew){
        ServerResponse<User> response = (ServerResponse<User>) session.getAttribute(Const.CURRENT_USER);
        if(response == null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        User user = response.getData();
        if(user == null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        return iUserService.resetPassword(passwordOld, passwordNew, user);
    }

    /**
     * 更新用户信息
     * @param session HttpSession
     * @param user
     * @return
     */
    @RequestMapping(value = "update_information.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> update_information(HttpSession session, @RequestBody User user){
        ServerResponse<User> session_response = (ServerResponse<User>) session.getAttribute(Const.CURRENT_USER);
        if(session_response == null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        User currentUser = session_response.getData();
        if(currentUser == null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        user.setId(currentUser.getId());
        user.setUsername(currentUser.getUsername());
        ServerResponse<User> response = iUserService.updateInformation(user);
        if(response.isSuccess()){
            response.getData().setUsername(currentUser.getUsername());
            session.setAttribute(Const.CURRENT_USER,response.getData());
        }
        return response;
    }

    /**
     *
     * @param session HttpSession
     * @return
     */
    @RequestMapping(value = "get_information.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> get_information(HttpSession session){
        ServerResponse<User> response = (ServerResponse<User>) session.getAttribute(Const.CURRENT_USER);
        if(response == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"未登录,需要强制登录status=10");
        }
        User currentUser = response.getData();
        if(currentUser == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"未登录,需要强制登录status=10");
        }
        return iUserService.getInformation(currentUser.getId());
    }

}
