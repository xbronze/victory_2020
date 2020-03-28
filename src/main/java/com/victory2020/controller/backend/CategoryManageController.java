package com.victory2020.controller.backend;

import com.victory2020.common.Const;
import com.victory2020.common.ResponseCode;
import com.victory2020.common.ServerResponse;
import com.victory2020.pojo.User;
import com.victory2020.service.ICategoryService;
import com.victory2020.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/manage/category")
public class CategoryManageController {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private ICategoryService iCategoryService;

    @RequestMapping("add_category.do")
    @ResponseBody
    public ServerResponse addCategory(String categoryName, @RequestParam(value = "parentId", defaultValue = "0") int parentId,
                                      HttpSession session){
        ServerResponse<User> response = (ServerResponse<User>) session.getAttribute(Const.CURRENT_USER);
        if(response == null || response.getData() == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登陆");
        }

        //校验当前登陆的用户是否有管理员权限
        if(iUserService.checkAdminRole(response.getData()).isSuccess()){
            return iCategoryService.addCategory(categoryName, parentId);
        } else {
            return ServerResponse.createByErrorMessage("无权限操作，需要管理员权限");
        }

    }

    @RequestMapping("set_category_name.do")
    @ResponseBody
    public ServerResponse setCategoryName(Integer categoryId, String categoryName, HttpSession session){
        ServerResponse<User> response = (ServerResponse<User>) session.getAttribute(Const.CURRENT_USER);
        if(response == null || response.getData() == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登陆");
        }

        //校验当前登陆的用户是否有管理员权限
        if(iUserService.checkAdminRole(response.getData()).isSuccess()){
            return iCategoryService.updateCategoryName(categoryId, categoryName);
        } else {
            return ServerResponse.createByErrorMessage("无权限操作，需要管理员权限");
        }
    }

    @RequestMapping("get_category.do")
    @ResponseBody
    public ServerResponse getChildrenParallelCategory(@RequestParam(value = "categoryId", defaultValue = "0") Integer categoryId,
                                                       HttpSession session){
        ServerResponse<User> response = (ServerResponse<User>) session.getAttribute(Const.CURRENT_USER);
        if(response == null || response.getData() == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登陆");
        }

        if(iUserService.checkAdminRole(response.getData()).isSuccess()){
            //查询子节点的category信息，不递归，保持平级
            return iCategoryService.getChildrenParallelCategory(categoryId);
        } else {
            return ServerResponse.createByErrorMessage("无权限操作，需要管理员权限");
        }
    }

    @RequestMapping("get_deep_category.do")
    @ResponseBody
    public ServerResponse getCategoryAndDeepChildrenCategory(@RequestParam(value = "categoryId", defaultValue = "0") Integer categoryId,
                                                             HttpSession session){
        ServerResponse<User> response = (ServerResponse<User>) session.getAttribute(Const.CURRENT_USER);
        if(response == null || response.getData() == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登陆");
        }

        if(iUserService.checkAdminRole(response.getData()).isSuccess()){

            return iCategoryService.selectCategoryAndChildrenById(categoryId);
        } else {
            return ServerResponse.createByErrorMessage("无权限操作，需要管理员权限");
        }
    }
}
