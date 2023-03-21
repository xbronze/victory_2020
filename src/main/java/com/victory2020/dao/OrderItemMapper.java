package com.victory2020.dao;

import com.victory2020.pojo.OrderItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderItemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrderItem record);

    int insertSelective(OrderItem record);

    OrderItem selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderItem record);

    int updateByPrimaryKey(OrderItem record);

    List<OrderItem> getByOrderIdAndUserId(@Param("orderNo") Long orderNo, @Param("userId") Integer userId);

    void batchInsert(List<OrderItem> orderItemList);

    List<OrderItem> getByOrderNoUserId(Long orderNo, Integer userId);

    List<OrderItem> getByOrderNo(Long orderNo);
}