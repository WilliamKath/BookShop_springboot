package com.example.bookshop.vo;

import com.example.bookshop.bean.Order;
import com.example.bookshop.bean.Seller;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("订单信息详情展示页->用于购物车")
public class OrderDetailVo extends Order {
    @ApiModelProperty("订单编号")
    private String orderId;
    @ApiModelProperty("图书ID")
    private int bookId;
    @ApiModelProperty("卖家ID")
    private int sellerId;
    @ApiModelProperty("买家ID")
    private int buyerId;
    @ApiModelProperty("通过卖家ID查找卖家信息")
    private Seller seller;
    @ApiModelProperty("通过图书ID查找图书信息")
    private BookVo bookVo;
}
