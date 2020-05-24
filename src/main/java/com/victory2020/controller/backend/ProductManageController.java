package com.victory2020.controller.backend;

import com.google.common.collect.Maps;
import com.victory2020.common.Const;
import com.victory2020.common.ResponseCode;
import com.victory2020.common.ServerResponse;
import com.victory2020.pojo.Product;
import com.victory2020.pojo.User;
import com.victory2020.service.IFileService;
import com.victory2020.service.IProductService;
import com.victory2020.service.IUserService;
import com.victory2020.util.PropertiesUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping("/manage/product")
public class ProductManageController {

    @Autowired
    private IProductService iProductService;

    @Autowired
    private IUserService iUserService;

    @Autowired
    private IFileService iFileService;

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

    /**
     * 获取产品的详细信息
     * @param productId
     * @param session
     * @return
     */
    @RequestMapping("get_product_detail.do")
    @ResponseBody
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

    /**
     * 获取产品列表
     * @param pageNum
     * @param pageSize
     * @param session
     * @return
     */
    @RequestMapping("get_product_list.do")
    @ResponseBody
    public ServerResponse getProductList(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
        @RequestParam(value = "pageSize",defaultValue = "10") int pageSize, HttpSession session){
        ServerResponse<User> response = (ServerResponse<User>) session.getAttribute(Const.CURRENT_USER);
        if(response == null || response.getData() == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登陆管理员用户");
        }
        if (iUserService.checkAdminRole(response.getData()).isSuccess()){
            return iProductService.getProductList(pageNum, pageSize);
        } else {
            return ServerResponse.createByErrorMessage("用户无权限操作");
        }
    }

    /**
     * 商品搜索
     * @param session
     * @param productName
     * @param productId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("search_product.do")
    @ResponseBody
    public ServerResponse productSearch(HttpSession session,String productName,Integer productId,
        @RequestParam(value = "pageNum",defaultValue = "1") int pageNum, @RequestParam(value = "pageSize",defaultValue = "10") int pageSize){
        ServerResponse<User> response = (ServerResponse<User>) session.getAttribute(Const.CURRENT_USER);
        if(response == null || response.getData() == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登陆管理员用户");
        }
        if (iUserService.checkAdminRole(response.getData()).isSuccess()){
            return iProductService.searchProduct(productName, productId, pageNum, pageNum);
        } else {
            return ServerResponse.createByErrorMessage("用户无权限操作");
        }
    }

    @RequestMapping("upload.do")
    @ResponseBody
    public ServerResponse upload(@RequestParam(value = "upload_file", required = false) MultipartFile file,
                                        HttpServletRequest request, HttpSession session){
        ServerResponse<User> response = (ServerResponse<User>) session.getAttribute(Const.CURRENT_USER);
        if(response == null || response.getData() == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登陆管理员用户");
        }
        if (iUserService.checkAdminRole(response.getData()).isSuccess()){
            String path = request.getSession().getServletContext().getRealPath("upload");
            String targetFileName = iFileService.upload(file, path);
            String url = PropertiesUtil.getProperty("ftp.server.http.prefix") + targetFileName;

            Map fileMap = Maps.newHashMap();
            fileMap.put("uri", targetFileName);
            fileMap.put("url", url);
            return ServerResponse.createBySuccess(fileMap);
        } else {
            return ServerResponse.createByErrorMessage("用户无权限操作");
        }
    }

    @RequestMapping("richtext_img_upload.do")
    @ResponseBody
    public Map richtextImgUpload(@RequestParam(value = "upload_file",required = false) MultipartFile file,
                                 HttpServletRequest request, HttpServletResponse response, HttpSession session){
        Map resultMap = Maps.newHashMap();
        ServerResponse<User> responseUser = (ServerResponse<User>) session.getAttribute(Const.CURRENT_USER);
        if(responseUser == null || responseUser.getData() == null){
            resultMap.put("success",false);
            resultMap.put("msg","请登录管理员");
            return resultMap;
        }
        //富文本中对于返回值有自己的要求,我们使用是simditor所以按照simditor的要求进行返回
//        {
//            "success": true/false,
//                "msg": "error message", # optional
//            "file_path": "[real file path]"
//        }
        if(iUserService.checkAdminRole(responseUser.getData()).isSuccess()){
            String path = request.getSession().getServletContext().getRealPath("upload");
            String targetFileName = iFileService.upload(file,path);
            if(StringUtils.isBlank(targetFileName)){
                resultMap.put("success",false);
                resultMap.put("msg","上传失败");
                return resultMap;
            }
            String url = PropertiesUtil.getProperty("ftp.server.http.prefix")+targetFileName;
            resultMap.put("success",true);
            resultMap.put("msg","上传成功");
            resultMap.put("file_path",url);
            response.addHeader("Access-Control-Allow-Headers","X-File-Name");
            return resultMap;
        }else{
            resultMap.put("success",false);
            resultMap.put("msg","无权限操作");
            return resultMap;
        }
    }
}
