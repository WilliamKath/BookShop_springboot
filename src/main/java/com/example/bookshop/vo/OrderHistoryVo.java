package com.example.bookshop.vo;

import com.example.bookshop.bean.Book;
import com.example.bookshop.bean.OrderHistory;
import com.example.bookshop.bean.Seller;
import com.example.bookshop.bean.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Timestamp;

@Data
@ApiModel("历史订单详情实体类")
public class OrderHistoryVo extends OrderHistory {
    @ApiModelProperty("历史订单编号ID")
    private int orderhxId;
    @ApiModelProperty("订单编号，与orderlist表主键相关")
    private String orderId;
    @ApiModelProperty("支付宝支付编号")
    private String payId;
    @ApiModelProperty("买家ID，与user表主键关联")
    private int buyerId;
    @ApiModelProperty("卖家ID，与user表主键关联")
    private int sellerId;
    @ApiModelProperty("图书ID，与book表主键关联")
    private int bookId;
    @ApiModelProperty("总金额")
    private String totalAmount;
    @ApiModelProperty("商品状态 0:待发货 1:已发货 2:已收货")
    private int goodsState;
    @ApiModelProperty("订单交易时间")
    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String createTime;

    @ApiModelProperty("通过卖家ID查找卖家信息")
    private User sellerOrBuyer;
    @ApiModelProperty("通过图书ID查找图书信息")
    private Book book;



}
