package com.victory2020.service;

import com.github.pagehelper.PageInfo;
import com.victory2020.common.ServerResponse;
import com.victory2020.vo.OrderVo;

import java.util.Map;

/**
 * @author xuzh
 * <p>创建时间: 2020-05-26 18:49 </p>
 */
public interface IOrderService {

    /**
     * 创建订单
     * @param userId 用户id
     * @param shippingId 购物车id
     * @return 结果
     */
    ServerResponse createOrder(Integer userId,Integer shippingId);

    /**
     * 取消订单
     * @param userId 人员id
     * @param orderNo 订单编号
     * @return 结果
     */
    ServerResponse<String> cancel(Integer userId,Long orderNo);

    ServerResponse getOrderCartProduct(Integer userId);

    /**
     * 获取订单详情
     * @param userId 人员id
     * @param orderNo 订单编号
     * @return 结果
     */
    ServerResponse<OrderVo> getOrderDetail(Integer userId, Long orderNo);

    /**
     * 获取订单列表
     * @param userId 人员id
     * @param pageNum 页码
     * @param pageSize 分页数
     * @return 订单列表
     */
    ServerResponse<PageInfo> getOrderList(Integer userId, int pageNum, int pageSize);

    ServerResponse pay(Long orderNo, Integer userId, String path);

    ServerResponse aliCallback(Map<String,String> params);

    ServerResponse queryOrderPayStatus(Integer userId,Long orderNo);





    //backend
    ServerResponse<PageInfo> manageList(int pageNum, int pageSize);

    ServerResponse<OrderVo> manageDetail(Long orderNo);

    ServerResponse<PageInfo> manageSearch(Long orderNo,int pageNum,int pageSize);

    ServerResponse<String> manageSendGoods(Long orderNo);

}
