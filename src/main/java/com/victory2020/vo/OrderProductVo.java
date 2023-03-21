package com.victory2020.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author: xbronze
 * @date: 2023-03-21 10:05
 * @description: TODO
 */
@Data
public class OrderProductVo {

    private List<OrderItemVo> orderItemVoList;

    private BigDecimal productTotalPrice;

    private String imageHost;

}
