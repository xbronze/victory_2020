package com.victory2020.controller.portal;

import com.github.pagehelper.PageInfo;
import com.victory2020.common.Const;
import com.victory2020.common.ResponseCode;
import com.victory2020.common.ServerResponse;
import com.victory2020.pojo.Shipping;
import com.victory2020.pojo.User;
import com.victory2020.service.IShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @author xuzh
 * <p>创建时间: 2020-05-24 21:41 </p>
 */
@Controller
@RequestMapping("/shipping/")
public class ShippingController {

    @Autowired
    private IShippingService iShippingService;


    @RequestMapping("add.do")
    @ResponseBody
    public ServerResponse add(HttpSession session, Shipping shipping){
        ServerResponse<User> response = (ServerResponse<User>) session.getAttribute(Const.CURRENT_USER);
        if(response == null || response.getData() == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        User user = response.getData();
        return iShippingService.addShippingAddress(user.getId(), shipping);
    }


    @RequestMapping("del.do")
    @ResponseBody
    public ServerResponse del(HttpSession session,Integer shippingId){
        ServerResponse<User> response = (ServerResponse<User>) session.getAttribute(Const.CURRENT_USER);
        if(response == null || response.getData() == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        User user = response.getData();
        return iShippingService.delShippingAddress(user.getId(), shippingId);
    }

    @RequestMapping("update.do")
    @ResponseBody
    public ServerResponse update(HttpSession session,Shipping shipping){
        ServerResponse<User> response = (ServerResponse<User>) session.getAttribute(Const.CURRENT_USER);
        if(response == null || response.getData() == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        User user = response.getData();
        return iShippingService.updateShippingAddress(user.getId(), shipping);
    }


    @RequestMapping("select.do")
    @ResponseBody
    public ServerResponse<Shipping> select(HttpSession session,Integer shippingId){
        ServerResponse<User> response = (ServerResponse<User>) session.getAttribute(Const.CURRENT_USER);
        if(response == null || response.getData() == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        User user = response.getData();
        return iShippingService.selectShippingAddress(user.getId(), shippingId);
    }


    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse<PageInfo> list(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                         @RequestParam(value = "pageSize",defaultValue = "10")int pageSize,
                                         HttpSession session){
        ServerResponse<User> response = (ServerResponse<User>) session.getAttribute(Const.CURRENT_USER);
        if(response == null || response.getData() == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        User user = response.getData();
        return iShippingService.shippingAddressList(user.getId(), pageNum,pageSize);
    }


}
