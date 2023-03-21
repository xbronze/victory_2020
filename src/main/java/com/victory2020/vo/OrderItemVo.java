package com.victory2020.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author: xbronze
 * @date: 2023-03-20 15:41
 * @description: TODO
 */
@Data
public class OrderItemVo {

    private Long orderNo;

    private Integer productId;

    private String productName;

    private String productImage;

    private BigDecimal currentUnitPrice;

    private Integer quantity;

    private BigDecimal totalPrice;

    private String createTime;
}
