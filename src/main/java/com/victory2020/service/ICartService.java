package com.victory2020.service;

import com.victory2020.common.ServerResponse;
import com.victory2020.vo.CartVO;

/**
 * @author xuzh
 * <p>创建时间: 2020-05-17 18:28 </p>
 */
public interface ICartService {

    public ServerResponse<CartVO> listProductInCart(Integer userId);

    public ServerResponse addProductToCart(Integer userId, Integer count, Integer productId);

    public ServerResponse<CartVO> updateProductInCart(Integer userId, Integer count, Integer productId);

    public ServerResponse<CartVO> deleteProductInCart(Integer userId, String productId);

    public ServerResponse<CartVO> selectOrUnSelect (Integer userId,Integer productId,Integer checked);

    public ServerResponse<Integer> getCartProductCount(Integer userId);

}
