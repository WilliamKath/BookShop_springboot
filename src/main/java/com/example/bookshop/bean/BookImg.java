package com.example.bookshop.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("图书图片实体类")
public class BookImg implements Serializable {
    @ApiModelProperty("图片ID")
    private int imgId;
    @ApiModelProperty("对应图书ID")
    private int itemId;
    @ApiModelProperty("是否为大图(全部商品页及详情页展示) 1-是 2-否")
    private int imgIsbig;
    @ApiModelProperty("图片存放地址url")
    private String imgUrl;

}
