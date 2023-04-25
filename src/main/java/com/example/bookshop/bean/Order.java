package com.example.bookshop.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("订单列表实体类")
public class Order {
    @ApiModelProperty("订单编号ID")
    private String orderId;
    @ApiModelProperty("图书ID")
    private int bookId;
    @ApiModelProperty("卖家ID")
    private int sellerId;
    @ApiModelProperty("买家ID")
    private int buyerId;
}
