package com.victory2020.controller.portal;

import com.victory2020.common.Const;
import com.victory2020.common.ResponseCode;
import com.victory2020.common.ServerResponse;
import com.victory2020.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author xuzh
 * <p>创建时间: 2020-05-26 18:49 </p>
 */
@Controller
@RequestMapping("/order/")
public class OrderController {

    public ServerResponse pay(HttpSession session, Long orderNo, HttpServletRequest request){
        ServerResponse<User> response = (ServerResponse<User>) session.getAttribute(Const.CURRENT_USER);
        if(response == null || response.getData() == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
    }
}
