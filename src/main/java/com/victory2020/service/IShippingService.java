package com.victory2020.service;

import com.github.pagehelper.PageInfo;
import com.victory2020.common.ServerResponse;
import com.victory2020.pojo.Shipping;

/**
 * @author xuzh
 * <p>创建时间: 2020-05-24 21:42 </p>
 */
public interface IShippingService {

    public ServerResponse addShippingAddress(Integer userId, Shipping shipping);

    public ServerResponse<String> delShippingAddress(Integer userId,Integer shippingId);

    public ServerResponse updateShippingAddress(Integer userId, Shipping shipping);

    public ServerResponse<Shipping> selectShippingAddress(Integer userId, Integer shippingId);

    public ServerResponse<PageInfo> shippingAddressList(Integer userId, int pageNum, int pageSize);

}
