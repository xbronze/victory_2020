package com.victory2020.controller.backend;

import com.victory2020.common.Const;
import com.victory2020.common.ResponseCode;
import com.victory2020.common.ServerResponse;
import com.victory2020.pojo.Product;
import com.victory2020.pojo.User;
import com.victory2020.service.IProductService;
import com.victory2020.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/manage/product")
public class ProductManageController {

    @Autowired
    private IProductService iProductService;

    @Autowired
    private IUserService iUserService;

    /**
     * 更新或新增销售产品信息
     * @param product
     * @param session
     * @return
     */
    @RequestMapping("save.do")
    @ResponseBody
    public ServerResponse productSave(Product product, HttpSession session){
        ServerResponse<User> response = (ServerResponse<User>) session.getAttribute(Const.CURRENT_USER);
        if(response == null || response.getData() == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登陆管理员用户");
        }
        if (iUserService.checkAdminRole(response.getData()).isSuccess()){
            return iProductService.saveOrUpdateProduct(product);
        } else {
            return ServerResponse.createByErrorMessage("用户无权限操作");
        }
    }

    /**
     * 修改产品销售状态
     * @param productId
     * @param status
     * @param session
     * @return
     */
    @RequestMapping("set_sale_status.do")
    @ResponseBody
    public ServerResponse setSaleStatus(Integer productId, Integer status, HttpSession session){
        ServerResponse<User> response = (ServerResponse<User>) session.getAttribute(Const.CURRENT_USER);
        if(response == null || response.getData() == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登陆管理员用户");
        }
        if (iUserService.checkAdminRole(response.getData()).isSuccess()){
            return iProductService.setSaleStatus(productId, status);
        } else {
            return ServerResponse.createByErrorMessage("用户无权限操作");
        }
    }

    public ServerResponse getProductDetail(Integer productId, HttpSession session){
        ServerResponse<User> response = (ServerResponse<User>) session.getAttribute(Const.CURRENT_USER);
        if(response == null || response.getData() == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登陆管理员用户");
        }
        if (iUserService.checkAdminRole(response.getData()).isSuccess()){
            return iProductService.manageProductDetail(productId);
        } else {
            return ServerResponse.createByErrorMessage("用户无权限操作");
        }
    }
}
