package com.victory2020.dao;

import com.victory2020.pojo.Cart;
import com.victory2020.vo.CartVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CartMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Cart record);

    int insertSelective(Cart record);

    Cart selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Cart record);

    int updateByPrimaryKey(Cart record);

    Cart selectCartByUserIdAndProductId(@Param("userId") Integer userId, @Param("productId") Integer productId);

    List<Cart> selectCartByUserId(Integer userId);

    int selectCartProductCheckedStatusByUserId(Integer userId);

    int deleteByUserIdAndProductId(@Param("userId") Integer userId, @Param("productIdList") List<String> productIdList);

    int checkedOrUncheckedProduct(@Param("userId") Integer userId, @Param("productId") Integer productId,
                                  @Param("checked") Integer checked);

    int selectCartProductCount(Integer userId);

    /**
     * 获取购物车指定人员已勾选的数据
     * @param userId 人员id
     * @return 结果
     */
    List<Cart> selectCheckedCartByUserId(Integer userId);
}