package com.example.bookshop.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 卖家实体类 - 与用户实体类共用同一张表
 */
@Data
@ApiModel("卖家实体类")
public class Seller {
    @ApiModelProperty("用户ID")
    private int sellerId;
    @ApiModelProperty("用户账户名")
    private String sellerName;
    @ApiModelProperty("用户邮箱")
    private String sellerEmail;
    @ApiModelProperty("用户手机号")
    private String sellerPhone;


}
